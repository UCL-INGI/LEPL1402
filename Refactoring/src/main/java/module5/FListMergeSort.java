package module5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * To complete this task, you're asked to implement the MergeSort algorithm for functional lists using only FList.
 * Arrays, ArrayList, etc. are prohibited for this exercise.
 *
 * You will be using the same FList class as in last exercise. We provide you with a template for this class here_.
 * In order to work on this exercise locally, you should first implement FList yourself (i.e., do the previous exercise)
 * before starting  this one. This will allow you to copy/paste your FList class into this task's IntelliJ Project and
 * work out your MergeSort implementation locally.
 *
 * The method we want you to implement has the following signature.
 *
 * Note that you have to implement merge recursively and that you are allowed (and encouraged!) to create other methods.
 * One last tip you might want to think about: when splitting a list into 2 sublists, the MergeSort algorithm works regardless
 * of how you decide to split the list into 2 smaller lists. This essentially means you could split list [0, 1, 2, 3, 4, 5] into
 * sublists [0, 2, 4] and [1, 3, 5] instead of the usual [0, 1, 2] and [3, 4, 5].
 */
public class FListMergeSort {
    //BEGIN STRIP
    public static int counter=0;

    public static void resetCounter(){
        counter=0;
    }
    //END STRIP

    /*
     * This method receives an FList and returns a new FList containing the same values but sorted in ascending order.
     *
     */
    public static FList<Integer> mergeSort(FList<Integer> fList){
        //BEGIN STRIP
        counter++;
        //END STRIP

        // TODO By Student
        return null;
    }

    // TODO Complete if necessary

    abstract static class CorrectFList<A> implements Iterable<A> {
        /**
         * Returns an empty FList
         */
        public static <A> CorrectFList<A> nil() {
            return (Nil<A>) Nil.INSTANCE;
        }

        /**
         * Returns a new FList obtained by prepending a to this list
         */
        public final CorrectFList<A> cons(final A a) {
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
        public abstract CorrectFList<A> tail();

        /**
         * Returns a new list containing the outputs obtained by applying function f to each element of this list
         */
        public abstract <B> CorrectFList<B> map(Function<A,B> f);

        /**
         * Returns a new list containing only the elements from this list that fullfill predicate f (i.e. f(elem) == true)
         */
        public abstract CorrectFList<A> filter(Predicate<A> f);


        public Iterator<A> iterator() {
            return new Iterator<A>() {
                // Do whatever you want here
                CorrectFList<A> cur = CorrectFList.this;

                public boolean hasNext() {
                    return !cur.isEmpty();
                }

                public A next() {
                    A elem = cur.head();
                    cur = cur.tail();
                    return elem;
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }


        private static final class Nil<A> extends CorrectFList<A> {
            public static final Nil<Object> INSTANCE = new Nil();

            @Override
            public int length() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public A head() {
                throw new NoSuchElementException();
            }

            @Override
            public CorrectFList<A> tail() {
                throw new NoSuchElementException();
            }

            @Override
            public <B> CorrectFList<B> map(Function<A, B> f) {
                return CorrectFList.nil();
            }

            @Override
            public CorrectFList<A> filter(Predicate<A> f) {
                return CorrectFList.nil();
            }
        }

        private static final class Cons<A> extends CorrectFList<A> {
            private final A elem;
            private final CorrectFList<A> next;

            public Cons(final A elem, final CorrectFList<A> next) {
                this.elem = elem;
                this.next = next;
            }

            @Override
            public int length() {
                return 1 + tail().length();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public A head() {
                return elem;
            }

            @Override
            public CorrectFList<A> tail() {
                return next;
            }

            @Override
            public <B> CorrectFList<B> map(Function<A, B> f) {
                return tail().map(f).cons(f.apply(elem));
            }

            @Override
            public CorrectFList<A> filter(Predicate<A> f) {
                if(f.test(elem))
                    return tail().filter(f).cons(elem);
                else
                    return tail().filter(f);
            }
        }
    }
}
