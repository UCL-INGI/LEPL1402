import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void hasNextOnEmpty() {
        CustomList<Integer> list = new CustomList<>();
        Iterator<Integer> iterator = list.iterator();
        assertFalse("Your method hasNext return true on an new empty list", iterator.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    public void nextOnEmpty() {
        CustomList<Integer> list = new CustomList<>();
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
    }

    @Test
    public void testHasNext() {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Iterator<Integer> iterator = list.iterator();
        for (int i = 0; i < 10; i++) {
            assertTrue("Your method hasNext return false to early", iterator.hasNext());
            iterator.next();
        }
    }

    @Test
    public void testIterationOrder() {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Iterator<Integer> iterator = list.iterator();
        int expected = 0;
        while (iterator.hasNext() && expected < 10) {
            int actual = (int) iterator.next();
            assertEquals("Your method does not iterate from first to last in the correct order",
                    expected, actual);
            expected += 1;
        }
        if (iterator.hasNext()) {
            fail("Your method hasNext return true when all element have been iterated over");
        } else if (expected < 10) {
            fail("Your iterator does not iterate over all elements (" + expected + " over 10)");
        }
    }

    @Test(expected=ConcurrentModificationException.class)
    public void testConcurrentModification() {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (int i : list) {
            if (i == 5) {
                list.pop();
            }
        }
    }

}
