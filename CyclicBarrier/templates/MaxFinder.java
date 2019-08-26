package templates;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MaxFinder {

   private final int nThreads,length,width,depth;
    private final int[][][] data;
    private final CyclicBarrier barrier;
    private int[] sums;
    private int max;
    public static int counter=0;

    /*
     * Worker constructor takes only one parameter int r, which is his associated row number
     * A worker is responsible of the calculation of the sum of each 2D-Array with index%r == row;
     *
     * Run should compute the sum of a 2D-array and store the result in sums[] then wait for the cyclic barrier to get the result
     * And restart computing nThreads further
     */
    class Worker implements Runnable {
        
        @@studentWorker@@
        
    }
	
    
    /*
     *
     * Initialize all the instance variable
     *
     */
    private MaxFinder(int[][][] matrix, int nThreads) throws InterruptedException{
        
        @@StudentMaxFinderConstructor@@
        

        // wait until done
        for (Thread thread : threads)
            thread.join();
    }
    /*
    * subSize is the length of the subarray
    * rowSize is the rowlength for all the array
    *
    */
    public static int getMaxSum(int[][][] matrix, int nThreads){
        try{
            MaxFinder mf = new MaxFinder(matrix, nThreads);
            return mf.max;
        }catch(InterruptedException e){
            return -1;
        }

    }
}
