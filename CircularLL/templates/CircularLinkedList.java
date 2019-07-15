package src;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class CircularLinkedList<Item> implements Iterable<Item> {

    private int n;          // size of the list
    private Node last;   // trailer of the list

    // helper linked list class
    private class Node {

        private Item item;
        private Node next;

        private Node(Item item){
            this.item = item;
            this.next = null;
        }

    }

    public CircularLinkedList() {
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Node getLast(){
        return last;
    }

    public Item getItem(Node n){
        return n.item;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
    @   @student_enqueue@@
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
    @   @student_remove@@
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     *
     */
    private class ListIterator implements Iterator<Item> {
    @   @student_iterator@@
    }


}
