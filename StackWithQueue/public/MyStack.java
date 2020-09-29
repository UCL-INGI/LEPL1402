import java.util.LinkedList;

public class MyStack<E> {

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
