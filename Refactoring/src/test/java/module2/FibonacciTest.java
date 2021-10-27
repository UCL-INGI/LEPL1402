package module2;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FibonacciTest {

    // number of function call for a number F(n) is 2 * F(n) - 1

    private int seqNumIt, seqNumRec = 0;

    private Supplier<Integer> smallRNG = () -> (int) (Math.random()+1)*20;

    private Supplier<Integer> largeRNG = () -> (int) (Math.random()+1)*100000;

    private Supplier<Integer> fiboSeqIt = () -> Fibonacci.fiboIterative(seqNumIt++);

    private Supplier<Integer> fiboSeqRec = () -> Fibonacci.fiboIterative(seqNumRec++);


    //BEGIN STRIP
    @Test
    public void testRecursif(){

        Integer [] seeds = Stream.generate(smallRNG).limit(5).toArray(Integer[]::new);

        for(Integer seed : seeds){
            Fibonacci.resetCount();
            int res = Fibonacci.fiboRecursive(seed);
            assertTrue((2 * res) - 1 <= Fibonacci.getCount());
        }

    }


    @Test
    public void testIterative(){

        // Will t.o easily if implem is recursive (or bad)

        Integer [] seeds = Stream.generate(largeRNG).limit(10).toArray(Integer[]::new);

        for(Integer seed : seeds){
            Fibonacci.resetCount();
            Fibonacci.fiboIterative(seed);
            assertEquals(1, Fibonacci.getCount());
        }

    }
    //END STRIP


    @Test
    public void testCorrectness(){
        Integer [] seqIt = Stream.generate(fiboSeqIt).limit(30).toArray(Integer[]::new);
        Integer [] seqRec = Stream.generate(fiboSeqRec).limit(30).toArray(Integer[]::new);

        assertEquals(0, (int) seqIt[0]);
        assertEquals(1, (int) seqIt[1]);
        assertEquals(1, (int) seqIt[2]);
        assertEquals(2, (int) seqIt[3]);
        assertEquals(3, (int) seqIt[4]);
        assertEquals(5, (int) seqIt[5]);
        assertEquals(8, (int) seqIt[6]);

        for(int i = 0; i < seqIt.length; i++){
            assertEquals(seqIt[i], seqRec[i]);
        }

        for(int i = 2; i < seqIt.length; i++){
            assertEquals((int) seqIt[i-1] + seqIt[i-2], (int) seqIt[i]);
            assertEquals((int) seqRec[i-1] + seqRec[i-2], (int) seqRec[i]);
        }

    }


}
