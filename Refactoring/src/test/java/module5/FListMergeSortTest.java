package module5;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FListMergeSortTest {
    private static Random rng = new Random();

    public static int randomInt(){
        return rng.nextInt(10000);
    }

    private static boolean isSorted(FList<Integer> fl) {
        if(fl.isEmpty()) return true;

        if(fl.tail().isEmpty()) return true;

        if(fl.head() <= fl.tail().head()) return isSorted(fl.tail());
        else return false;

    }

    @Test()
    public void testBasic() {

        FList<Integer> fl = FList.nil();
        fl = fl.cons(199);
        fl = fl.cons(249);
        fl = fl.cons(133);
        fl = fl.cons(329);
        fl = fl.cons(592);
        fl = fl.cons(253);
        fl = fl.cons(145);
        fl = fl.cons(700);

        FList<Integer> supposedSorted;
        try {
            supposedSorted = FListMergeSort.mergeSort(fl);
        }
        catch (Exception e) {
            fail("Your code produced the following exception: " + e);
            return;
        }

        assertEquals(133, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(145, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(199, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(249, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(253, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(329, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(592, (int)supposedSorted.head());
        supposedSorted = supposedSorted.tail();
        assertEquals(700, (int)supposedSorted.head());
    }

    @Test()
    public void testGlobal() {
        FList<Integer> fl = FList.nil();
        int[] array = new int[1000];
        for(int i = 0 ; i < 1000 ; i++){
            int r = randomInt();
            fl = fl.cons(r);
            array[i] = r;
        }
        Arrays.sort(array);

        FList<Integer> supposedSorted = FListMergeSort.mergeSort(fl);

        //BEGIN STRIP
        if(FListMergeSort.counter<1000){
            fail("mergeSort is not recursive");
        }
        //END STRIP


        if(!isSorted(supposedSorted)){
            fail("mergeSort does not sort correctly");
        }

        FList<Integer> copy = supposedSorted;
        for(int i = 0 ; i < 1000 ; i++){
            if(array[i]!=copy.head()) {
                fail("Some elements have changed");
            }
            copy = copy.tail();
        }
    }


    @Test()
    public void testComplexity() {
        int size = 10000;

        FList<Integer> fl = FList.nil();
        int[] array = new int[size];
        for(int i = 0 ; i < size ; i++){
            int r = randomInt();
            fl = fl.cons(r);
            array[i] = r;
        }
        Arrays.sort(array);
        long t = System.currentTimeMillis();
        FList<Integer> supposedSorted = FListMergeSort.mergeSort(fl);
        long timeTaken = System.currentTimeMillis()-t;
        FList<Integer> copy = supposedSorted;
        for(int i = 0 ; i < size ; i++){
            if(array[i]!=copy.head()) {
                fail("Some elements have changed");
            }
            copy = copy.tail();
        }
        if(timeTaken>4500){
            fail("Your implementation is too slow: " + timeTaken);
        }
    }
}
