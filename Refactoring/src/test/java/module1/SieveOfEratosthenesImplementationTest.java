package module1;

import org.junit.Test;

import java.util.BitSet;
import java.util.Random;

import static org.junit.Assert.*;

public class SieveOfEratosthenesImplementationTest {


    //BEGIN STRIP
    @Test
    public void testRandom(){
        Random rng = new Random();
        int r = rng.nextInt(15000);

        assertEquals(np(r), Sieve.numberOfPrime(r));
    }
    //END STRIP

    @Test
    public void testLow(){
        assertEquals(4, Sieve.numberOfPrime(10));
        assertEquals(25, Sieve.numberOfPrime(100));
        assertEquals(Sieve.numberOfPrime(1000), 168);
        /*SieveOfEratosthenesImplementation.bits.set(0,true);
        SieveOfEratosthenesImplementation.bits.set(1,true);
        
        if(SieveOfEratosthenesImplementation.bits.cardinality() != 168+2 && SieveOfEratosthenesImplementation.bits.cardinality() != 1000-168-2){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "You are not using the bitset properly" + SieveOfEratosthenesImplementation.bits.cardinality());
        }*/
    }

    //BEGIN STRIP
    @Test
    public void testComplexity(){
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
    //END STRIP

}