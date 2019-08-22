import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MaxFinder {

    private final int nThreads,length,width,depth;
    private final int[][][] data;
    private final CyclicBarrier barrier;
    private int[] maxima;
    private int max;

    class Worker implements Runnable {
        int myRow,i;

        Worker(int row) {
            myRow = row;
            i = myRow;
        }
        public void run() {
            boolean notDone=true;
            while(notDone){
                int sum = 0;
                for(int k = 0 ; k < width ; k++){
                    for(int j = 0 ; j < depth ; j++){
                        sum += data[i][k][j];
                    }
                }
                maxima[myRow] = sum;
                try {
                    barrier.await();
                }catch(InterruptedException ex) {
                    return;
                }catch(BrokenBarrierException ex) {
                    return;
                }finally {
                    i += nThreads;
                    if(i>=length) notDone=false;
                }

            }

        }
    }

    private MaxFinder(int[][][] matrix, int nThreads) throws InterruptedException{
        data = matrix;
        this.nThreads = nThreads;
        length = matrix.length;
        width = matrix[0].length;
        depth = matrix[0][0].length;
        maxima = new int[nThreads];
        max = Integer.MIN_VALUE;
        Runnable barrierAction = () -> {
            for(int i = 0 ; i < nThreads ; i++)
                max = Math.max(max, maxima[i]);
        };
        barrier = new CyclicBarrier(nThreads, barrierAction);

        List<Thread> threads = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(new Worker(i));
            threads.add(thread);
            thread.start();
        }

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
