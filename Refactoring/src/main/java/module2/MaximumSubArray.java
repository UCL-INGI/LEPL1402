package module2;

/**
 * In this exercise you must write a method maxSubArray that finds the largest sum in a contiguous subarray.
 *
 * For example if the array is a = [-2,1,-3,4,-1,2,1,-5,4]. A contiguous subarray is defined by two indices, i and j such that 0 <= i < j < a.lenght.
 * For instance the contiguous subarray defined by i = 2 and j = 4 is [-3, -1].
 * The sum of this subarray is -4.
 *
 * The goal of your code is to find the first subarray that has the maximum sum. In the example, this subarray is [4, -1, 2, 1] with a sum of 6.
 *
 * Your algorithm should run in O(n) and you can use the following properties to help you get this complexity:
 *      - if the sum of a subarray is less or equal to 0, then every extension to it (i.e., any subarray just after it) has a sum greater or equal to it
 *      - Let assume that you have a contiguous subarray from i to j with a sum of S.
 *        If a[j+1] > S + a[j+1] then you have no interest in extending the contiguous subarray but should start a new one from j+1
 */
public class MaximumSubArray {

    /**
     * Find the contiguous subarray with the maximal sum
     *
     * @param a a non-empty array
     * @return A triplet (sum, start, end) with sum the sum of the subarray and `start` and `end` the
     *         start and end of the subarray
     *
     * For example for the array [-2,1,-3,4,-1,2,1,-5,4] your method should return [6, 3, 6]
     */
    public static int[] maxSubArray(int[] a){
        // TODO: students
        return null;
    }
}
