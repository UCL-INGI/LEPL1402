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
        int s = a.length;
        int[] aux = new int[s];

        for(int n = 1 ; n< s ; n = n+n){
            for(int i = 0 ; i< s-n ; i += n+n){
                int lo= i;
                int mid = i+n-1;
                int hi = Math.min(i+n+n-1, s-1);
                studentMerge(a, aux , lo , mid , hi);
            }
        }
    }

    public static void studentMerge(int[] a, int[] aux, int lo, int mid, int hi) {



        @@student_merge@@
    }
    public static void studentSort(int[] a) {
        @@student_sort@@
    }

    @@student_additionnal@@

}
