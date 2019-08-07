package templates;

public class QuickSort {

    private static int count = 0;

    // This class should not be instantiated.
    private QuickSort() {
    }

    public static void swap(Object[] a, int i, int j){
      count++;
        assert a != null;
        assert 0 <= i && i < a.length;
        assert 0 <= j && j < a.length;
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static int partition(Comparable[] a, int left, int right) {
      count++;
        int k, pivot = left;
        Comparable center = a[left];

        for(k=left+1; k <= right; k++) {
            if (a[k].compareTo(center) < 0) {
                pivot++;
                swap(a,k,pivot);
            }
        }

        swap(a,left,pivot);
        return pivot;
    }

    public static void sort(Comparable[] a) {
        @   @sort_code@@
    }

    public static void sort(Comparable[] a, int left, int right) {
        @   @sort_code2@@
    }

    public static void swap_student(Object[] a, int i, int j) {
        @   @exch_code@@
    }

    public static int partition_student(Comparable[] a, int left, int right) {
        @   @partition_code@@
    }

    public static int get_count(){
      return count;
    }

    public static void reset_count(){
      count = 0;
    }

}
