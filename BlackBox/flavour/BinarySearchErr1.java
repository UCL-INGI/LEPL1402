package flavour;

import src.BinarySearchFlavour;

<<<<<<< HEAD
public class BinarySearchErr1 extends BinarySearchFlavour implements ExerciseFlavour {
=======
public class BinarySearchErr1 extends BinarySearchFlavour {
>>>>>>> 4b94073c605519203bf270963f07afb5088fa490

    private static final boolean correctness = false;

    @Override
    public int binarySearch(int [] arr, int low, int high, int elem) {

        if(low > high || low < 0 || high > arr.length-1){
            return -2;
        }

        while (high > low) { // mistake is here

            int mid = (low + high) / 2;

            if (arr[mid] == elem){
                return mid;
            }

            if (arr[mid] > elem){
                high = mid - 1;
            } else {
                low = mid + 1;
            }

        }

        // not found.
        return -1;
    }

    @Override
    public boolean correctness() {
        return correctness;
    }

}
