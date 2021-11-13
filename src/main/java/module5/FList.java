package module5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Functional programming is an increasingly important programming paradigm.
 *  In this paradigm, data structures are immutable.
 *      - Functional programming: https://en.wikipedia.org/wiki/Functional_programming
 *      - immutable: https://en.wikipedia.org/wiki/Purely_functional_data_structure
 *
 *  You are asked to implement an immutable list called FList that can be used in functionnal programming.
 *
 *  You should first make sure that you understood the code that is given to you and what is asked from you before trying to work out an implementation.
 *  Don't be intimidated by the size of size of the class we're giving you:
 *  none of its methods should require more than 5 lines of code from you. Make sure you code recursively and not iteratively (which would be much harder)!
 *
 *  Since you know how your code should work, we strongly recommend you to test it on your computer before trying to submit.
 *  The iterator is the most critical part of this task, so if it doesn't work, most of the tests will not work.
 */
public abstract class FList<A> implements Iterable<A> {
    /**
     * Returns an empty FList
     */
    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }

    /**
     * Returns a new FList obtained by prepending a to this list
     */
    public final FList<A> cons(final A a) {
        return new Cons(a, this);
    }

    /**
     * @return the number of elements in this list
     */
    public abstract int length();

    /**
     * @return true if the list is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * @return the head of the list.
     * @throws NoSuchElementException if the list is empty
     */
    public abstract A head();

    /**
     * @return the tail of the list (i.e. the sublist without the first element of this list)
     * @throws NoSuchElementException if the list is empty
     */
    public abstract FList<A> tail();

    /**
     * Returns a new list containing the outputs obtained by applying function f to each element of this list
     */
    public abstract <B> FList<B> map(Function<A,B> f);

    /**
     * Returns a new list containing only the elements from this list that fullfill predicate f (i.e. f(elem) == true)
     */
    public abstract FList<A> filter(Predicate<A> f);


    public Iterator<A> iterator() {
        return new Iterator<A>() {
            // Do whatever you want here

            public boolean hasNext() {
                // TODO
                return false;
            }

            public A next() {
                // TODO
                return null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();

        // TODO
        //BEGIN STRIP
        @Override
        public int length() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public A head() {
            return null;
        }

        @Override
        public FList<A> tail() {
            return null;
        }

        @Override
        public <B> FList<B> map(Function<A, B> f) {
            return null;
        }

        @Override
        public FList<A> filter(Predicate<A> f) {
            return null;
        }
        //END STRIP
    }

    private static final class Cons<A> extends FList<A> {
        // TODO
        //BEGIN STRIP
        private final A elem;
        private final FList<A> next;
        public Cons(final A elem, final FList<A> next) {
            this.elem = elem;
            this.next = next;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public A head() {
            return null;
        }

        @Override
        public FList<A> tail() {
            return null;
        }

        @Override
        public <B> FList<B> map(Function<A, B> f) {
            return null;
        }

        @Override
        public FList<A> filter(Predicate<A> f) {
            return null;
        }
        //END STRIP
    }
}
