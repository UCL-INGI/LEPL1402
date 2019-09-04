import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.GradeFeedback;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class)
public class Tests{

    Supplier<Integer> rnd = () -> (int) (Math.random() * 100);


    @Test
    @Grade
    @GradeFeedback(message = "Order in the queue is not respected", onFail = true, onTimeout = true)
    public void testFIFO(){

        MyQueue<Integer> queue = new MyQueue<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);
        Arrays.stream(seeds).forEach(queue::enqueue);

        for(int i=0; i < 100; i++){
            assertEquals(seeds[i], queue.dequeue());
        }

    }


    @Test
    @Grade
    @GradeFeedback(message = "Order in the queue is not respected", onFail = true, onTimeout = true)
    public void testEnqueueAndPeek(){

        MyQueue<Integer> queue = new MyQueue<>();
        Integer [] seeds = Stream.generate(rnd).limit(100).toArray(Integer[]::new);

        for(Integer seed : seeds){
            queue.enqueue(seed);
            assertEquals(seeds[0], queue.peek());
        }
    }


    @Test
    @Grade
    @GradeFeedback(message = "Order in the queue is not respected", onFail = true, onTimeout = true)
    public void testEmpty(){

        MyQueue<Integer> queue = new MyQueue<>();

        assertTrue(queue.empty());
        queue.enqueue(1);
        assertFalse(queue.empty());
        queue.peek();
        assertFalse(queue.empty());
        queue.dequeue();
        assertTrue(queue.empty());

    }

}