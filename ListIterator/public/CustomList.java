import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import java.lang.Iterable;
import java.util.Iterator;

public class CustomList<Item> implements Iterable<Item> {

    private int size;
    private ListNode<Item> first;
    private ListNode<Item> last;

    public CustomList() {
        first=null;
        last=null;
        size = 0;
    }

    /**
     * Add an element to the list
     *
     * @param The element to add at the end of the list
     */
    public void add(Item e) {
        ListNode<Item> n = new ListNode<>(e);
        if (first == null) {
            first = n;
            last = n;
        } else {
            last.setNext(n);
            last = n;
        }
        size += 1;
    }

    /**
     * Pop the first element of the list
     *
     * @return The first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    public Item pop() {
        if (first == null)
            throw new NoSuchElementException();
        Item toReturn = first.getItem();
        first = first.getNext();
        size -= 1;
        return toReturn;
    }

    private class CustomIterator implements Iterator<Item> {

        // You can add here the methods/constructors/variables that
        // you need

        /**
         * Still has element to iterate
         *
         * @return true if there is still an element
         *         that has not been iterated over,
         *         false otherwise
         */
        @Override
        public boolean hasNext() {
            // TODO
            return false;
        }

        /**
         * Next element in the iteration
         *
         * @return The next element in the iteration.
         *         The list is iterated from first to last
         *
         * @throws ConcurrentModificationException if the list
         *         has been modified during the iteration
         * @throws NoSuchElementException if there is no more
         *         element to iterate over
         */
        @Override
        public Item next() {
            // TODO
            return null;
        }
    }

    /**
     * Return an iterator over the list
     *
     * @return An iterator that iterate from first to last
     */
    @Override
    public Iterator<Item> iterator() {
        return new CustomIterator();
    }

}
