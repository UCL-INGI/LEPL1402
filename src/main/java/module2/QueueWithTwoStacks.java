package module2;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * In this short exercise we will ask you to implement a FIFO queue using two stacks (provided as two Java Stack<E>).
 * We need you to provide 4 methods : enqueue, dequeue, peek, empty.
 * You can use the Stack API, but you can't instantiate a new Stack.
 * Use s1 and s2 from the MyQueue class below.
 *
 *      - Stack API: https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html
 */
public class QueueWithTwoStacks<E> {

    Stack<E> s1;
    Stack<E> s2;

    private E front;

    /*
     * Constructor
     */
    public QueueWithTwoStacks() {
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
