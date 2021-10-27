package module2;

/**
 * In this exercise we ask you to implement the top-down merge sort algorithm on an array of integer. You have to use 2 methods:
 *
 * - Sort, this method has to divide the array and apply merge.
 * - Merge, this method has to merge the array given the 2 index, it receives from sort 3 index, lower bound, middle and high.
 *   It also receives a copy of the first array.
 *
 *  The tests are build to make you able to test the two methods separly but we recommand you to test them by yourself.
 */
public class MergeSort {

    //BEGIN STRIP
    private static int counter=0;

    public static void counterRst(){
        counter=0;
    }

    public static int getCounter(){
        return counter;
    }

    public static void merge(int[] a, int[] aux, int lo, int mid, int hi) {

        counter++;

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                aux[k] = a[j++];
            } else if (j > hi) {
                aux[k] = a[i++];
            } else if (a[j] - a[i] < 0) {
                aux[k] = a[j++];
            } else {
                aux[k] = a[i++];
            }
        }
    }

    public static void sort(int[] a) {
        int[] aux = new int[a.length];
        sortRecursiveWithNameTheStudentWontNormallyFind(a, aux, 0, a.length-1);
    }

    private static void sortRecursiveWithNameTheStudentWontNormallyFind(int [] a, int [] aux, int lo, int hi) {
        if (lo >= hi)
            return;
        int mid = lo + (hi-lo)/2;
        sortRecursiveWithNameTheStudentWontNormallyFind(a, aux, lo, mid);
        sortRecursiveWithNameTheStudentWontNormallyFind(a, aux, mid+1, hi);
        studentMerge(a, aux, lo, mid,  hi);
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i];
        }
    }
    //END STRIP

    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: aux[lo..hi] is sorted and a is left unchanged
     *
     * For example, let a = [4, 5, 1, 3], lo = 0, mid = 1, hi = 3
     * We have that the portion [4, 5] and [1, 3] are sorted
     *
     * The merge function take this two portions and put them in aux
     * in the correct order
     *
     * At the end of the execution, we have a = [4, 5, 1, 3] and
     * aux = [1, 3, 4, 5]
     */
    public static void studentMerge(int[] a, int[] aux, int lo, int mid, int hi) {
        // TODO By student
    }

    //BEGIN STRIP
    /**
     *
     * @param a the array to sort from lo to hi
     * @param aux the auxiliary array used in the merge function
     * @param lo the lower bound index for the sort
     * @param hi the upper bound index for the sort
     * @return nothing. The a array is sorted from lo to hi
     *
     * This function is recursive. This means that you should first call it
     * on the first half part of the array, then the other half. Once this is done,
     * you should merge the two parts together.
     *
     * So if a = [1, 4, 2, 6, 3, 10], you should recursively call the method on
     * the part with [1, 4, 2] and [6, 3, 10] (! use the lo and hi index) then merge
     * these parts with the merge function.
     *
     * hint: since the mergeSort function modify only from lo to hi, you can call it
     * successively on different part of the array
     */
    public static void mergeSort(int a[], int [] aux,int lo, int hi) {
        // TODO By student
    }
    //END STRIP

    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void studentSort(int[] a) {
        int [] aux = new int[a.length];
        // TODO: merge the array with a recursive function
    }

}

//BEGIN STRIP
class MergeSortBis {


    private static int counter=0;

    public static void counterRst(){
        counter=0;
    }

    public static int getCounter(){
        return counter;
    }

    /**
     * Pre-conditions: a[lo..mid] and a[mid+1..hi] are sorted
     * Post-conditions: a[lo..hi] is sorted
     */
    public static void merge(int[] a, int[] aux, int lo, int mid, int hi) {

        counter++;
    }
    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void sort(int[] a) {

    }

}
//END STRIP
