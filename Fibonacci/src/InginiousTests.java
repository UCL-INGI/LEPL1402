package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class)
public class InginiousTests {

    // @TODO These tests are really easy to bypass if the student has access to the sources. Maybe try to find smarter
    //       tests ...

    private int seqNumIt, seqNumRec = 0;

    private int [] nCalls = {1, 1, 3, 5, 9, 15, 25, 41, 67, 109, 177, 287, 465, 753, 1219, 1973, 3193, 5167, 8361, 13529};

    private Supplier<Integer> largeRNG = () -> (int) (Math.random()+1)*100000;

    private Supplier<Integer> fiboSeqIt = () -> Fibonacci.fiboIterative(seqNumIt++);

    private Supplier<Integer> fiboSeqRec = () -> Fibonacci.fiboIterative(seqNumRec++);


    @Test
    @Grade
    public void testRecursif(){

        for(int i = 0; i < 20; i++){
            Fibonacci.resetCount();
            Fibonacci.fiboRecursive(i);
            assertEquals(nCalls[i], Fibonacci.getCount());
        }

    }


    @Test(timeout = 100) // 100 ms timeout
    @Grade
    public void testIterative(){

        // Will t.o easily if implem is recursive (or bad)

        Integer [] seeds = Stream.generate(largeRNG).limit(10).toArray(Integer[]::new);

        for(Integer seed : seeds){
            Fibonacci.fiboIterative(seed);
        }

    }


    @Test
    @Grade
    public void testCorrectness(){
        Integer [] seqIt = Stream.generate(fiboSeqIt).limit(30).toArray(Integer[]::new);
        Integer [] seqRec = Stream.generate(fiboSeqRec).limit(30).toArray(Integer[]::new);

        assertEquals(0, (int) seqIt[0]);
        assertEquals(1, (int) seqIt[1]);
        assertEquals(1, (int) seqIt[2]);
        assertEquals(2, (int) seqIt[3]);
        assertEquals(3, (int) seqIt[4]);
        assertEquals(5, (int) seqIt[5]);

        for(int i = 0; i < seqIt.length; i++){
            assertEquals(seqIt[i], seqRec[i]);
        }

        for(int i = 2; i < seqIt.length; i++){
            assertEquals((int) seqIt[i-1] + seqIt[i-2], (int) seqIt[i]);
            assertEquals((int) seqRec[i-1] + seqRec[i-2], (int) seqRec[i]);
        }

    }




}
