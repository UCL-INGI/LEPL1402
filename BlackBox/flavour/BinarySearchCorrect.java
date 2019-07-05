package flavour;

import src.BinarySearchFlavour;
import src.FlavourExercise;

public class BinarySearchCorrect extends BinarySearchFlavour {


    private static final boolean correctness = true;

    /*
     * Cette methode retourne l'index du tableau "arr" entre l'index "low" et l'index "high" 
     * ou se trouve l'element "elem" OU -1 si "elem" ne s'y trouve pas OU -2 si les parametres
     * ne respectent pas les preconditions.
     *
     * @pre low >= 0, high <= |arr|, low <= high, sorted(arr[low...high]) == true
     * @post retourne l'index dans lequel l'element recherche se trouve, -1 s'il n'est pas present.
     */
    public int binarySearch(int [] arr, int low, int high, int elem) {
        return super.binarySearch(arr, low, high, elem);
    }

    @Override
    public boolean correctness() {
        return correctness;
    }
}