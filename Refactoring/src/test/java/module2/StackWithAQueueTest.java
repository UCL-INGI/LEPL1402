package module2;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class StackWithAQueueTest {
    //BEGIN STRIP
    Supplier<Integer> rnd = () -> (int) (Math.random() * 100);

    
    @Test
    public void testLIFO(){

        StackWithAQueue<Integer> stack = new StackWithAQueue<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);
        Arrays.stream(seeds).forEach(stack::push);

        for(int i=99; i > 0; i--){
            assertEquals(seeds[i], stack.pop());
        }

    }

    
    @Test
    public void testPushAndPeek(){

        StackWithAQueue<Integer> stack = new StackWithAQueue<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);

        for(Integer seed : seeds){
            stack.push(seed);
            assertEquals(seed, stack.peek());
        }
    }

    
    @Test
    public void testEmpty(){

        StackWithAQueue<Integer> stack = new StackWithAQueue<>();

        assertTrue(stack.empty());
        stack.push(1);
        assertFalse(stack.empty());
        stack.peek();
        assertFalse(stack.empty());
        stack.pop();
        assertTrue(stack.empty());

    }
    //END STRIP
}
