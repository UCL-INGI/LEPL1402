package module6;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ParallelMergeSortTest {
    //BEGIN STRIP

    private static Random rng = new Random();

    private static boolean isArraySorted(Integer[] array){
        for(int i = 0 ; i < array.length -1; i++){
            if(array[i]>array[i+1])
                return false;
        }
        return true;
    }

    @Test()
    public void testGlobal() {
        ParallelMergeSort.resetS();
        ParallelMergeSort.resetC();
        ParallelMergeSort.resetCom();

        int size = 5000;
        Integer[] array = new Integer[size];
        for(int i = 0 ; i < size ; i++) array[i] = rng.nextInt(10000);
        Integer[] copy = Arrays.copyOf(array, size);

        ParallelMergeSort task = new ParallelMergeSort(array, 0, size-1, new Integer[size], Comparator.comparing(Integer::intValue));

        new ForkJoinPool().invoke(task);

        if(ParallelMergeSort.counter<4000){
            fail("You didn't call merge enough time");
        }
        if(120>ParallelMergeSort.notSorter){
            fail("You called sort too many times");
        }
        assertTrue(isArraySorted(array));

    }

    @Test()
    public void tesComplexity() {

        int size = 600000;
        Integer[] array = new Integer[size];
        for(int i = 0 ; i < size ; i++) array[i] = rng.nextInt(10000);
        Integer[] copy = Arrays.copyOf(array, size);

        ParallelMergeSort task = new ParallelMergeSort(array, 0, size-1, new Integer[size], Comparator.comparing(Integer::intValue));
        new ForkJoinPool().invoke(task);

        assertTrue(isArraySorted(array));
    }
    //END STRIP
}
