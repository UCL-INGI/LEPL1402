package module2;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

public class MaximumSubArrayTest {

    //BEGIN STRIP
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
    //END STRIP

    @Test
    public void testOneElement(){
        int[] alone = {1};
        int[] expected = {1, 0, 0};
        assertArrayEquals(MaximumSubArray.maxSubArray(alone), expected);
    }

    @Test
    public void testExample(){
        int[] example = {-2,1,-3,4,-1,2,1,-5,4};
        int[] expected = good_search(example);
        assertArrayEquals(MaximumSubArray.maxSubArray(example), expected);
    }

    //BEGIN STRIP
    @Test
    public void testOnlyNegative(){
        for(int i = 0; i < 10; i++){
            int[] negative = generate_negative_random_tab(100);
            HashMap<String, Integer> max = get_max_and_index(negative);
            int[] expected = {max.get("max"), max.get("index"), max.get("index")};
            assertArrayEquals(MaximumSubArray.maxSubArray(negative), expected);
        }
    }

    @Test
    public void testCorrectness(){
        for(int i = 0; i < 10; i++){
            int[] tab = generate_random_tab(100);
            assertArrayEquals(MaximumSubArray.maxSubArray(tab), good_search(tab));
        }
    }

    @Test
    public void testComplexity(){
        int[] tab = generate_random_tab(2000);
        int[] tab2 = generate_random_tab(50000);
        long[] exec = new long[2];
        //try cubic complexity
        exec[0] = System.currentTimeMillis();
        MaximumSubArray.maxSubArray(tab);
        exec[1] = System.currentTimeMillis();

        assertFalse("The time complexity of your algorithm is approximatively O(n^3). " +
                "A solution is possible in time complexity O(n^2) with partial sums.", (exec[1]-exec[0]) > 1000);

        exec[0] = System.currentTimeMillis();
        MaximumSubArray.maxSubArray(tab2);
        exec[1] = System.currentTimeMillis();

        assertFalse("The time complexity of your algorithm is approximatively O(n^2). " +
                "A solution is possible in time complexity O(n) : either the maximum subarray sum ending at position" +
                " i+1 includes the maximum subarray sum ending at position i as prefix, or it doesn't.", (exec[1]-exec[0]) > 1000);

    }
    //END STRIP
}
