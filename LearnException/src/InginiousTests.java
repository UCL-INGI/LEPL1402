package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    @Test
    @Grade(value=10 , custom = true, cpuTimeout=1000)
    public void testGlobal() throws CustomGradingResult{

        int i1 = 25, i2 = 0;
        boolean correct=false;
        double res= 0.0;
        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        if(!correct){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "Divide() by 0 should throw an exception");
        }


        correct = false;
        i1 = 25;
        i2 = 5;

        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        if(correct){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "Divide has thrown an exception when it shouldn't");
        }else if(res!=5.0){
            throw new CustomGradingResult(TestStatus.FAILED, 1 , "Divide has not given the expected result");
        }

        correct = false;
        i1 = 5;
        i2 = 2;

        try{
            res = Divider.divide(i1, i2);
        }catch(ArithmeticException e){
            correct = true;
        }

        if(correct){
            throw new CustomGradingResult(TestStatus.FAILED, 2 , "Divide has thrown an exception when it shouldn't");
        }else if(res!= 2.5){
            throw new CustomGradingResult(TestStatus.FAILED, 3 , "Divide has not given the expected result, did you cast integer BEFORE dividing them");
        }

        i1 = 5;
        i2 = 0;
        correct = Divider.canDivide(i1, i2);

        if(correct){
            throw new CustomGradingResult(TestStatus.FAILED, 4 , "canDivide has returned true when it should have returned false");
        }

        i1 = 5;
        i2 = 2;
        correct = Divider.canDivide(i1, i2);

        if(!correct){
            throw new CustomGradingResult(TestStatus.FAILED, 5 , "canDivide has returned false when it should have returned true");
        }

        correct = true;
        i1 = 5;
        i2 = 2;
        try{
            res = Divider.betterDivide(i1, i2);
        }catch(ArithmeticException e){
            correct = false;
        }

        if(res!=2.5){
            throw new CustomGradingResult(TestStatus.FAILED, 6 , "BetterDivide has not given the expected result, did you cast integer BEFORE dividing them");
        }

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

        if(!correct){
            throw new CustomGradingResult(TestStatus.FAILED, 7 , "betterDivide has not set a message in the exception");
        }


        i1 = 5;
        i2 = 2;
        String s = Divider.betterCanDivide(i1, i2);
        if(almostEquals(2.5, Double.valueOf(s))){
            throw new CustomGradingResult(TestStatus.FAILED, 8 , "BetterCanDivide has not given the expected result");
        }

        s=null;
        i1 = 5;
        i2 = 0;
        s = Divider.betterCanDivide(i1, i2);
        if(message.compareTo(s)!=0){
            throw new CustomGradingResult(TestStatus.FAILED, 9 , "BetterCanDivide didn't return the message given by betterDivide exception");
        }

        throw new CustomGradingResult(TestStatus.SUCCESS, 10 , "Good job!");




    }

    public static boolean almostEquals(double d1, double d2){
        return Math.abs(d1-d2) > 0.000001;
    } 

}