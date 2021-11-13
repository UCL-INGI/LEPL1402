package module2;

import java.util.Iterator;

/**
 * In this task, you have to implement your own version of the famous java's ArrayList<E> : a dynamic sized array.
 * Each time you want to add an element, you must check that the item can fit and then append it at the end of the list.
 * If the array is too small, you need to resize it so that new items can be added in the future.
 * To remove an element you need to specify an index to choose which element of the list you want to remove (remove(0) for the first ... remove(size()-1) for the last).
 *
 * Never leave "blanks" in your array.
 * When you remove an element at some index, all subsequents elements must be "shifted".
 * So if your list look like this:
 *
 *     .. figure:: /course/LEPL1402/MyArrayList/before.png
 *        :scale: 100 %
 *        :alt: alternate text
 *        :align: center
 *        :figclass: align-center
 *
 * Then after the call ``remove(1)`` it should look like this:
 *
 *     .. figure:: /course/LEPL1402/MyArrayList/after.png
 *        :scale: 100 %
 *        :alt: alternate text
 *        :align: center
 *        :figclass: align-center
 *
 *
 * You also need to implement the MyArrayListIterator class.
 * Iterator is an interface you have to implement in order to make the class implementing it able to enumerate/browse/iterate over an object :
 * here, we want you to implement a FIFO order iterator over your MyArrayList.
 *
 * Pay attention, we add some constraints for this task:
 *
 *      - Your iterator don't have to implement the remove method from Iterator.
 *      - Your iterator must throw a ConcurrentModificationException when you want to get the next element but some other element has been added to the list meanwhile.
 *      - You cannot use System.arraycopy for this task.
 *      - Your constructor must throw an IndexOutOfBoundsException if the parameter is smaller than 0.
 *      - your remove method must throw an IndexOutOfBoundsException if the index parameter is smaller than 0 or greater than size()-1.
 *
 * A lot of classes implement this interface, including the official ArrayList from java.
 * We strongly encourage you to experiment how the ArrayList<E> iterator works before implementing yours.
 *
 *      - Iterator : https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
 *      - ArrayList: https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
 */
public class MyArrayList<Item> implements Iterable<Item> {

    private Object [] list;
    private int size;


    public MyArrayList(int initSize){
        // YOUR CODE HERE
    }


    /*
     * Checks if 'list' needs to be resized then add the element at the end
     * of the list.
     */
    public void enqueue(Item item){
        // YOUR CODE HERE
    }


    /*
     * Removes the element at the specified position in this list.
     * Returns the element that was removed from the list. You dont need to
     * resize when removing an element.
     */
    public Item remove(int index){
        // YOUR CODE HERE
        return null;
    }


    public int size(){
        return this.size;
    }


    public Object [] getList(){
        return this.list;
    }


    @Override
    public Iterator<Item> iterator() {
        return new MyArrayListIterator();
    }


    private class MyArrayListIterator implements Iterator<Item> {
        @Override
        public boolean hasNext() {
            // YOUR CODE HERE
            return false;
        }

        @Override
        public Item next() {
            // YOUR CODE HERE
            return null;
        }

    }

}