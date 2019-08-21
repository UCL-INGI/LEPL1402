package templates;

import java.util.Comparator;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort<E> extends RecursiveAction {

    private volatile E[] array, aux;
    private int lo,hi;
    private Comparator<? super E> comp;

    private static final int threshold = 128;

    public static int counter=0, notSorter=0, sorter=0;;

    public static void resetC(){counter=0;}
    public static void resetS(){sorter=0;}
    public static void resetCom(){notSorter=0;}

    public ParallelMergeSort(E[] a, int lo, int hi, E[] aux, Comparator<? super E> comp) {
        @@studentConstructor@@
    }

    @Override
    protected void compute() {
        notSorter++;
		@@studentCompute@@
    }

    private void sort(int lo, int hi){
        sorter++;
        @@studentSort@@
    }

    private void merge(int lo, int mid, int hi){
        counter++;
		@@studentMerge@@
    }

}
