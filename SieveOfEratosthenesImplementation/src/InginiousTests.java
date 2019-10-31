package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import templates.*;


import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {



    @Test
    @Grade(value=10 , custom = true, cpuTimeout=1000)
    public void testRandom() throws CustomGradingResult{
        Random rng = new Random();
        int r = rng.nextInt(15000);

        assertEquals(np(r), Sieve.numberOfPrime(r));

    }

    @Test
    @Grade(value=10 , custom = true, cpuTimeout=1000)
    public void testLow() throws CustomGradingResult{
        assertEquals(4, Sieve.numberOfPrime(10));
        assertEquals(25, Sieve.numberOfPrime(100));
        assertEquals(Sieve.numberOfPrime(1000), 168);
        /*Sieve.bits.set(0,true);
        Sieve.bits.set(1,true);
        
        if(Sieve.bits.cardinality() != 168+2 && Sieve.bits.cardinality() != 1000-168-2){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "You are not using the bitset properly" + Sieve.bits.cardinality());
        }*/
    }

    @Test
    @Grade(value=10 , custom = true, cpuTimeout=2000)
    public void testComplexity() throws CustomGradingResult{
        assertEquals(5761455, Sieve.numberOfPrime(100_000_000));
    }


    /*
     *
     * Code from : https://gist.github.com/dukky/6265182
     *
     */

    public static int np(int n){
        BitSet bits = new BitSet(n+1);
        bits.clear();
        bits.flip(0, n);
        double csqrtn = Math.ceil(Math.sqrt(n));
        for (int i = 2; i < csqrtn; ++i)
            if (bits.get(i))
                for (int j = i * i; j < n; j += i)
                    bits.set(j, false);
        return bits.cardinality() - 2;
    }

}