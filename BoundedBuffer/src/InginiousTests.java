package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Optional;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

  private void sleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException e){

        }
    }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testDoPut() throws CustomGradingResult {
    BoundedBuffer buf = new BoundedBuffer(2);

    Thread consumer = new Thread(new Consumer(buf, 1, null));
    consumer.start();

    sleep(200);

    Thread producer = new Thread(new Producer(buf, 1, null));
    producer.start();

    sleep(200);

    if(consumer.isAlive()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't notify other threads that you've add an element");
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
      throw new CustomGradingResult(TestStatus.FAILED, 0, "Something goes wrong with you code");
    }

    if(buf2.getData()[0] == null){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't add the element correctly");
    }
    if(buf2.isEmpty()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't increment the size");
    }
    if(!buf2.isFull()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't add elements correcly");
    }
    if(buf2.getPutPointer() != 0){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "After the end of the buffer length, your putPointer must return to zero");
    }
    throw new CustomGradingResult(TestStatus.SUCCESS, 1);

  }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testDoTake(){
    boolean flag = true;
    BoundedBuffer buf = new BoundedBuffer(1);
    Thread producer = new Thread(new Producer(buf, 3, null));
    producer.start();

    sleep(200);

    Thread consumer = new Thread(new Consumer(buf, 1, null));
    consumer.start();

    sleep(200);

    if(producer.isAlive()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't notify other threads that you've add an element");
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
      throw new CustomGradingResult(TestStatus.FAILED, 0, "Something goes wrong with you code");
    }

    if(!buf2.isEmpty() || list.get(0) != 0 || list.get(1) != 1){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't take the elements correctly");
    }
    if(buf2.isFull()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You don't decrement the size");
    }
    if(buf2.getTakePointer() != 0){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "After the end of the buffer length, your takePointer must return to zero");
    }
    if(buf2.getData()[0] != null || buf2.getData()[1] != null){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "When you remove an element, you must set to empty the location of this element");
    }

    throw new CustomGradingResult(TestStatus.SUCCESS, 1);
  }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testPut(){
    boolean flag = true;

    BoundedBuffer buf = new BoundedBuffer(2);

    Thread producer = new Thread(new Producer(buf, 4, null));
    producer.start();

    sleep(200);

    if(!producer.isAlive()){ //must be alive here
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You must wait when the buffer is full");
    }

    Thread consumer = new Thread(new Consumer(buf, 1, null));
    consumer.start();

    sleep(200);

    try{
        producer.join(10);
        consumer.join(10);
    } catch(InterruptedException e){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "Something goes wrong with you code");
    }

    if(producer.isAlive()){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You wait when you don't have to");
    }
    if(buf.getData()[0] != 2 && buf.getData()[1] != 1){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "The content your buffer is not good");
    }
    throw new CustomGradingResult(TestStatus.SUCCESS, 1);
  }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testTake(){
    boolean flag = true;
    BoundedBuffer buf = new BoundedBuffer(2);
    List<Integer> list = new ArrayList<>(1);
    Thread consumer = new Thread(new Consumer(buf, 3, list));
    consumer.start();

    sleep(200);

    if(!consumer.isAlive()){ // must wait
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You must wait when the buffer is empty");
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
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You must wait when the buffer is empty");
    }
    if(!list.get(0) != 20){
      throw new CustomGradingResult(TestStatus.FAILED, 0, "You must wait when the buffer is empty");
    }

    throw new CustomGradingResult(TestStatus.SUCCESS, 1);
  }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testOffer(){

  }

  @Test
  @Grade(value = 1, custom = true, customPermissions = ThreadPermissionFactory.class, cpuTimeout = 3000)
  public void testPoll(){

  }
}
