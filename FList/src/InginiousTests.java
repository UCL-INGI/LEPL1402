package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.Random;
import templates.*;

import static org.junit.Assert.*;

abstract class CorrectFList<A> implements Iterable<A> {
    /**
     * Returns an empty FList
     */
    public static <A> CorrectFList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }

    /**
     * Creates a new list with a prepended to this list
     */
    public final CorrectFList<A> cons(final A a) {
        return new Cons(a, this);
    }

    /**
     * @return the number of elements in the list
     */
    public abstract int length();

    /**
     * @return true if the list is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * @return the head of the list.
     * Throws IllegalArgumentException if the list is empty
     */
    public abstract A head();

    /**
     * @return the tail of the list (i.e. the sublist without the first element of this list)
     * Throws IllegalArgumentException if the list is empty
     */
    public abstract CorrectFList<A> tail();

    /**
     * Returns a new list with the output of the function f applied to each element of this list
     */
    public abstract <B> CorrectFList<B> map(Function<A,B> f);

    /**
     * Creates a new list with only the element that fullfill the predicate f (i.e. f(elem) == true).
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

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private boolean flag = true;
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


    private Object[] asArray(CorrectFList list){
        Object[] array = new Object[list.length()];
        int index= 0;
        for(Object o : list){
            array[index++] = o;
        }
        return array;
    }

    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message = "You failed the example", onFail = true, onTimeout = true)
    public void testExample(){

        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }
        int index = 10;
        list = list.map(i -> i+1);
        // will print 1,2,...,11
        for (Integer i: list) {
            int k = i.intValue();
            assertEquals(k, index);
            index--;
        }

        list = list.filter(i -> i%2 == 0);
        // will print 2,4,6,...,10
        index = 10;
        for (Integer i: list) {
            int k = i.intValue();
            assertEquals(k, index);
            index-=2;
        }
        flag = false;

    }

    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message="The length you return is not correct", onFail=true)
    public void testLength(){

        int[] sizes = randomNumberSup(100);

        for(int i = 0 ; i<100 ; i++){

            FList<Integer> list = FList.nil();

            for(int j = 0 ; j < sizes[i] ; j++){
                    list = list.cons(new Random().nextInt(100));
            }

            assertEquals(list.length(), sizes[i]);
        }

        FList<Integer> list = FList.nil();
        assertEquals(list.length(), 0);

    }


    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message="Your map function don't work as expected", onFail=true)
    public void testMap(){
        FList<Integer> list = FList.nil();
        CorrectFList<Integer> correctList = CorrectFList.nil();
        int[] values = randomNumberSup(100);

        for(int i = 0 ; i < 100 ; i++){
            list = list.cons(values[i]);
            correctList = correctList.cons(values[i]);
        }

        Object[] result = asArray(list.map( a -> a*a-42));
        Object[] answer = asArray(correctList.map(a -> a*a-42));

        assertArrayEquals(result, answer);

    }


    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message="Your filter function don't work as expected", onFail=true)
    public void testFilter(){
        FList<Integer> list = FList.nil();
        CorrectFList<Integer> correctList = CorrectFList.nil();
        int[] values = randomNumberSup(1000);

        for(int i = 0 ; i < 1000 ; i++){
            list = list.cons(values[i]);
            correctList = correctList.cons(values[i]);
        }

        Object[] result = asArray(list.filter( a -> a%5==0));
        Object[] answer = asArray(correctList.filter(a -> a%5==0));

        assertArrayEquals(result, answer);

    }

	@Test()
    @Grade(value=10, custom=true, cpuTimeout=100)
    public void testIterator() throws CustomGradingResult{
        FList<Integer> list = FList.nil();

        int[] values = randomNumberSup(100);

        for(int i = 0 ; i < 100; i++){
            list = list.cons(values[i]);
        }
        int index= 99;
        for(Integer i : list){
            assertEquals(values[index--],i.intValue());
        }

        boolean noSuch = false, concurr=false;

        try{
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            it.next();
        }catch(NoSuchElementException e){
            noSuch = true;
        }

        try{
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            fl = fl.cons(1);
            it.remove();
        }catch(UnsupportedOperationException e){
            concurr = true;
        }

        if(!concurr && !noSuch){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "You forgot about the exceptions");
        }
        if(!concurr){
            throw new CustomGradingResult(TestStatus.FAILED, 3, "You forgot about the UnsupportedOperationException");
        }
        if(!noSuch){
            throw new CustomGradingResult(TestStatus.FAILED, 3 , "You forgot about the NoSuchElementException");
        }

        throw new CustomGradingResult(TestStatus.SUCCESS, 10 , "You nailed it!");

    }


}
