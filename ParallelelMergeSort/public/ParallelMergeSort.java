
import java.util.Comparator;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

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

}