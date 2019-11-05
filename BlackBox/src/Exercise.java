package src;

import flavour.*;

public class Exercise implements ExerciseFlavour {

    private static int count = 0;
    private static BinarySearchFlavour currentFlavour;

    // Add manually instances of your flavours here !
    private static final BinarySearchFlavour[] allFlavours = new BinarySearchFlavour[]{
        new BinarySearchCorrect() , new BinarySearchErr1(), new BinarySearchErr2(),
            new BinarySearchErr3(), new BinarySearchErr4(), new BinarySearchErr5(),
            new BinarySearchErr6()
    };


    public static final int nImplems = allFlavours.length;
    public static final int nBugs = 6; // the number of flavours that are incorrect

    public static int binarySearch(int [] arr, int low, int high, int elem) {
        currentFlavour = allFlavours[count];
        int retVal = currentFlavour.binarySearch(arr, low, high, elem);
        return retVal;
    }


    public boolean correctness() {
        return allFlavours[count].correctness();
    }

}
