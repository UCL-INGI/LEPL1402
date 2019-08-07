package src;

import java.util.Stack;

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

    public void enqueue(E elem) {
      @@enqueue@@
    }

    public E dequeue() {
      @@dequeue@@
    }

    public E peek() {
      @@peek@@
    }

    public boolean empty() {
      @@empty@@
    }

}
