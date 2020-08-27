package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import templates.*;

import java.util.Random;
import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private static Random rng = new Random();

    private static String randomString(int size){
        Random rng = new Random();
        String s = "";
        for(int i =0; i < size ; i++){
            char c = (char)(rng.nextInt(57)+65);
            s = s.concat(String.valueOf(c));
        }
        return s;
    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=100)
    @GradeFeedback(message="Your lambda for question 1 is incorrect", onFail=true)
    public void testFirstFunction() throws CustomGradingResult{

        int size = 100;

        Random rng = new Random();
        Integer in, ran;

        for(int i = 0 ; i < size ; i++){
            ran = rng.nextInt(1000);
            in = StudentCode.f1.apply(ran);
            assertEquals(in, Integer.valueOf(ran+2));
        }


    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=100)
    @GradeFeedback(message="Your lambda for question 2 is incorrect", onFail=true)
    public void testSecondFunction() throws CustomGradingResult{

        int size = 100;
        String s1;
        for(int i = 0 ; i < size ; i++){
            int l = rng.nextInt(25);
            s1 = randomString(l);
            assertEquals(StudentCode.mf.apply(s1).intValue(), l);
        }


    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=200)
    @GradeFeedback(message="Your lambda for question 3 is incorrect", onFail=true)
    public void testPredicate() throws CustomGradingResult{

        int size = 100;

        Random rng = new Random();
        Integer ran;

        for(int i = 0 ; i < size ; i++){
            ran = rng.nextInt(1000);
            if(ran%2==0) assertTrue(StudentCode.p1.test(ran));
            else assertFalse(StudentCode.p1.test(ran));
        }


    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=100)
    @GradeFeedback(message="Your lambda for question 4 is incorrect", onFail=true)
    public void testStringCompTo() throws CustomGradingResult{

        int size = 100;

        String s1, s2;
        for(int i = 0 ; i < size ; i++){
            s1 = randomString(25);
            s2 = randomString(25);
            assertEquals(s1.compareTo(s2), StudentCode.comp.compare(s1,s2));
        }

    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=100)
    @GradeFeedback(message="Your lambda for question 5 is incorrect", onFail=true)
    public void testStringLengthComp() throws CustomGradingResult{

        int size = 100;
        String s1, s2;
        for(int i = 0 ; i < size ; i++){
            int l1 = rng.nextInt(25);
            int l2 = rng.nextInt(25);
            s1 = randomString(l1);
            s2 = randomString(l2);
            assertEquals(s1.length()-s2.length(), StudentCode.compLength.compare(s1,s2));
        }

    }

}
