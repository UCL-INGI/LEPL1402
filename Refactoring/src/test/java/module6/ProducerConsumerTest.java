package module6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class ProducerConsumerTest {
    //BEGIN STRIP
    /*
     * @TODO : This test suite should be improved, as there is currently no way to verify that the student put his/her
     *   unlock() instructions in a 'finally { }' block.
     */
    @Test
    public void testOverlappingThreads(){

        ProducerConsumer.LockQueue queue = new ProducerConsumer.LockQueue();
        int nThreads = 10;
        int nOps = 1000; // nOps should ALWAYS be greater than the LockQueue fixed size (which is 100)
        ExecutorService service = Executors.newFixedThreadPool(nThreads);

        CountDownLatch count = new CountDownLatch(1);
        AtomicBoolean running = new AtomicBoolean(false);
        AtomicInteger overlap = new AtomicInteger(0);

        Collection<Future<Integer>> retVals = new ArrayList<>(nThreads);

        for(int i = 0; i < nOps*2; i++ ){
            final Integer elem = i;
            retVals.add(
                    service.submit( // Initiate threads work here, but block them immediately
                            () -> {
                                count.await(); // wait for the count to unleash the threads.
                                if(running.get()){
                                    overlap.incrementAndGet();
                                }
                                running.set(true);
                                int ret = -1;
                                if(elem % 2 == 0){
                                    queue.enqueue(elem);
                                } else {
                                    ret = queue.dequeue();
                                }
                                running.set(false);
                                return ret;
                            }
                    )
            );
        }

        count.countDown();
        // Ensure that threads will run in parallel, as it will hold them while the countdown is not
        // over and then release them all at once.
        ArrayList<Integer> actuals = new ArrayList<>();
        for(Future<Integer> fi : retVals){
            try {
                actuals.add(fi.get());
            } catch (InterruptedException | ExecutionException e) {
                fail(e.getMessage());
            }
        }
        assertEquals(nOps*2, actuals.size());
        assertEquals(0, queue.size());
        assertEquals(nOps, queue.head);
        assertEquals(nOps, queue.tail);
        assertTrue(queue.empty()); // same number of enqueue and dequeue, should be empty in the end.

        Set<Integer> set = new HashSet<>();
        for(Integer i : actuals){
            if(i != -1){
                assertTrue(set.add(i));
            }
        }

        if(overlap.get() == 0) testOverlappingThreads();
        // if overlap is 0 (could happen, but with a VERY small probability) rerun the test.
    }

    @Test
    public void testNotFullCondition(){

        ProducerConsumer.LockQueue q = new ProducerConsumer.LockQueue();
        Thread t = new Thread(new Producer(q, 100));
        t.start();
        try{
            t.join();
        } catch (InterruptedException e){}
        // at this point, q is full
        assertTrue(q.full());
        assertEquals(100, q.size());

        Thread waiter = new Thread(new Producer(q, 1));
        Thread other = new Thread(new Producer(q, 1));
        waiter.start();
        other.start();

        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {}

        assertTrue(waiter.isAlive() && other.isAlive()); // Means it is blocked (as it should)

        Thread cons = new Thread(new Consumer(q, 1));
        cons.start();

        try{
            cons.join();
            Thread.sleep(1500);
        } catch (InterruptedException e){}

        assertFalse(!waiter.isAlive() && !other.isAlive());
        assertFalse(waiter.isAlive() && other.isAlive()); // now the other producer should be able to enqueue since one element
        // has been consumed, so the thread "waiter" XOR "other" should be dead, because its job is finished.
    }

    @Test
    public void testNotEmptyCondition(){
        ProducerConsumer.LockQueue q = new ProducerConsumer.LockQueue();
        assertTrue(q.empty());

        Thread waiter = new Thread(new Consumer(q, 1));
        Thread other = new Thread(new Consumer(q, 1));
        waiter.start();
        other.start();

        try{
            Thread.sleep(1500);
        } catch (InterruptedException e) {}

        assertTrue(waiter.isAlive() && other.isAlive()); // Means it is blocked (as it should)

        Thread cons = new Thread(new Producer(q, 1));
        cons.start();

        try{
            cons.join();
            Thread.sleep(1500);
        } catch (InterruptedException e){}

        assertFalse( !waiter.isAlive() && !other.isAlive());
        assertFalse(waiter.isAlive() && other.isAlive());
        // has been consumed, so the thread "waiter" should be dead, because its job is finished.
        assertTrue(q.empty());
    }

    @Test
    public void testCount(){
        // Simple test, just to verify that student did not forget to update count.
        ProducerConsumer.LockQueue q = new ProducerConsumer.LockQueue();
        assertEquals(0, q.count);
        q.enqueue(1);
        assertEquals(1, q.count);
        q.dequeue();
        assertEquals(0, q.count);
    }

    class Producer implements Runnable {

        private final int nb_tests;
        private ProducerConsumer.LockQueue q ;

        public Producer(ProducerConsumer.LockQueue q) {
            this.q = q;
            nb_tests = 1000;
        }

        public Producer(ProducerConsumer.LockQueue q, int nb_tests) {
            this.q = q;
            this.nb_tests = nb_tests;
        }


        public void run() {
            for (int i=0; i<nb_tests; i++) {
                q.enqueue(i);
            }
        }
    }

    class Consumer implements Runnable {

        private final int nb_tests ;
        private ProducerConsumer.LockQueue q ;

        public Consumer(ProducerConsumer.LockQueue q) {
            this.q = q;
            nb_tests = 1000;
        }

        public Consumer(ProducerConsumer.LockQueue q, int nb_tests) {
            this.q = q;
            this.nb_tests = nb_tests;
        }

        public void run(){
            for (int i=0; i<nb_tests; i++) {
                q.dequeue();
            }
        }
    }
    //END STRIP
}
