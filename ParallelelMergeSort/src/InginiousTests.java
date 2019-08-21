package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;


import templates.*;

import java.util.Random;
import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private static Random rng = new Random();

    private static boolean isArraySorted(Integer[] array){
        for(int i = 0 ; i < array.length -1; i++){
            if(array[i]>array[i+1])
                return false;
        }
        return true;
    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void testGlobal() throws CustomGradingResult{
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
            throw new CustomGradingResult(TestStatus.FAILED,0,"You didn't call merge enough time");
        }
        if(120>ParallelMergeSort.notSorter){
            throw new CustomGradingResult(TestStatus.FAILED,0,"You called sort too many times");
        }
        assertTrue(isArraySorted(array));

    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void tesComplexity() throws CustomGradingResult{

        int size = 600000;
        Integer[] array = new Integer[size];
        for(int i = 0 ; i < size ; i++) array[i] = rng.nextInt(10000);
        Integer[] copy = Arrays.copyOf(array, size);

        ParallelMergeSort task = new ParallelMergeSort(array, 0, size-1, new Integer[size], Comparator.comparing(Integer::intValue));
        new ForkJoinPool().invoke(task);

        assertTrue(isArraySorted(array));

    }



}
