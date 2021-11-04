package module1;

import org.junit.Test;

import static org.junit.Assert.*;

public class LearnExceptionTest {

    @Test
    public void testGlobal() {

        int i1 = 25, i2 = 0;
        boolean correct=false;
        double res= 0.0;
        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        assertTrue("Divide() by 0 should throw an exception", correct);

        correct = false;
        i1 = 25;
        i2 = 5;

        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        assertFalse("Divide has thrown an exception when it shouldn't", correct);
        assertEquals("Divide has not given the expected result", res, 5.0);

        correct = false;
        i1 = 5;
        i2 = 2;

        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        assertFalse("Divide has thrown an exception when it shouldn't", correct);
        assertEquals("Divide has not given the expected result, did you cast integer BEFORE dividing them", res, 2.5);

        i1 = 5;
        i2 = 0;
        correct = Divider.canDivide(i1, i2);

        assertFalse("canDivide has returned true when it should have returned false", correct);

        i1 = 5;
        i2 = 2;
        correct = Divider.canDivide(i1, i2);

        assertTrue("canDivide has returned false when it should have returned true", correct);

        correct = true;
        i1 = 5;
        i2 = 2;
        try{
            res = Divider.betterDivide(i1, i2);
        }catch(ArithmeticException e){
            correct = false;
        }

        assertEquals("BetterDivide has not given the expected result, did you cast integer BEFORE dividing them", res, 2.5);

        correct = false;
        i1 = 5;
        i2 = 0;
        String message = null;
        try{
            res = Divider.betterDivide(i1, i2);
        }catch(ArithmeticException e){
            message = e.getMessage();
            correct = e.getMessage()!=null;
        }

        assertTrue("betterDivide has not set a message in the exception", correct);

        i1 = 5;
        i2 = 2;
        String s = Divider.betterCanDivide(i1, i2);

        assertFalse("BetterCanDivide has not given the expected result", almostEquals(2.5, Double.valueOf(s)));

        s=null;
        i1 = 5;
        i2 = 0;
        s = Divider.betterCanDivide(i1, i2);

        assertEquals("BetterCanDivide didn't return the message given by betterDivide exception", message.compareTo(s), 0);

    }

    public static boolean almostEquals(double d1, double d2){
        return Math.abs(d1-d2) > 0.000001;
    } 

}