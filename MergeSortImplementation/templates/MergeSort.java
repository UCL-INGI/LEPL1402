package templates;

public class MergeSort {


    private static int counter=0;

    public static void counterRst(){
        counter=0;
    }

    public static int getCounter(){
        return counter;
    }

    public static void merge(int[] a, int[] aux, int lo, int mid, int hi) {

        counter++;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j] - aux[i] < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
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
    }

    public static void studentMerge(int[] a, int[] aux, int lo, int mid, int hi) {
        @@student_merge@@
    }
    public static void studentSort(int[] a) {
        @@student_sort@@
    }

    @@student_additionnal@@

}
