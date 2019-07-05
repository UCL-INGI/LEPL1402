package src;

import flavour.*;

public class Exercise implements ExerciseFlavour {

    private static int count = 0;
    private static BinarySearchFlavour currentFlavour;

    // Add manually instances of your falvours here !
    private static final BinarySearchFlavour[] allFlavours = new BinarySearchFlavour[]{
        new BinarySearchCorrect(), new BinarySearchErr1(), new BinarySearchErr2(),
            new BinarySearchErr3(), new BinarySearchErr4(), new BinarySearchErr5(),
            new BinarySearchErr6()
    };

    // BUGGED : Flavour "Err4" yields an infinite loop... Maybe adapt the JailRunner to measure CPU time.

    public static final int nImplems = allFlavours.length;

    public static int binarySearch(int [] arr, int low, int high, int elem) {
        currentFlavour = allFlavours[count];
        int retVal = currentFlavour.binarySearch(arr, low, high, elem);
        return retVal;
    }


    @Override
    public boolean correctness() {
        return allFlavours[count].correctness();
    }

}
