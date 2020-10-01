package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    Random random = new Random();

    @Test
    @Grade
    public void hasNextOnEmpty() {
        CustomList<Integer> list = new CustomList<>();
        Iterator<Integer> iterator = list.iterator();
        assertFalse("Your method hasNext return true on an new empty list", iterator.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    @Grade
    public void nextOnEmpty() {
        CustomList<Integer> list = new CustomList<>();
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
    }

    @Test
    @Grade
    public void testHasNext() {
        CustomList<Integer> list = new CustomList<>();
        int numberElements = random.nextInt(90) + 10;
        for (int i = 0; i < numberElements; i++) {
            list.add(random.nextInt());
        }

        Iterator<Integer> iterator = list.iterator();
        for (int i = 0; i < numberElements; i++) {
            assertTrue("Your method hasNext return false to early", iterator.hasNext());
            iterator.next();
        }
    }

    @Test
    @Grade
    public void testIterationOrder() {
        CustomList<Integer> list = new CustomList<>();
        int numberElements = random.nextInt(90) + 10;
        int [] expectedElements = new int[numberElements];
        for (int i = 0; i < numberElements; i++) {
            int e = random.nextInt();
            list.add(e);
            expectedElements[i] = e;
        }

        Iterator<Integer> iterator = list.iterator();
        int idx = 0;
        while (iterator.hasNext() && idx < numberElements) {
            int expected = expectedElements[idx];
            int actual = (int) iterator.next();
            assertEquals("Your method does not iterate from first to last in the correct order",
                    expected, actual);
            idx += 1;
        }
        if (iterator.hasNext()) {
            fail("Your method hasNext return true when all element have been iterated over");
        } else if (idx < numberElements) {
            fail("Your iterator does not iterate over all elements (" + idx + " over " + numberElements +")");
        }
    }

    @Test(expected=ConcurrentModificationException.class)
    @Grade
    public void testConcurrentModification() {
        CustomList<Integer> list = new CustomList<>();
        int numberElements = random.nextInt(90) + 10;
        for (int i = 0; i < numberElements; i++) {
            list.add(random.nextInt());
        }

        int popIdx = 0;
        for (int i : list) {
            if (popIdx == 5) {
                list.pop();
            }
            popIdx += 1;
        }
    }
}
