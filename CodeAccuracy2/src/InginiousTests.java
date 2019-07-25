package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import org.junit.*;
import org.junit.runner.RunWith;
import java.util.function.Supplier;

import templates.*;


@RunWith(GradingRunner.class)
public class InginiousTests {

    Supplier<Integer> rng = () -> (int) (Math.random() * 100);

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Calling swap with empty or null array should fail")})
    public void test1(){
        try {
            QuickSort.swap(null, 0, 0);
        } catch (AssertionError error) {

        } catch (Exception err) {
            fail()
        }
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "")})
    public void test2(){

    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "")})
    public void test3(){

    }


}