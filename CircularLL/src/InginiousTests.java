package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/*
 * Most of the code of this test suite is taken (and adapted) from an exercise of LSINF1121 course.
 * Original authors : Pierre Schaus, John Aoga
 * Modified by : Alexandre Rucquoy, Jacques Yakoub
 */
@RunWith(GradingRunner.class)
public class InginiousTests {

    private Supplier<Integer> rng = () -> (int) (Math.random()*100); // never generate 0


    @Test()
    @Grade()
    @GradeFeedbacks({@GradeFeedback(onFail=true, message = "The size \"n\" of your list is incorrect"),
            @GradeFeedback(onSuccess = true, message = "")})
    public void testSize(){
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        assertEquals(0, list.size());

        for(int i = 0; i < 10; i ++){
            list.enqueue(i);
            assertEquals(i+1, list.size());
        }

        assertEquals(10, list.size());

        for(int i = 9; i >= 0; i--){
            list.remove(i);
            assertEquals(i, list.size());
        }
    }


    @Test
    @Grade
    @GradeFeedback(onFail=true, message = "The Node reference \"last\" is not pointing to the expected node")
    public void testLastReference(){
        CircularLinkedList<Integer> list = new CircularLinkedList<>();list.enqueue(1);
        assertEquals(1, (int) list.getItem(list.getLast()));
        list.enqueue(2);
        assertEquals(2, (int) list.getItem(list.getLast()));
        list.enqueue(3);
        assertEquals(3, (int) list.getItem(list.getLast()));
        list.remove(2); // remove "3"
        assertEquals(2, (int) list.getItem(list.getLast()));

    }

    @Test
    @Grade()
    public void testIteratorList() {

        for (int i = 0; i < 20; i++) {

            CircularLinkedList<Integer> a = new CircularLinkedList<>();
            assertEquals(0, a.size());
            a.enqueue(i);
            assertEquals(1, a.size());
            Iterator itera = a.iterator();
            assertTrue(itera.hasNext());
            assertEquals(i,itera.next());

            CircularLinkedList<Integer> b = new CircularLinkedList<>();
            b.enqueue(i);
            b.remove(0);
            Iterator iterb = b.iterator();
            assertFalse(iterb.hasNext());

        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Grade()
    @GradeFeedback(onFail=true, message = "Your code does not throw an IndexOutOfBoundsException when it should")
    public void testOutOfBound() {
        CircularLinkedList<Integer> a = new CircularLinkedList<>();
        a.enqueue(3);
        a.remove(1);
    }


    @Test(expected = ConcurrentModificationException.class)
    @Grade()
    @GradeFeedback(onFail=true, message = "Your code does not throw a ConcurrentModificationException when it should")
    public void testConcurrentModificationNext() {
        CircularLinkedList<Integer> a = new CircularLinkedList<>();
        Iterator iter = a.iterator();
        a.enqueue(3);
        iter.next();
    }


    @Test
    @Grade
    public void testRandom(){

        for (int i = 0; i < 50; i++) {

            CircularLinkedList<Integer> std = new CircularLinkedList<>(); // student code
            LinkedList<Integer> jdk = new LinkedList<>(); //Java implem

            for (int k = 0; k < 100; k++) {
                int v = rng.get();
                std.enqueue(v);
                jdk.add(v);
            }

            if (i%2 == 0) {
                std.remove(10);
                jdk.remove(10);
                std.remove(0);
                jdk.remove(0);
                std.remove(std.size()-1);
                jdk.remove(jdk.size()-1);
            }

            Iterator<Integer> stdIter = std.iterator();
            Iterator<Integer> jdkIter = jdk.iterator();
            assertEquals(jdk.size(),std.size());

            while (jdkIter.hasNext()) {
                assertTrue(stdIter.hasNext());
                assertEquals(jdkIter.next(), stdIter.next());
            }

            assertFalse(stdIter.hasNext());

        }

    }


}
