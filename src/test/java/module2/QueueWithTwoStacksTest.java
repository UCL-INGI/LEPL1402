package module2;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class QueueWithTwoStacksTest {

    //BEGIN STRIP
    private Random random = new Random();
    //END STRIP

    @Test
    public void testEmptyNew() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        assertTrue("Your method empty() return false for newly created queue", q.empty());
    }

    @Test
    public void testNonEmpty() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        q.enqueue(random.nextInt());
        assertFalse("Your method empty() return true on a non-emtpy list", q.empty());
    }

    @Test
    public void testEmpty() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        q.enqueue(random.nextInt());
        q.dequeue();
        assertTrue("Your method empty() return false on an empty queue", q.empty());
    }

    @Test
    public void testQueue() {
        QueueWithTwoStacks<Integer> q1 = new QueueWithTwoStacks<>();
        for (int i = 0; i < 10; i++) {
            q1.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals("Your queue and dequeue methods does not work correctly",
                    i, (int) q1.dequeue());
        }

        //BEGIN STRIP
        QueueWithTwoStacks<Integer> q2 = new QueueWithTwoStacks<>();
        int nbElements = random.nextInt(100);
        int [] expecteds = new int[nbElements];
        for (int i = 0; i < nbElements; i++) {
            int element = random.nextInt();
            q2.enqueue(element);
            expecteds[i] = element;
        }
        for (int i = 0; i < nbElements; i++) {
            assertEquals("Your queue and dequeue methods does not work correctly",
                    expecteds[i], (int) q2.dequeue());
        }
        //END STRIP
    }

    @Test(expected=NoSuchElementException.class)
    public void testEmptyDequeue() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        q.dequeue();
    }

    @Test
    public void testPeek() {
        //BEGIN STRIP
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
            assertEquals("Your method peaks does not gives the first element in the list",
                    0, (int) q.peek());
        }
        //END STRIP
        QueueWithTwoStacks<Integer> q2 = new QueueWithTwoStacks<>();
        int nbElements = random.nextInt(100);
        int head = 0;
        for (int i = 0; i < nbElements; i++) {
            int element = random.nextInt();
            q2.enqueue(element);
            if (i == 0)
                head = element;
            assertEquals("Your method peaks does not gives the first element in the list",
                    head, (int) q2.peek());
        }
    }

    @Test(expected=NoSuchElementException.class)
    public void testEmptyPeek() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        q.peek();
    }

    @Test
    public void testPeekElement() {
        QueueWithTwoStacks<Integer> q = new QueueWithTwoStacks<>();
        q.enqueue(random.nextInt());
        q.peek();
        assertFalse("Your method peek should not empty a list of 1 element", q.empty());
    }

}
