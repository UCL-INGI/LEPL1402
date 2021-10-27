package module2;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArraySearchTest {

    // generate random number
    private int tabMaxSize = (int) Math.pow(10, 6);
    private Supplier<Integer> rng = () -> (int) ((Math.random()*tabMaxSize-1)+1);

    private int[] generate_ordered_tab(){
        int[] tab = new int[tabMaxSize];
        int val = -rng.get();
        for(int i = 0; i<tabMaxSize-1; i++){
            tab[i] = val;
            val += 2;
        }
        return tab;
    }

    //BEGIN STRIP
    public int good_search(int[] tab, int elem){
        int start = 0;
        int end = tab.length - 1;
        boolean b = false;
        while(!b && start <= end){
            int mid = (start+end)/2;
            if(tab[mid] == elem) return mid;
            else if(tab[mid] < elem) start = mid+1;
            else end = mid -1;
        }
        return -1;
    }
    //END STRIP

    @Test
    public void testCorrectness(){
        int[] tab = generate_ordered_tab();
        for(int i = 0; i < 100; i++){
            int random = rng.get();
            assertEquals(ArraySearch.search(tab, tab[random]), random);
            assertEquals(ArraySearch.search(tab, tab[random]+1), -1);
        }
    }

    //BEGIN STRIP
    //test complexity
    @Test
    public void testComplexity(){
        int[] tab = generate_ordered_tab();
        long[] timers = new long[4];
        timers[0] = System.currentTimeMillis();
        for(int i = 0; i < 2000; i++){
            ArraySearch.search(tab, tab[rng.get()]);
        }
        timers[1] = System.currentTimeMillis();
        timers[2] = System.currentTimeMillis();
        for(int i = 0; i < 2000; i++){
            good_search(tab, tab[rng.get()]);
        }
        timers[3] = System.currentTimeMillis();
        assertFalse((timers[1]-timers[0]) - (timers[3]-timers[2]) > 30);
    }
    //END STRIP

}
