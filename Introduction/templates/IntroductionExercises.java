package templates;

public class IntroductionExercises {

    public static int variable = 0;

    public static int[] squares;

    /*
     * Function that bound variable to value
     */
    @@student_attribute@@

    /*
     * Function that return the addition of the two parameters
     */
    @@student_add@@

    /*
     * return true is a and b are equal
     */
    @@student_equals@@

    /*
     * Function that return the max between a and b
     * You must use a ternary operation
     */
    public static int max(int a, int b){
        @@student_max@@
    }

    /*
     * Function that return the middle value.
     * If a > b > c, the function must return b.
     * If two value are equals, return -1.
     */
    @@student_middleValue@@

    /*
     * This function must return :
     * "Good morning, sir!" if str is "Morning"
     * "Good evening, sir!" if str is "Evening"
     * "Hello, sir!" otherwise
     * Use a switch case statement
     * Your implementation must be case sensitive
     */
    @@student_salutation@@

    /*
     * This function must return a new array of length 3
     * The first element of this new array is the last element of a
     * The second element is the first element of a
     * The last element is the middle element of a
     */
    @@student_lastFirstMiddle@@

    /*
     * This function must return the sum of the elements of array using a for loop
     */
    @@student_sum@@

    /*
     * return the maximum element of array using a while loop
     */
    @@student_maxArray@@

    /*
     * Using the argument of the program
     * Bound the variable squares with the square of
     * the elements passed in argument.
     * Look at the java API : https://docs.oracle.com/javase/8/docs/api/index.html
     * If an exception occurs, assign the value 0 at the index where its occurs
     */
    public static void main2(String... args){
        @@student_main@@
    }

}
