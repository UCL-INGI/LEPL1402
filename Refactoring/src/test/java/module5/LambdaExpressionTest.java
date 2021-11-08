package module5;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LambdaExpressionTest {
    //BEGIN STRIP

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
    public void testFirstFunction(){

        int size = 100;

        Random rng = new Random();
        Integer in, ran;

        for(int i = 0 ; i < size ; i++){
            ran = rng.nextInt(1000);
            in = LambdaExpressionInJava.f1.apply(ran);
            assertEquals(in, Integer.valueOf(ran+2));
        }
    }

    @Test()
    public void testSecondFunction() {

        int size = 100;
        String s1;
        for(int i = 0 ; i < size ; i++){
            int l = rng.nextInt(25);
            s1 = randomString(l);
            assertEquals(LambdaExpressionInJava.mf.apply(s1).intValue(), l);
        }
    }

    @Test()
    public void testPredicate() {

        int size = 100;

        Random rng = new Random();
        Integer ran;

        for(int i = 0 ; i < size ; i++){
            ran = rng.nextInt(1000);
            if(ran%2==0) assertTrue(LambdaExpressionInJava.p1.test(ran));
            else assertFalse(LambdaExpressionInJava.p1.test(ran));
        }
    }

    @Test()
    public void testStringCompTo() {

        int size = 100;

        String s1, s2;
        for(int i = 0 ; i < size ; i++){
            s1 = randomString(25);
            s2 = randomString(25);
            assertEquals(s1.compareTo(s2), LambdaExpressionInJava.comp.compare(s1,s2));
        }

    }

    @Test()
    public void testStringLengthComp() {

        int size = 100;
        String s1, s2;
        for(int i = 0 ; i < size ; i++){
            int l1 = rng.nextInt(25);
            int l2 = rng.nextInt(25);
            s1 = randomString(l1);
            s2 = randomString(l2);
            assertEquals(s1.length()-s2.length(), LambdaExpressionInJava.compLength.compare(s1,s2));
        }

    }
    //END STRIP
}
