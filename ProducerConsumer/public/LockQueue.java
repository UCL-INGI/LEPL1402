
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockQueue {

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
