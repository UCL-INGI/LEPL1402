package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    Supplier<Integer> rnd = () -> (int) (Math.random() * 100);


    @Grade
    @Test
    public void testLIFO(){

        MyStack<Integer> stack = new MyStack<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);
        Arrays.stream(seeds).forEach(stack::push);

        for(int i=99; i > 0; i--){
            assertEquals(seeds[i], stack.pop());
        }

    }


    @Grade
    @Test
    public void testPushAndPeek(){

        MyStack<Integer> stack = new MyStack<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);

        for(Integer seed : seeds){
            stack.push(seed);
            assertEquals(seed, stack.peek());
        }
    }


    @Grade
    @Test
    public void testEmpty(){

        MyStack<Integer> stack = new MyStack<>();

        assertTrue(stack.empty());
        stack.push(1);
        assertFalse(stack.empty());
        stack.peek();
        assertFalse(stack.empty());
        stack.pop();
        assertTrue(stack.empty());

    }

}