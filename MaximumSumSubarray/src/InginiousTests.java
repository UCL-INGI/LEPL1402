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
    private Supplier<Integer> rng = () -> (int) (new Random().nextInt(20000)-10000); // gen -10.000 to 10.000

    private int[] generate_random_tab(int size){
      return Stream.generate(rng).limit(size).mapToInt(i -> i).toArray();
    }

    private int[] generate_negative_random_tab(int size){
      int[] tab = generate_random_tab(size);
      for(int i = 0; i<tab.length; i++){
        if(tab[i] > 0) {
          tab[i] = -tab[i];
        }
      }
      return tab;
    }

    private HashMap<String, Integer> get_max_and_index(int[] tab){
      HashMap<String, Integer> h = new HashMap<>();
      int index = 0;
      int max = tab[0];
      for(int i = 1; i<tab.length; i++){
        if(max < tab[i]){
          max = tab[i];
          index = i;
        }
      }
      h.put("max", max);
      h.put("index", index);
      return h;
    }

    /**
     * this solution comes from https://en.wikipedia.org/wiki/Maximum_subarray_problem
     */
    public int[] good_search(int[] a){
      int largest_ending_here = 0, best_start = 0, this_start = 0, end = 0, best_so_far = Integer.MIN_VALUE;
        for(int i = 0; i<a.length; i++){
            largest_ending_here += a[i];
            if(best_so_far < largest_ending_here){
                best_so_far = largest_ending_here;
                best_start = this_start;
                end = i;
            }
            if(largest_ending_here < 0){
                largest_ending_here = 0;
                this_start = i+1;
            }
        }
        int[] tab = {best_so_far, best_start, end};
        return tab;
    }

    @Test
    @Grade(cpuTimeout=3000, value = 1)
    @GradeFeedbacks({@GradeFeedback(message = "Your algorithm has not the expected time complexity !!", onTimeout = true),
    @GradeFeedback(message = "What happens when there is only one element?\n", onFail = true)})
    public void testOneElement(){
      int[] alone = {1};
      int[] expected = {1, 0, 0};
      assertArrayEquals(MaximumSubArray.maxSubArray(alone), expected);
    }

    @Test
    @Grade(cpuTimeout=3000, value = 1)
    @GradeFeedbacks({@GradeFeedback(message = "Your algorithm has not the expected time complexity !!", onTimeout = true),
    @GradeFeedback(message = "You don't pass the example.\n", onFail = true)})
    public void testExample(){
      int[] example = {-2,1,-3,4,-1,2,1,-5,4};
      int[] expected = good_search(example);
      assertArrayEquals(MaximumSubArray.maxSubArray(example), expected);
    }

    @Test
    @Grade(cpuTimeout=3000, value = 2)
    @GradeFeedbacks({@GradeFeedback(message = "Your algorithm has not the expected time complexity !!", onTimeout = true),
    @GradeFeedback(message = "What happens if the array contains only negative integers?\n", onFail = true)})
    public void testOnlyNegative(){
      for(int i = 0; i < 10; i++){
        int[] negative = generate_negative_random_tab(100);
        HashMap<String, Integer> max = get_max_and_index(negative);
        int[] expected = {max.get("max"), max.get("index"), max.get("index")};
        assertArrayEquals(MaximumSubArray.maxSubArray(negative), expected);
      }
    }

    @Test
    @Grade(cpuTimeout=3000, value = 2)
    @GradeFeedbacks({@GradeFeedback(message = "Your algorithm has not the expected time complexity !!", onTimeout = true),
    @GradeFeedback(message = "Your maxSubArray does not work!\n", onFail = true)})
    public void testCorrectness(){
      for(int i = 0; i < 10; i++){
        int[] tab = generate_random_tab(100);
        assertArrayEquals(MaximumSubArray.maxSubArray(tab), good_search(tab));
      }
    }

    @Test
    @Grade(cpuTimeout=3000, value = 10)
    @GradeFeedbacks({@GradeFeedback(message = "Your algorithm has not the expected time complexity !!", onTimeout = true),
    @GradeFeedback(message = "Your maxSubArray does not work!\n", onFail = true)})
    public void testComplexity(){
      for(int i = 0; i < 10; i++){
        int[] tab = generate_random_tab(10000);
        assertArrayEquals(MaximumSubArray.maxSubArray(tab), good_search(tab));
      }
    }

}
