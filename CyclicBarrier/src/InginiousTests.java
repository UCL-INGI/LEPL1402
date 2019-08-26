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

import templates.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private static Random rng = new Random();
    private static boolean firstTestFlag=false;

    private static int[][][] generateArray(int size, int nThread){
        int[][][] matrix = new int[100*nThread][size][size];

        for(int i = 0 ; i < 100*nThread ; i++){
            for(int j = 0 ; j < size ; j++){
                for(int k = 0 ; k < size ; k++ ){
                    matrix[i][j][k] = rng.nextInt(100);
                }
            }
        }

        return matrix;
    }

    private static int maxSum(int[][][] matrix){
        int max = Integer.MIN_VALUE;
        for(int i = 0 ; i < matrix.length ; i++){
            int sum = 0;
            for(int j = 0 ; j < matrix[0].length ; j++){
                for(int k = 0 ; k < matrix[0][0].length ; k++){
                    sum+= matrix[i][j][k];
                }
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void testCreateWorker() throws CustomGradingResult{

        int[][][] matrix = generateArray(100,11);

        int max = maxSum(matrix);

        int result = MaxFinderF.getMaxSum(matrix, 11);

        firstTestFlag = 11==MaxFinderF.counter;

        if(firstTestFlag){
            throw new CustomGradingResult(TestStatus.SUCCESS, 5, "Right amount of worker started!");
        }

        throw new CustomGradingResult(TestStatus.FAILED, 0, "You didn't start the right amount of worker");

    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void testMaxFinder() throws CustomGradingResult{

        if(!firstTestFlag){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "You didn't start the right amount of worker");
        }
        int[][][] matrix = generateArray(100,11);
        
        int max = maxSum(matrix);

        int result = MaxFinderF.getMaxSum(matrix, 10);

        if(max==result){
            throw new CustomGradingResult(TestStatus.SUCCESS, 5, "MaxFinder implementation is working properly");
        }
        
        throw new CustomGradingResult(TestStatus.FAILED, 0, "MaxFinder implementation is not working");

    }
    
    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void testWorker() throws CustomGradingResult{

        if(!firstTestFlag){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "You didn't start the right amount of worker");
        }

        int[][][] matrix = generateArray(100,11);
        
        int max = maxSum(matrix);

        int result = MaxFinderWorker.getMaxSum(matrix, 10);

        if(max==result){
            throw new CustomGradingResult(TestStatus.SUCCESS, 5, "Worker implementation is working properly");
        }
        
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Worker implementation is not working");

    }
    
    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    public void testGlobal() throws CustomGradingResult{

        if(!firstTestFlag){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "You didn't start the right amount of worker");
        }
        
        int[][][] matrix = generateArray(100,11);
        
        int max = maxSum(matrix);

        int result = MaxFinder.getMaxSum(matrix, 10);

        if(max==result){
            throw new CustomGradingResult(TestStatus.SUCCESS, 5, "Both working well implementation is working properly");
        }
        
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Result is not correct");

    }

}
