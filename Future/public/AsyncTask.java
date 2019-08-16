import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncTask {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * Return a Future that return the first index of elem inside longArray or -1.
     * longArray is a non ordered random array.
     * You have to use executor to success this task. Don't hesite to read the
     * documentation of Future and ExecutorService in order to understand what
     * you are supposed to do.
     */
    public static Future<Integer> indexOf(int[] longArray, int elem){
        //TODO
    }

    /**
     * Do some usefull calculation
     */
    public static Integer someCalculations(int[] longArray, int i){
        //HIDDEN to you
    }

    /**
     * Once all thread are finished, do another very usefull calculation.
     */
    public static void someOtherCalculations(List<Integer> results, Integer calculated){
        //HIDDEN TO YOU
    }

    /**
     * Return a list of Future that try to find the index of all elements
     * inside array.
     * Each Future must run in a new thread
     */
    public static List<Future<Integer>> createFutures(int[] array, int[] elements){
        //TODO
    }

    /**
     * This code must work thanks to your implementation of the to functions above.
     */
    public static void test(int[] array, int[] elements, int randomNumber){
        //get the list of all futures that runs inside different threads
        List<Future<Integer>> futures = createFutures(array, elements);
        //while the threads are executing asynchronously, we can perform
        //some usefull tasks
        Integer calc = someCalculations(array, randomNumber);

        //Then we need the result of the threads
        List<Integer> res = new ArrayList<>(futures.size());
        try {
            for(int i = 0; i< futures.size(); i++){
                res.add(futures.get(i).get());
            }
        } catch(InterruptedException e){

        } catch(ExecutionException e){

        }

        //finally we perform other calculation with the results of the threads
        //and the other calculation we made
        SomeOtherCalculations(res, calc);

    }

}
