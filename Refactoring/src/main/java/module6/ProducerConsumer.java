package module6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * In this task we will ask you to solve the producer/consumer problem, represented here as a concurrent bounded FIFO queue.
 * We will run simultaneously two different types of threads on your queue :
 *
 *      - Consumers, consuming (= dequeuing) elements from the queue.
 *      - Producers, producing (= enqueuing) elements to the queue.
 *
 * Your queue needs to be thread-safe : it must be able to operate in a concurrent environment.
 * It also means that threads must wait if necessary.
 *
 * A producer can't progress if there's no space left in the queue.
 * A consumer can't take element from the queue if it is empty.
 *
 * For this exercise, you will need to implement enqueue and dequeue,
 * using all the instance variables that are in the LockQueue class below.
 *
 * We strongly suggest you to look at await and signal (or signalAll) methods.
 *      - await: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html#await--
 *      - signal: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html#signal--
 *
 * You are not allowed to instantiate new locks or condition objects.
 * Do not reset value of head and tail to 0 when you reach the size of the queue.
 */
public class ProducerConsumer {
    static class LockQueue {

        public final static int SIZE = 100;

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public int head = 0;
        public int tail = 0;
        public final Integer [] cells = new Integer[SIZE];
        public int count = 0;



        public Integer dequeue() {
            //TODO YOUR CODE HERE
            return null;
        }


        public void enqueue(Integer i) {
            //TODO YOUR CODE HERE
        }

        public boolean full(){
            return this.count == SIZE;
        }

        public boolean empty(){
            return this.head == this.tail;
        }

        public int size() { return this.tail - this.head; }

    }
}
