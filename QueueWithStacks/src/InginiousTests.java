package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.GradeFeedback;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class)
public class InginiousTests {

    private Random random = new Random();

    @Test
	@Grade
    public void testEmptyNew() {
        MyQueue<Integer> q = new MyQueue<>();
        assertTrue("Your method empty() return false for newly created queue", q.empty());
    }

    @Test
	@Grade
    public void testNonEmpty() {
        MyQueue<Integer> q = new MyQueue<>();
        q.enqueue(random.nextInt());
        assertFalse("Your method empty() return true on a non-emtpy list", q.empty());
    }

    @Test
	@Grade
    public void testEmpty() {
        MyQueue<Integer> q = new MyQueue<>();
        q.enqueue(random.nextInt());
        q.dequeue();
        assertTrue("Your method empty() return false on an empty queue", q.empty());
    }

    @Test
	@Grade
    public void testQueue() {
        MyQueue<Integer> q = new MyQueue<>();
        int nbElements = random.nextInt(100);
        int [] expecteds = new int[nbElements];
        for (int i = 0; i < nbElements; i++) {
            int element = random.nextInt();
            q.enqueue(element);
            expecteds[i] = element;
        }
        for (int i = 0; i < nbElements; i++) {
            assertEquals("Your queue and dequeue methods does not work correctly",
                    expecteds[i], (int) q.dequeue());
        }
    }

    @Test(expected=NoSuchElementException.class)
    @Grade
    public void testEmptyDequeue() {
        MyQueue<Integer> q = new MyQueue<>();
        q.dequeue();
    }

    @Test
	@Grade
    public void testPeek() {
        MyQueue<Integer> q = new MyQueue<>();
        int nbElements = random.nextInt(100);
        int head = 0;
        for (int i = 0; i < nbElements; i++) {
            int element = random.nextInt();
            q.enqueue(element);
            if (i == 0)
                head = element;
            assertEquals("Your method peaks does not gives the first element in the list",
                    head, (int) q.peek());
        }
    }

    @Test(expected=NoSuchElementException.class)
    @Grade
    public void testEmptyPeek() {
        MyQueue<Integer> q = new MyQueue<>();
        q.peek();
    }

    @Test
	@Grade
    public void testPeekElement() {
        MyQueue<Integer> q = new MyQueue<>();
        q.enqueue(random.nextInt());
        q.peek();
        assertFalse("Your method peek should not empty a list of 1 element", q.empty());
    }

}
