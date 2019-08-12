package templates;

public class MergeSortBis {


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

        @@student_merge@@
    }
    /**
     * Rearranges the array in ascending order, using the natural order
     */
    public static void sort(int[] a) {
        @@student_sort@@
    }

    @@student_additionnal@@

}
