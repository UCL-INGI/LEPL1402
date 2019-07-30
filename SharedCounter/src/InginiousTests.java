package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    private AssertionError ae;

    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @Test
    @GradeFeedbacks({@GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code."),
            @GradeFeedback(onFail = true, message = ""),
            @GradeFeedback(onSuccess = true, message = "")})
    public void BasicConcurrencyTest(){

        int nThreads = 100;
        int nOps = 100;
        Thread[] threads = new Thread[nThreads];
        SharedCounter counter = new SharedCounter();
        CountDownLatch latch = new CountDownLatch(1);

        for (int i=0;i<nThreads;i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try { latch.await(); }
                    catch (InterruptedException e) {}
                    for (int j=0;j<nOps;j++) {
                        try{
                            if (ThreadLocalRandom.current().nextDouble()<0.25) counter.dec();
                            else counter.inc();
                            assertTrue(counter.get() >= 0);
                        } catch (AssertionError e) { ae = e; }
                    }
                }
            });
            threads[i].start();
        }

        latch.countDown();

        for (int i=0;i<nThreads;i++) {
            try { threads[i].join(); }
            catch (InterruptedException e) {}
        }
        assertTrue(counter.get() >= 0);

        if(ae != null) throw ae;
    }


    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @Test
    @GradeFeedbacks({@GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code."),
            @GradeFeedback(onFail = true, message = "Threads are not blocked when they should be blocked..."),
            @GradeFeedback(onSuccess = true, message = "")})
    public void testBlockingThread(){

        SharedCounter counter = new SharedCounter();
        Thread dec = new Thread(new Runnable() {
            @Override
            public void run() {
                counter.dec();
            }
        });
        dec.start();

        try{ Thread.sleep(1500); }
        catch (InterruptedException e) {}

        assertTrue(dec.isAlive());

        Thread inc = new Thread(new Runnable() {
            @Override
            public void run() {
                counter.inc();
            }
        });

        inc.start();

        try{
            inc.join();
            dec.join();
        } catch(InterruptedException e){}

        assertFalse(dec.isAlive());
        assertEquals(0, counter.get());
    }

    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @Test
    @GradeFeedbacks({@GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code."),
            @GradeFeedback(onFail = true, message = "Either your counter went below 0 at some point or your counter" +
                    "is not exactly equal to 0 after performing the same number of inc() and dec()..."),
            @GradeFeedback(onSuccess = true, message = "")})
    public void waitingThreadsTest(){

        for(int times = 0; times < 500; times++ ) {

            int nDecs = 2;

            SharedCounter counter = new SharedCounter();
            CountDownLatch latch = new CountDownLatch(1);

            for (int i = 0; i < 1000; i++) {
                counter.inc();
            }

            Thread[] decs = new Thread[nDecs];

            for (int i = 0; i < nDecs; i++) {

                decs[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            latch.await();
                        } catch (InterruptedException e) {}
                        for (int i = 0; i < 1000; i++) {
                            try {
                                counter.dec();
                                assertTrue(counter.get() >= 0);
                            } catch (AssertionError e) { ae = e; }
                        }
                    }
                });

                decs[i].start();
            }

            Thread inc = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                    }
                    for (int i = 0; i < 1000; i++) {
                        counter.inc();
                    }
                }
            });

            inc.start();

            latch.countDown();

            for (int i = 0; i < nDecs; i++) {
                try {
                    decs[i].join();
                } catch (InterruptedException e) {
                }
            }

            try {
                inc.join();
            } catch (InterruptedException e) {}

            if (ae != null) throw ae;
            assertEquals(0, counter.get());
        }

    }



}
