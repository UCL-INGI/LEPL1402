import java.util.Stack;
import java.util.NoSuchElementException;

public class MyQueue<E> {

    Stack<E> s1;
    Stack<E> s2;

    private E front;

    /*
     * Constructor
     */
    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
        this.front = null;
    }

    /**
     * Add an element to the end of the list
     *
     * @param elem The element to add
     */
    public void enqueue(E elem) {
        // TODO
    }

    /**
     * Remove the first element from the queue
     *
     * @return The oldest element in the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public E dequeue() {
        // TODO
        return null;
    }

    /**
     * Peek at the first element of the queue
     *
     * @return The first element of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public E peek() {
        // TODO
        return null;
    }

    /**
     * @return true if the queue is empty
     */
    public boolean empty() {
        // TODO
        return false;
    }

}
