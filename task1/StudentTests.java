import flavour.BinarySearchCorrect;
import flavour.BinarySearchErr1;
import org.junit.Test;



import static org.junit.Assert.*;

public class StudentTests {

    public static int count = 0;


    public static int binarySearch(int [] arr, int low, int high, int elem) {
        if(count == 0) {
            return BinarySearchCorrect.binarySearch(arr, low, high, elem);
        } else if(count == 1) {
            return BinarySearchErr1.binarySearch(arr, low, high, elem);
        }
        return Integer.MIN_VALUE;
    }


    // STUDENT CODE WILL BE INSERTED HERE
    @Test
    public void test(){
        /*
        int [] arr = new int[] {1, 3, 5, 6, 8, 10, 11, 13, 17, 42, 100, 110, 121};
        int res = binarySearch(arr, 0, arr.length-1, 10);
        assertEquals(5, res);

         */
        @        @student_code@@
    }
    // END OF STUDENT CODE

}