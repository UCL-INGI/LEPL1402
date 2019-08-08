package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

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

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Your search does not work\n", onFail = true)})
    public void testCorrectness(){
      int[] tab = generate_ordered_tab();
      for(int i = 0; i < 100; i++){
        int random = rng.get();
        assertEquals(Search.search(tab, tab[random]), random);
        assertEquals(Search.search(tab, tab[random]+1), -1);
      }
    }

    //test complexity
    @Test
    @Grade(cpuTimeout=3000)
    @GradeFeedback(message = "Are you sure to have the best complexity possible?\n", onFail = true)
    public void testComplexity(){
      int[] tab = generate_ordered_tab();
      long[] timers = new long[4];
      timers[0] = System.currentTimeMillis();
      for(int i = 0; i < 2000; i++){
        Search.search(tab, tab[rng.get()]);
      }
      timers[1] = System.currentTimeMillis();
      timers[2] = System.currentTimeMillis();
      for(int i = 0; i < 2000; i++){
        good_search(tab, tab[rng.get()]);
      }
      timers[3] = System.currentTimeMillis();
      assertFalse((timers[1]-timers[0]) - (timers[3]-timers[2]) > 30);
    }

}
