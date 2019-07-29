package SharedCounter;

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

    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @Test
    public void BasicConcurrencyTest(){

        int nThreads = 100;
        int nOps = 10000;
        Thread[] threads = new Thread[nThreads];
        SharedCounter counter = new SharedCounter();
        CountDownLatch latch = new CountDownLatch(1);

        for (int i=0;i<nThreads;i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try { latch.await(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                    for (int j=0;j<nOps;j++) {
                        if (ThreadLocalRandom.current().nextDouble()<0.25) counter.dec();
                        else counter.inc();
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

    @Grade(cpuTimeout = 5000, customPermissions = ThreadPermissionFactory.class)
    @Test
    @GradeFeedbacks({@GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code."),
    @GradeFeedback(onFail = true, message = "Either your counter went below 0 or it was not equal to 0 when performing " +
            "the same number of inc() and dec()"), @GradeFeedback(onSuccess = true, message = "")})
    public void testPositivity(){

        for(int times = 0; times < 500; times++) {

            // rerun this whole test 500 times, as faulty code may not always fail
            // (non-determinism induced by threads, all that stuff...)
            // 500 times may be overkilled, but i don't want to leave things to fate.

            SharedCounter counter = new SharedCounter();
            int nThreads = 10;
            int nOps = 1000;

            Thread[] incs = new Thread[nThreads];
            Thread[] decs = new Thread[nThreads];

            for (int i = 0; i < nThreads; i++) {

                incs[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < nOps; i++) {
                            assertTrue(counter.get() >= 0);
                            counter.inc();
                        }
                    }
                });

                decs[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < nOps; i++) {
                            counter.dec();
                            assertTrue(counter.get() >= 0);
                        }
                    }
                });

                incs[i].start();
                decs[i].start();
            }

            for (int i = 0; i < nThreads; i++) {
                try {
                    incs[i].join();
                    decs[i].join();
                } catch (InterruptedException e) {}
            }

            assertEquals(0, counter.get());
        }

    }



}
