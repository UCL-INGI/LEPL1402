package module5;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FListTest {
    //BEGIN STRIP
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
    //END STRIP

    private Supplier<Integer> rng = () -> (int) (new Random().nextInt(100));

    private int[] randomNumberSup(int size){
        return Stream.generate(rng).limit(size).mapToInt(i->i).toArray();
    }
    private Object[] asArray(FList list){
        Object[] array = new Object[list.length()];
        int index= 0;
        for(Object o : list){
            array[index++] = o;
        }
        return array;
    }

    //BEGIN STRIP
    private Object[] asArray(CorrectFList list){
        Object[] array = new Object[list.length()];
        int index= 0;
        for(Object o : list){
            array[index++] = o;
        }
        return array;
    }
    //END STRIP

    @Test()
    public void testIsEmpty() {
        FList<Integer> list = FList.nil(); // list = nil
        assertTrue(list.isEmpty());

        list = list.cons(1); // list = 1|nil
        assertFalse(list.isEmpty());
    }

    @Test()
    public void testHeadTail() {
        FList<Integer> list = FList.nil(); // list = nil

        // test whether head() throws the exception
        boolean noSuch = false;
        try {
            list.head();
        }
        catch(NoSuchElementException e) {
            noSuch = true;
        }
        if(!noSuch) {
            fail();
        }

        // test whether tail() throws the exception
        noSuch = false;
        try {
            list.tail();
        }
        catch(NoSuchElementException e) {
            noSuch = true;
        }
        if(!noSuch) {
            fail();
        }

        // test whether we can access elements correctly through head() and tail()
        for(int i = 9; i >= 0; i--) {
            list = list.cons(i);
        } // list = 0|1|2|3|4|5|6|7|8|9|nil
        for(int expectedValue = 0; expectedValue < 10; expectedValue++) {
            int actualValue = list.head();
            assertEquals(actualValue, expectedValue);
            list = list.tail();
        }
    }

    @Test()
    public void testMapBasic() {
        FList<Integer> list = FList.nil();
        // list = nil

        for(int i = 9; i >= 0; i--) {
            list = list.cons(i);
        }
        // list = 0|1|2|3|4|5|6|7|8|9|nil

        list = list.map(i -> i+1);
        // list = 1|2|3|4|5|6|7|8|9|10|nil

        for(int expectedValue = 1; expectedValue <= 10; expectedValue++) {
            int actualValue = list.head();
            assertEquals(actualValue, expectedValue);
            list = list.tail();
        }
    }

    @Test()
    public void testFilterBasic() {
        FList<Integer> list = FList.nil();
        // list = nil

        for(int i = 10; i > 0; i--) {
            list = list.cons(i);
        }
        // list = 1|2|3|4|5|6|7|8|9|10|nil

        list = list.filter(i -> i%2 == 0);
        // list = 2|4|6|8|10|nil

        for(int expectedValue = 2; expectedValue <= 10; expectedValue+=2) {
            int actualValue = list.head();
            assertEquals(actualValue, expectedValue);
            list = list.tail();
        }
    }

    @Test()
    public void testLength() {

        int[] sizes = randomNumberSup(100);

        for(int i = 0 ; i<100 ; i++) {
            FList<Integer> list = FList.nil();
            for(int j = 0 ; j < sizes[i] ; j++) {
                list = list.cons(new Random().nextInt(100));
            }
            assertEquals(list.length(), sizes[i]);
        }

        FList<Integer> list = FList.nil();
        assertEquals(list.length(), 0);
    }

    //BEGIN STRIP
    @Test()
    public void testMap() {
        FList<Integer> list = FList.nil();
        CorrectFList<Integer> correctList = CorrectFList.nil();
        int[] values = randomNumberSup(100);

        for(int i = 0 ; i < 100 ; i++) {
            list = list.cons(values[i]);
            correctList = correctList.cons(values[i]);
        }

        Object[] result = asArray(list.map( a -> a*a-42));
        Object[] answer = asArray(correctList.map(a -> a*a-42));

        assertArrayEquals(result, answer);

    }


    @Test()
    public void testFilter() {
        FList<Integer> list = FList.nil();
        CorrectFList<Integer> correctList = CorrectFList.nil();
        int[] values = randomNumberSup(1000);

        for(int i = 0 ; i < 1000 ; i++) {
            list = list.cons(values[i]);
            correctList = correctList.cons(values[i]);
        }

        Object[] result = asArray(list.filter( a -> a%5==0));
        Object[] answer = asArray(correctList.filter(a -> a%5==0));

        assertArrayEquals(result, answer);

    }
    //END STRIP

    @Test()
    public void testIterator() {
        FList<Integer> list = FList.nil();

        int[] values = randomNumberSup(100);

        for(int i = 0 ; i < 100; i++) {
            list = list.cons(values[i]);
        }
        int index= 99;
        for(Integer i : list) {
            assertEquals(values[index--],i.intValue());
        }

        boolean noSuch = false, unsupported=false;

        try {
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            it.next();
        }
        catch(NoSuchElementException e) {
            noSuch = true;
        }

        try {
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            fl = fl.cons(1);
            it.remove();
        }
        catch(UnsupportedOperationException e) {
            unsupported = true;
        }

        if(!unsupported && !noSuch) {
            fail("You forgot about the exceptions");
        }
        if(!unsupported) {
            fail("You forgot about the UnsupportedOperationException");
        }
        if(!noSuch) {
            fail("You forgot about the NoSuchElementException");
        }
    }
}
