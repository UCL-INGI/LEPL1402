package module6;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Optional;

import java.util.*;

import static org.junit.Assert.*;

public class BoundedBufferTest {
    //BEGIN STRIP
    private void sleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){

        }
    }

    @Test
    public void testDoPut() {
        BoundedBuffer buf = new BoundedBuffer(2);

        Thread consumer = new Thread(new Consumer(buf, 1, null));
        consumer.start();

        sleep(200);

        Thread producer = new Thread(new Producer(buf, 1, null));
        producer.start();

        sleep(200);

        if(consumer.isAlive()){
            fail("You don't notify other threads that you've add an element");
        }

        BoundedBuffer buf2 = new BoundedBuffer(3);
        Thread prod = new Thread(new Producer(buf2, 2, null));
        prod.start();

        sleep(200);

        try{
            producer.join(10);
            prod.join(10);
            consumer.join(10);
        } catch(InterruptedException e){
            fail("Something goes wrong with you code");
        }

        if(buf2.getData()[0] == null){
            fail("You don't add the element correctly");
        }
        if(buf2.isEmpty()){
            fail("You don't increment the size");
        }
        if(!buf2.isFull()){
            fail("You don't add elements correcly");
        }
        if(buf2.getPutPointer() != 0){
            fail("After the end of the buffer length, your putPointer must return to zero");
        }
        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    public void testDoTake() {
        boolean flag = true;
        BoundedBuffer buf = new BoundedBuffer(1);
        Thread producer = new Thread(new Producer(buf, 3, null));
        producer.start();

        sleep(200);

        Thread consumer = new Thread(new Consumer(buf, 1, null));
        consumer.start();

        sleep(200);

        if(producer.isAlive()){
            fail("You don't notify other threads that you've add an element");
        }

        BoundedBuffer buf2 = new BoundedBuffer(2);
        Thread prod = new Thread(new Producer(buf2, 3, null));
        prod.start();

        sleep(200);
        List<Integer> list = new ArrayList<>(2);

        Thread cons = new Thread(new Consumer(buf2, 2, list));
        cons.start();

        sleep(200);

        try{
            producer.join(10);
            prod.join(10);
            cons.join(10);
            consumer.join(10);
        } catch(InterruptedException e){
            fail("Something goes wrong with you code");
        }

        if(!buf2.isEmpty() || list.get(0) != 0 || list.get(1) != 1){
            fail("You don't take the elements correctly");
        }
        if(buf2.isFull()){
            fail("You don't decrement the size");
        }
        if(buf2.getTakePointer() != 0){
            fail("After the end of the buffer length, your takePointer must return to zero");
        }
        if(buf2.getData()[0] != null || buf2.getData()[1] != null){
            fail("When you remove an element, you must set to empty the location of this element");
        }

        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    public void testPut() {
        boolean flag = true;

        BoundedBuffer buf = new BoundedBuffer(2);

        Thread producer = new Thread(new Producer(buf, 4, null));
        producer.start();

        sleep(200);

        if(!producer.isAlive()){ //must be alive here
            fail("You must wait when the buffer is full");
        }

        Thread consumer = new Thread(new Consumer(buf, 1, null));
        consumer.start();

        sleep(200);

        try{
            producer.join(10);
            consumer.join(10);
        } catch(InterruptedException e){
            fail("Something goes wrong with you code");
        }

        if(producer.isAlive()){
            fail("You wait when you don't have to");
        }
        if(buf.getData()[0] != 2 && buf.getData()[1] != 1){
            fail("The content your buffer is not good");
        }
        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    public void testTake() {
        boolean flag = true;
        BoundedBuffer buf = new BoundedBuffer(2);
        List<Integer> list = new ArrayList<>(1);
        Thread consumer = new Thread(new Consumer(buf, 3, list));
        consumer.start();

        sleep(200);

        if(!consumer.isAlive()){ // must wait
            fail("You must wait when the buffer is empty");
        }

        Thread producer = new Thread(new Producer(buf, 1, null));
        producer.start();

        sleep(200);

        try{
            producer.join(10);
            consumer.join(10);
        } catch(InterruptedException e){

        }

        if(consumer.isAlive()){
            fail("You must wait when the buffer is empty");
        }
        if(list.get(0) != 20){
            fail("You must wait when the buffer is empty");
        }

        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    public void testOffer() {
        List<Boolean> list = new ArrayList<>(2);
        BoundedBuffer buf = new BoundedBuffer(1);
        Thread producer = new Thread(new Producer(buf, 5, list));
        producer.start();

        sleep(200);

        if(producer.isAlive()){
            fail("When the buffer is not full you should put the element");
        }
        if(!list.get(0)){
            fail("You should return true when the element is added");
        }
        if(buf.getData()[0] != 42 && buf.isEmpty()){
            fail("You don't put correctly then element");
        }

        Thread producer2 = new Thread(new Producer(buf, 5, list));
        producer2.start();

        sleep(200);

        try{
            producer.join(10);
            producer2.join(10);
        } catch(InterruptedException e){

        }

        if(producer2.isAlive()){
            fail("You should wait only ms second and then abort the operation");
        }
        if(list.get(1)){
            fail("You should return false when the time is out");
        }

        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    public void testPoll() {
        BoundedBuffer buf = new BoundedBuffer(1);
        List<Integer> list = new ArrayList<>(2);
        Thread producer = new Thread(new Producer(buf, 1, null));
        producer.start();

        sleep(200);

        Thread consumer = new Thread(new Consumer(buf, 4, list));
        consumer.start();

        sleep(200);

        if(consumer.isAlive()){
            fail("When the buffer is not empty you should take an element");
        }
        if(list.get(0) != 20 && !buf.isEmpty()){
            fail("You are not taking correctly an element");
        }

        Thread consumer2 = new Thread(new Consumer(buf, 4, list));
        consumer2.start();

        sleep(200);

        if(list.get(1) != null){
            fail("You should return null when the time is out");
        }

        try{
            producer.join(10);
            consumer.join(10);
            consumer2.join(10);
        } catch (InterruptedException e){

        }

        // throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }


    class Consumer implements Runnable {

        private BoundedBuffer buf;
        private List<Integer> elems;
        private int testNbr;

        private Supplier<Integer> rng2 = () -> (int) ((Math.random() * 100) + 1);

        public Consumer(BoundedBuffer buffer, int testNbr, List<Integer> elems) {
            this.buf = buffer;
            this.elems = elems;
            this.testNbr = testNbr;
        }

        @Override
        public void run() {
            switch (testNbr) {
                case 1:
                    try {
                        this.buf.take2();
                    } catch (InterruptedException e) {

                    }
                    break;
                case 2:
                    for (int i = 0; i < 2; i++) {
                        try {
                            elems.add(this.buf.take2());
                        } catch (InterruptedException e) {

                        }
                    }
                    break;
                case 3:
                    try {
                        elems.add(this.buf.take());
                    } catch (InterruptedException e) {

                    }
                    break;
                case 4:
                    elems.add(this.buf.poll(50));
                    break;
            }


        }
    }

    class Producer implements Runnable {

        private BoundedBuffer buf;

        private int testNbr;
        private List<Boolean> results;

        private Supplier<Integer> rng = () -> (int) ((Math.random()*1000) + 1);

        public Producer(BoundedBuffer buffer, int testNbr, List<Boolean> list){
            this.buf = buffer;
            this.testNbr = testNbr;
            this.results = list;
        }

        @Override
        public void run() {
            switch(testNbr){
                case 1:
                    try{
                        this.buf.put(20);
                    } catch(InterruptedException e){

                    }
                    break;
                case 2:
                    for(int i = 0; i<3; i++) {
                        try {
                            this.buf.put(rng.get());
                        } catch (InterruptedException e) {

                        }
                    }
                    break;
                case 3:
                    for(int i = 0; i<2; i++) {
                        try {
                            this.buf.put2(i);
                        } catch (InterruptedException e) {

                        }
                    }
                    break;
                case 4:
                    for(int i = 0; i<3; i++) {
                        try {
                            this.buf.put(i);
                        } catch (InterruptedException e) {

                        }
                    }
                    break;
                case 5:
                    results.add(this.buf.offer(42, 50));
                    break;
            }
        }
    }
    //END STRIP
}
