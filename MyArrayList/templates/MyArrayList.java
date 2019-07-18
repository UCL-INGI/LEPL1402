package src;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class MyArrayList<Item> implements Iterable<Item> {

    private Object [] list;
    private int size;


    public MyArrayList(int initSize){
    @   @student_constructor@@
    }


    /*
     * Checks if 'list' needs to be resized then add the element at the end
     * of the list.
     */
    public void enqueue(Item item){
    @   @student_enqueue@@
    }


    /*
     * Removes the element at the specified position in this list.
     * Returns the element that was removed from the list. You dont need to
     * resize when removing an element.
     */
    public Item remove(int index){
    @   @student_remove@@
    }

    public int size(){
        return this.size;
    }

    @   @student_utils@@

    /*
     * Dont give this method to students, they dont need to implement nor use it.
     * Only serves for testing purposes.
     */
    public int listSize(){
        return this.list.length;
    }


    @Override
    public Iterator<Item> iterator() {
        return new MyArrayListIterator();
    }


    private class MyArrayListIterator implements Iterator<Item> {
    @   @student_iterator@@
    }


}
