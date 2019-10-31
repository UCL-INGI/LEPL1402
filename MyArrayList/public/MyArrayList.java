import java.util.ConcurrentModificationException;
import java.util.Iterator;

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
        // YOUR CODE HERE
    }

}