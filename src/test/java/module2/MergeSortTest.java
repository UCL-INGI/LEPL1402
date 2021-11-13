package module2;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

//BEGIN STRIP
/*
 * Most of the code of this test suite is taken (and adapted) from an exercise of LSINF1121 course.
 * Original authors : Pierre Schaus, John Aoga
 * Modified by : Henri Piron
 */
//END STRIP
public class MergeSortTest {

    //BEGIN STRIP
    private final int arraySize = 10000;

    @Test()
    public void testSort(){
        int [] array = randomArray(arraySize);
        int [] copyArray = new int[arraySize];
        System.arraycopy(array, 0, copyArray, 0, arraySize);
        MergeSort.studentSort(array);
        Arrays.sort(copyArray);
        assertArrayEquals(copyArray, array);
    }

    /*
     * TODO: better evaluation of number of count. Sensitive to small variation in algorithm
    @Test()
    @Grade(value=1, cpuTimeout=1000)
    @GradeFeedback(onFail=true, message = "Your sort don't call the merge method enough")
    public void testNumberMergeCall(){

        MergeSort.counterRst();

        int[] array = randomArray(10000);
        MergeSort.studentSort(array);

        int count = MergeSort.getCounter();

        assertTrue(isArraySorted(array));
        assertEquals(count, 9999);

    }
    */

    @Test()
    public void testMerge(){
        int [] array = randomArray(arraySize);
        int [] copyArray = new int[arraySize];
        System.arraycopy(array, 0, copyArray, 0, arraySize);
        MergeSort.sort(array);
        Arrays.sort(copyArray);
        assertArrayEquals(copyArray, array);
    }
    //END STRIP

    @Test()
    public void testComplete(){
        int [] array = randomArray(arraySize);
        int [] copyArray = new int[arraySize];
        System.arraycopy(array, 0, copyArray, 0, arraySize);
        MergeSortBis.sort(array);
        Arrays.sort(copyArray);
        assertArrayEquals(copyArray, array);
    }

    private static boolean isArraySorted(int[] array){
        for(int i = 0 ; i < array.length -1; i++){
            if(array[i]>array[i+1])
                return false;

        }
        return true;
    }

    private static int[] randomArray(int size){
        int[] array = new int[size];
        Random rng = new Random();
        for(int i = 0 ; i < size ; i++){
            array[i]= rng.nextInt(Integer.MAX_VALUE);
        }
        return array;
    }

}
