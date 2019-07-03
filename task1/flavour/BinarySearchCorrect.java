package flavour;

public class BinarySearchCorrect {

    public static final boolean correctness = true;

    /*
     * Cette methode retourne l'index du tableau "arr" entre l'index "low" et l'index "high" 
     * ou se trouve l'element "elem" OU -1 si "elem" ne s'y trouve pas OU -2 si les parametres
     * ne respectent pas les preconditions.
     *
     * @pre low >= 0, high <= |arr|, low <= high, sorted(arr[low...high]) == true
     * @post retourne l'index dans lequel l'element recherche se trouve, -1 s'il n'est pas present.
     */
    public static int binarySearch(int [] arr, int low, int high, int elem) {

        // The "presumably" correct implementation, the one we'll give to students.

        if(low > high || low < 0 || arr == null ||  high > arr.length-1){
            return -2;
        }
        
        while (high >= low) { 
            
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

}