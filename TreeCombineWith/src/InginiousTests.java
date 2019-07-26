package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import templates.*;

// TODO Finish tests
@RunWith(GradingRunner.class)
public class InginiousTests {

    private Supplier<Integer> rng = () -> (int) (Math.random() * 25) + 5; // at least 5 elements

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "")})
    public void test1() {

    }

}