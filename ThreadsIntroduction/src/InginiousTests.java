package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    @Test
    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @GradeFeedback(onFail = true, onTimeout = true, message = "Your threads did not ran properly")
    public void testThreads(){

        Thread[] t = new Thread[4];

        for(int i=0; i<1000; i++) {

            Counter[] r = Launcher.init(t);

            assertEquals(r[0].getCount(), r[0].getRnd());
            assertEquals(r[1].getCount(), r[1].getRnd());
            assertEquals(r[2].getCount(), r[2].getRnd());
            assertEquals(r[3].getCount(), r[3].getRnd());
        }
    }


    @Test
    @Grade(cpuTimeout = 3000, customPermissions = ThreadPermissionFactory.class)
    @GradeFeedback(onFail = true, onTimeout = true, message = "You did not give proper names to your threads")
    public void testName(){
        
        Thread[] t = new Thread[4];

        Counter [] r = Launcher.init(t);

        for(int i=0; i<t.length; i++){
            assertEquals(t[i].getName(), String.valueOf(i));
        }

    }

}