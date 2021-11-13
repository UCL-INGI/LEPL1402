package module6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ThreadsIntroductionTest {
    //BEGIN STRIP
    @Test
    public void testThreads(){

        Thread[] t = new Thread[4];

        for(int i=0; i<1000; i++) {

            ThreadsIntroduction.Counter[] r = ThreadsIntroduction.Launcher.init(t);

            assertEquals(r[0].getCount(), r[0].getRnd());
            assertEquals(r[1].getCount(), r[1].getRnd());
            assertEquals(r[2].getCount(), r[2].getRnd());
            assertEquals(r[3].getCount(), r[3].getRnd());
        }
    }


    @Test
    public void testName(){

        Thread[] t = new Thread[4];

        ThreadsIntroduction.Counter [] r = ThreadsIntroduction.Launcher.init(t);

        for(int i=0; i<t.length; i++){
            assertEquals(t[i].getName(), String.valueOf(i));
        }

    }
    //END STRIP
}
