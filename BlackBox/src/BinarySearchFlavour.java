package src;
public abstract class BinarySearchFlavour {

    public BinarySearchFlavour() {
    }

    public boolean checkPre(int [] arr, int low, int high) {
        if (arr == null)
            return false;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i+1])
                return false;
        }
        return low <= high && low >= 0 && high <= arr.length - 1;
    }

    public abstract int binarySearch(int [] arr, int low, int high, int elem);
    public abstract boolean correctness();
    public abstract String feedbackMessage();
}
