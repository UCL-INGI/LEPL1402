package module2;

import java.util.LinkedList;

/**
 * In this short exercise we will ask you to implement a LIFO stack using a queue (provided as a Java ``LinkedList<E>``).
 * We need you to provide 4 methods : push, pop, peek, empty.
 * You can't use the LinkedList API except for the methods listed below:
 *     - add
 *     - remove
 *     - peek
 *     - isEmpty
 *     - size
 *
 * The challenge of this exercise is to use only the queue that is provided as instance variable: queue.
 * In other words, you can't instantiate a new LinkedList anywhere.
 */
class MyStack<E> {

    private LinkedList<E> queue;

    /*
     * Constructor
     */
    public MyStack() {
        this.queue = new LinkedList<>();
    }

    /**
     * Push an element on the stack
     *
     * @param elem the Element to push
     */
    public void push(E elem) {
    }

    /**
     * Remove the element at the top of the stack
     *
     * @return The element at the top of the stack
     */
    public E pop() {
        return null;
    }

    /**
     * Look at the element at the top of the stack
     *
     * @return The element at the top of the stack
     */
    public E peek() {
        return null;
    }

    /**
     * Is the stack empty
     *
     * @return True if there is no element in the stack
     *         and false otherwise
     */
    public boolean empty() {
        return false;
    }

}
