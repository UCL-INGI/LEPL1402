package module1;

/**
 * Welcome to the first task of a long series!
 *
 * This serie of inginious tasks will accompany you throughout the quadrimester to learn java and apprehend the material seen in class.
 * Successful completion of all tasks is a major step towards the success of this course.
 * We encourage you to try to do them yourself without going on the Internet.
 *
 * We also encourage you to try your solutions by yourself on you computer.
 * To do so take a look at this tutorial https://lepl1402.readthedocs.io/en/latest/tools/index.html
 * on the tools you will need.
 *
 * This first task will cover the basics of java step by step.
 * In most exercises we want you to paste the **signature** and the **body** of the methods you create.
 * In other cases we just wan to you paste the **body** and we give you the **signature**.
 *
 * Here is the class that you must implements in this task.
 * The context of all variable must be static and the modifier public.
 * You must find the return type by yourself.
 */

public class Introduction {

    public static int variable = 0;

    public static int[] squares;

    /**
     * Function that bound variable to value
     */
    //BEGIN STRIP
    static void attribute (int value) {

    }
    //END STRIP
    //TODO attribute(int value) method

    /**
     * Function that return the addition of the two parameters
     */
    //BEGIN STRIP
    static int add (int a, int b) {
        return 0;
    }
    //END STRIP
    //TODO add(int a, int b) method

    /**
     * return true is a and b are equal
     */
    //BEGIN STRIP
    static boolean equalsIntegers (int a, int b) {
        return false;
    }
    //END STRIP
    //TODO equalsIntegers(int a, int b) method

    /**
     * Function that return the max between a and b
     * You must use a ternary operation
     */
    public static int max(int a, int b){
        //TODO the body of this function in one line
        return 0;
    }

    /**
     * Function that return the middle value.
     * If a > b > c, the function must return b.
     * If two value are equals, return -1.
     */
    //BEGIN STRIP
    static int middleValue (int a, int b, int c) {
        return 0;
    }
    //END STRIP
    //TODO middleValue(int a, int b, int c) method

    /**
     * This function must return :
     * "Good morning, sir!" if str is "Morning"
     * "Good evening, sir!" if str is "Evening"
     * "Hello, sir!" otherwise
     * Use a switch case statement
     * Your implementation must be case sensitive
     */
    //BEGIN STRIP
    static String greetings (String str) {
        return null;
    }
    //END STRIP
    //TODO greetings(String str) method

    /**
     * This function must return a new array of length 3
     * The first element of this new array is the last element of a
     * The second element is the first element of a
     * The last element is the middle element of a
     */
    //BEGIN STRIP
    static int[] lastFirstMiddle (int[] a) {
        return null;
    }
    //END STRIP
    //TODO lastFirstMiddle(int[] a)

    /**
     * This function must return the sum of the elements of array using a for loop
     */
    //BEGIN STRIP
    static int sum (int[] array) {
        return 0;
    }
    //END STRIP
    //TODO sum(int[] array) method

    /**
     * return the maximum element of array using a while loop
     */
    //TODO maxArray(int[] array) method
    //BEGIN STRIP
    static int maxArray (int[] array) {
        return 0;
    }
    //END STRIP


    /**
     * Assign to the variable square, the square of the
     * parameters.
     *
     * Let assume that the program is invoked with the following
     * line:
     *  java IntroductionExercises 0 3 4 5
     *
     * The arguments of the program are 0, 3, 4 and 5.
     * After the execution of the main, the variable squares
     * should be:
     *  squares = [0, 9, 16, 25]
     *
     * If an exception occurs when converting an argument to
     * an integer, put 0 at the corresponding index. For example
     *
     *  java IntroductionExercise 0 3.1 4 5
     *
     * would yield
     *
     *  square = [0, 0, 16, 25]
     *
     * because 3.1 can not be converted to an integer
     * */
    public static void main2(String... args){
        //TODO main body
    }

}
