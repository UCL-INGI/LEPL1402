package src;

import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.Random;

import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    Random r = new Random();

    @Test
    @Grade(value = 1, cpuTimeout=1000, customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="Should work with one thread", onFail=true)
    public void testSingleThread() {
        float[] values = new float[] {1.5f,3.2f,5.0f,6.6f,7.2f,1.5f,3.7f,5.8f,6.0f,9.0f,1.3f,2.3f,4.5f,1.5f};
        Assert.assertEquals(3, ParallelCounting.parallelCount(Optional.of(values), 1.5f, 1));
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000, customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="Should work with all numbers of threads from 1 to values.length", onFail=true)
    public void testMultipleThreads() {
        float[] values = new float[] {1.5f,3.2f,5.0f,6.6f,7.2f,1.5f,3.7f,5.8f,6.0f,9.0f,1.3f,2.3f,4.5f,1.5f};
        for(int i=1;i<=values.length;i++) {
            Assert.assertEquals(3, ParallelCounting.parallelCount(Optional.of(values), 1.5f, i));
        }
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000, customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="Should also work with empty array", onFail=true)
    public void testEmptyValues() {
        Assert.assertEquals(0,ParallelCounting.parallelCount(Optional.of(new float[]{}),1.5f,2));
    }

    @Test
    @Grade(value = 1, cpuTimeout=1000, customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="Should return 0 if no values are provided", onFail=true)
    public void testNoValues() {
        Assert.assertEquals(0,ParallelCounting.parallelCount(Optional.empty(),1.5f,2));
    }

    @Test
    @Grade(value = 1, cpuTimeout=2000, customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="Should work with larger input (1 million array elements)", onFail=true)
    public void testWithLargeInput() {
        // make a large array
        float[] values=new float[999_999];
        for(int i=0;i<values.length;i+=3) {
            values[i]=1.0f;
        }
        Optional<float[]> opt=Optional.of(values);

        Assert.assertEquals(values.length/3, ParallelCounting.parallelCount(opt, 1.0f, 3));
    }

    private int count(float [] values, float target) {
        int count = 0;
        for (float v : values) {
            if (v == target)
                count ++;
        }
        return count;
    }

    @Test
    @Grade(value=1,cpuTimeout=2000,customPermissions=ThreadPermissionFactory.class)
    @GradeFeedback(message="You should use nThreads to do the computation", onFail=true)
    public void testNumberThreads() {
        int size = 100_000;
        float [] values = new float[size];
        for (int i = 0; i < size; i++)
            values[i] = r.nextFloat();

        int [] maxNumberRunningThread = new int[]{-1};

        Thread t = new Thread(new Runnable(){
            public void run() {
                while (true) {
                    int nbRunningThread = Thread.getAllStackTraces().keySet().size();
                    if (maxNumberRunningThread[0] < nbRunningThread)
                        maxNumberRunningThread[0] = nbRunningThread;
                }
            }
        });
        t.setDaemon(true);
        t.start();

        // Initial count thread. The JVM will launch multiple thread to do various
        // things, need to remove them from the last thread count
        int initialThreadCount = Thread.getAllStackTraces().keySet().size();
        int nThreads = 3;
        int count = ParallelCounting.parallelCount(Optional.of(values), values[0], nThreads);

        Assert.assertEquals(nThreads, maxNumberRunningThread[0] - initialThreadCount);
    }

}
