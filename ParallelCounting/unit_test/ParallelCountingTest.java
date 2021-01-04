import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class ParallelCountingTest {
    @Test
    public void test1() {
        float[] values = new float[] {4.5f,3.2f,5.0f,6.6f,7.2f,1.5f,3.7f,5.8f,6.0f,9.0f,1.3f,2.3f,4.5f,1.5f};
        Assert.assertEquals(2,ParallelCounting.parallelCount(Optional.of(values),4.5f,2));
    }

    @Test
    public void test2() {
        Assert.assertEquals(0,ParallelCounting.parallelCount(Optional.empty(),4.5f,2));
    }

    /**
     * The next test checks whether your implementation with multiple threads is actually
     * faster than with one single thread.
     * Warning 1: This test is slow. Don't run it every time.
     * Warning 2: This test might not be very reliable on your computer, depending what
     * your computer is doing in the background, etc.
     */
    @Test
    public void test3() {
        // make a large array
        float[] values=new float[99_999_999*2];
        for(int i=0;i<values.length;i+=3) {
            values[i]=1.0f;
        }
        Optional<float[]> opt=Optional.of(values);

        // just a test to see whether the result is okay
        Assert.assertEquals(values.length/3, ParallelCounting.parallelCount(opt, 1.0f, 1));

        // Speed test.
        // Using two threads should be at least 30% faster than using one thread:

        long t=System.currentTimeMillis();
        for(int i=0;i<10;i++) {
            ParallelCounting.parallelCount(opt, 1.0f, 1);
        }
        long t1=System.currentTimeMillis()-t;
        for(int i=0;i<10;i++) {
            ParallelCounting.parallelCount(opt, 1.0f, 2);
        }
        long t2=System.currentTimeMillis()-t-t1;
        System.out.println(t1+"   "+t2);
        Assert.assertTrue(
                "Using two threads should be at least 30% faster than using one thread (see comments of this test)",
                t2<t1*0.7);
    }
}