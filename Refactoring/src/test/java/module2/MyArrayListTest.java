package module2;

import org.junit.Test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class MyArrayListTest {

    Supplier<Integer> rng = () -> (int) (Math.random() * 100);

    @Test
    public void testSize(){

        MyArrayList<Integer> list = new MyArrayList<Integer>(4);
        assertEquals(4, list.size());

        assertEquals(0, list.size());
        list.enqueue(1);
        assertEquals(1, list.size());
        list.enqueue(1);
        assertEquals(2, list.size());
        list.enqueue(2);
        assertEquals(3, list.size());
        list.remove(2);
        assertEquals(2, list.size());

    }


    @Test
    public void testResize(){

        int init = rng.get();
        MyArrayList<Integer> list = new MyArrayList<Integer>(init);

        Integer [] elements = Stream.generate(rng).limit(init*3).toArray(Integer[]::new);
        Arrays.stream(elements).forEach(list::enqueue);

        assertEquals(3*init, list.size());

        for(int i = 0; i < init*3; i++){
            assertEquals(elements[i], list.getList()[i]);
        }

    }


    @Test
    public void testRemove(){

        MyArrayList<Integer> simple = new MyArrayList<Integer>(5);
        simple.enqueue(1);
        simple.enqueue(2);
        simple.enqueue(3);
        simple.enqueue(4);
        simple.enqueue(5);
        Integer res = simple.remove(1); // removes "2"

        assertEquals(2, (int)res);
        assertEquals((int)simple.remove(1), 3);

        MyArrayList<Integer> list = new MyArrayList<Integer>(20);
        Integer [] elements = Stream.generate(rng).limit(10).toArray(Integer[]::new);
        Arrays.stream(elements).forEach(list::enqueue);



        for(int i = 0; i < 10; i++){
            list.remove(i);
            assertEquals(9, list.size());
            assertNull(list.getList()[9]);
            for(int j = 0; j < 9; j++){
                Integer ret = list.remove(0);
                assertNotNull(ret);
            }
            assertEquals(0, list.size());
            list = new MyArrayList<Integer>(20);
            Arrays.stream(elements).forEach(list::enqueue);
        }


    }



    @Test
    public void testIterator(){

        MyArrayList<Integer> list = new MyArrayList<Integer>(30);
        Integer[] elements = Stream.generate(rng).limit(30).toArray(Integer[]::new);
        Arrays.stream(elements).forEach(list::enqueue);

        Iterator<Integer> it = list.iterator();

        for(int i = 0; i < 30; i++){
            assertTrue(it.hasNext());
            assertEquals(elements[i], it.next());
        }

        assertFalse(it.hasNext());

    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsConstructor(){
        MyArrayList<Integer> list = new MyArrayList<Integer>(-1);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfBoundsRemove(){
        MyArrayList<Integer> list = new MyArrayList<Integer>(2);
        list.enqueue(1);
        list.remove(2);
    }


    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModification(){
        MyArrayList<Integer> list = new MyArrayList<Integer>(2);
        list.enqueue(1);
        list.enqueue(2);
        list.enqueue(3);

        Iterator<Integer> it = list.iterator();

        list.enqueue(4);
        it.next();
    }

}