package module6;

import java.util.Comparator;
import java.util.concurrent.RecursiveAction;

/**
 * In this task, you will be asked to implement a special kind of merge sort using the RecursiveAction, ForkJoinPool and ForkJoinTask interfaces.
 * You will understand by reading these interfaces that the merge sort you must implement is not only recursive but in parallel.
 *      - RecursiveAction: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html
 *      - ForkJoinPool: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html
 *      - ForkJoinTask: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinTask.html
 *
 * You have to complete the following class : ParallelMergeSort
 *
 * Here is an example of how we can use your implementation :
 *
 *     .. code-block:: java
 *
 *         int size = 1000;
 *         Integer[] array = new Integer[size];
 *         for(int i = 0 ; i < size ; i++){
 *             array[i] = rng.nextInt(10000);
 *         }
 *         ParallelMergeSort task = new ParallelMergeSort(array, 0, size-1, new Integer[size],
 *                         Comparator.comparing(Integer::intValue));
 *         new ForkJoinPool().invoke(task);
 *
 *
 * You will see that we've fixed a threshold, the reason we are using a threshold is easy to understand.
 * Since you want your code to be effective on very large array,
 * starting a thread for every element can be very ressource consuming and thus you would be losing the advantage you had of using concurrent programming.
 * So when the subarray you're working on is smaller than the threshold, it simply run a normal sort.
 */
public class ParallelMergeSort<E> extends RecursiveAction {

    private volatile E[] array, aux;
    private int lo,hi;
    private Comparator<? super E> comp;

    private static final int threshold = 128;


    public ParallelMergeSort(E[] a, int lo, int hi, E[] aux, Comparator<? super E> comp) {
        //TODO by Student
    }

    /*
     * Run a normal sort when the difference between hi and lo is under the threshold
     * Otherwise : Split the sub array in two and start the sort on each part of the array simultaneously
     */
    @Override
    protected void compute() {
        //TODO by student
    }

    //Sort array between lo and hi using merge sort
    private void sort(int lo, int hi){
        //TODO by Student
    }

    //merge two subarray and keep them sorted
    private void merge(int lo, int mid, int hi){

        //TODO by Student
    }

    //BEGIN STRIP
    public static int counter=0, notSorter=0, sorter=0;;
    public static void resetC(){counter=0;}
    public static void resetS(){sorter=0;}
    public static void resetCom(){notSorter=0;}
    //END STRIP
}
