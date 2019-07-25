package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import templates.*;


@RunWith(GradingRunner.class)
public class InginiousTests {

    private Supplier<Integer> rng = () -> (int) (Math.random() * 25) + 5; // at least 5 elements

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with empty or null array should fail")})
    public void test1() {
        try {
            QuickSort.swap(null, 0, 0);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with empty or null array should fail")})
    public void test2() {
        try {
            QuickSort.swap(new Object[]{}, 0, 0);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with index out of range should fail")})
    public void test3() {
        try {
            QuickSort.swap(new Object[]{1, 2}, -1, 0);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with index out of range should fail")})
    public void test4() {
        try {
            QuickSort.swap(new Object[]{1, 2}, 0, 42);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with index in range should success")})
    public void test5() {
        try {
            Object[] test = new Object[]{1, 2};
            QuickSort.swap(test, 0, 1);
            assertEquals(test[0], 2);
            assertEquals(test[1], 1);
        } catch (AssertionError error) {
            fail("Should not crash");
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "precondition(s) check on partition method: FAIL")})
    public void test6() {
        try {
            Integer[] test = new Integer[]{1, 2};
            QuickSort.partition(test, 1, 0);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "post-condition(s) check on partition method: FAIL")})
    public void test7() {

        int size;
        Integer[] test;
        int left;
        int right;
        int pivot;
        boolean firstcheck;
        boolean secondcheck;

        for (int i = 0; i < 1000; i++) {

            try {
                size = rng.get();
                test = new Random()
                        .ints(size, -25, 25)
                        .boxed()
                        .toArray(Integer[]::new);
                left = new Random().nextInt(size);
                // Random throws a IllegalArgument exception if we don't check bounds
                right = (left == size-1) ? left : new Random().ints(1, left, size-1).sum();
                pivot = QuickSort.partition(test, left, right);
                int pivot_value = test[pivot];

                // because we can get duplicated values, we must use <= or >= everywhere

                firstcheck = Arrays
                        .stream(test)
                        .skip(left)
                        .limit(pivot-left+1)
                        .allMatch(value -> value <= pivot_value);

                secondcheck = Arrays
                        .stream(test)
                        .skip(pivot+1)
                        .limit(right-pivot)
                        .allMatch(value -> value >= pivot_value);

                assertTrue(firstcheck);
                assertTrue(secondcheck);

            } catch (AssertionError error) {
                fail("Precondition is hold");
            } catch (Exception err) {
                fail("Unexpected error");
            }

        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "post-condition(s) check on sort(Comparable[] a) method: FAIL")})
    public void test8() {

        for (int i = 0; i < 100; i++) {

            try {
                int size = rng.get();
                Integer[] test = new Random()
                        .ints(size, -25, 25)
                        .boxed()
                        .toArray(Integer[]::new);

                Integer[] expected = Arrays.stream(test).sorted().toArray(Integer[]::new);
                QuickSort.sort(test);

                assertTrue(Arrays.deepEquals(expected, test));

            } catch (AssertionError error) {
                fail("Precondition is hold");
            } catch (Exception err) {
                fail("Unexpected error");
            }

        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling sort with index out of range should fail")})
    public void test9() {
        try {
            QuickSort.sort(new Integer[]{1, 2}, 0, 42);
        } catch (AssertionError error) {
            assertTrue(true);
        } catch (Exception err) {
            fail("Unexpected error");
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "post-condition(s) check on partition(Comparable[] a, int left, int right) method: FAIL")})
    public void test10() {

        for (int i = 0; i < 100; i++) {

            try {
                int size = rng.get();
                Integer[] test = new Random()
                        .ints(size, -25, 25)
                        .boxed()
                        .toArray(Integer[]::new);
                int right = new Random().nextInt(size);
                int limit = right + 1;

                Stream<Integer> integerStream = Arrays.stream(test).limit(limit).sorted();
                integerStream = Stream.concat(integerStream, Arrays.stream(test).skip(limit));
                Integer[] expected = integerStream.toArray(Integer[]::new);
                QuickSort.sort(test, 0, right);

                assertTrue(Arrays.deepEquals(expected, test));

            } catch (AssertionError error) {
                fail("Precondition is hold");
            } catch (Exception err) {
                fail("Unexpected error");
            }

        }
    }

}