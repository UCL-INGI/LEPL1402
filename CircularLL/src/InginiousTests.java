package src;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import java.util.LinkedList;

import static org.junit.Assert.*;

import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests{

    @Test
    @Grade
    @GradeFeedback(onFail=true, message="The size of your list is incorrect")
    public void testSize(){
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        assertEquals(0, list.size());

        for(int i = 0; i < 10; i ++){
            list.enqueue(i);
            assertEquals(i+1, list.size());
        }

        for(int i = 9; i >= 0; i--){
            list.remove(i);
            assertEquals(i, list.size());
        }
    }

    @Test
    @Grade
    @GradeFeedback(onFail=true, message="The element are not well inserted in your queue")
    public void testEnqueue() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }

        ListNode<Integer> ptr = list.getFirst();
        for (int i = 0; i < 10; i ++) {
            assertEquals(i, (int) ptr.getItem());
            ptr = ptr.getNext();
        }
    }

    @Test
    @Grade
    @GradeFeedback(onFail=true, message="The first and last element are not well updated when the queue is emptied")
    public void firstAndLastUpdateEmpty() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }
        for (int i = 0; i < 10; i ++) {
            list.remove(0);
        }
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }

    @Test
    @Grade
    @GradeFeedback(onFail=true, message="The last element is not well updated when removed!")
    public void testRemoveLast() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }

        ListNode<Integer> ptr = list.getFirst();
        for (int i = 0; i < 9; i++) {
            ptr = ptr.getNext();
        }

        list.remove(9);
        assertEquals(ptr, list.getLast());
    }



    @Test
    @Grade
    @GradeFeedback(onFail=true, message="Removing the element in insertion order does not work")
    public void testRemoveInOrder() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }

        ListNode<Integer> ptr = list.getFirst();
        for (int i = 0; i < 10; i ++) {
            ListNode<Integer> nextFirst = ptr.getNext();
            assertEquals(i, (int) list.remove(0));
            if (i != 9) {
                assertEquals("The first element is not correctly updated", nextFirst, list.getFirst());
            } else {
                assertNull("The first element is not correctly updated", list.getFirst());
            }
            ptr = ptr.getNext();
        }
    }

    @Test
    @Grade
    @GradeFeedback(onFail=true, message="When removing an element in the middle, the pointers are not well updated")
    public void testRemoveMiddle() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }
        ListNode<Integer> first = list.getFirst();
        ListNode<Integer> last = list.getLast();

        list.remove(4);

        assertEquals(first, list.getFirst());
        assertEquals(last, list.getLast());

        ListNode<Integer> ptr = list.getFirst();
        for (int i = 0; i < 4; i++) {
            assertEquals(i, (int) ptr.getItem());
            ptr = ptr.getNext();
        }

        for (int i = 5; i < 10; i++) {
            assertEquals(i, (int) ptr.getItem());
            ptr = ptr.getNext();
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Grade
    @GradeFeedback(onFail=true, message="Your remove method does not throw a IndexOutOfBoundsException when removing from empty list")
    public void testRemoveEmpty() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.remove(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Grade
    @GradeFeedback(onFail=true, message="Your remove method does not throw a IndexOutOfBoundsException when removing from we just emptied")
    public void testRemoveOutOfBound() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }
        list.remove(15);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @Grade
    @GradeFeedback(onFail=true, message="Your remove method does not throw a IndexOutOfBoundsException the index is negative")
    public void testRemoveOutOfBoundNegative() {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for (int i = 0; i < 10; i ++) {
            list.enqueue(i);
        }
        list.remove(-12);
    }
}
