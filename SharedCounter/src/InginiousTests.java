package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
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
    @GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code.")
    public void BasicConcurrencyTest(){

        int nThreads = 100;
        int nOps = 100;
        final AssertionError[] ae = new AssertionError[1];

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
                        } catch (AssertionError e) { ae[0] = e; }
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

        if(ae[0] != null) throw ae[0];
    }


    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @Test
    @GradeFeedback(onTimeout = true, message = "There is probably a deadlock in your code.")
    @GradeFeedback(onFail = true, message = "Threads are not waiting when they should be waiting...")
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
    @GradeFeedback(onFail = true, onTimeout = true, message = "Either your counter went below 0 at some point or your counter" +
            "is not exactly equal to 0 after performing the same number of inc() and dec()...")
    public void waitingThreadsTest(){
    
        int nThreads = 50;
        int nOps = 1000;
        final AssertionError[] ae = new AssertionError[1];

        for(int times = 0; times < 100; times++){

            SharedCounter counter = new SharedCounter();
            CountDownLatch latch = new CountDownLatch(1);

            Thread [] incs = new Thread[nThreads];
            Thread [] decs = new Thread[nThreads];

            for(int i = 0; i < nThreads; i++){

                incs[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < nOps; i++){
                            try{
                                assertTrue(counter.get() >= 0);
                                counter.inc();
                            } catch (AssertionError e) { ae[0] = e;}
                        }
                    }
                });

                decs[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < nOps; i++){
                            try {
                                counter.dec();
                                assertTrue(counter.get() >= 0);
                            }catch (AssertionError e) {ae[0] = e;}
                        }
                    }
                });

                incs[i].start();
                decs[i].start();

            }

            latch.countDown();

            for(int i = 0; i < nThreads; i++){
                try{
                    incs[i].join();
                    decs[i].join();
                } catch (InterruptedException e){}
            }

            if(ae[0] != null) throw ae[0];

            assertEquals(0, counter.get());


        }

    }



}
