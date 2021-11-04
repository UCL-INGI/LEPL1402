package module2;

/**
 * In this task, we will ask you to implement two different versions of the Fibonacci sequence number: a recursive one and an iterative one.
 * The objective of this exercise is to make you aware of what a call stack is and what problem could occur.
 * We strongly suggest you to test your code with various input : what will happen if you test your recursive implementation with a very large index.
 * Why ? Will the same thing happen with your iterative implementation ? Why ?
 *
 * Reminder : Fibonacci's sequence is computed as follows:
 *      f(n) = 0                if n=0
 *             1                if n=1
 *             f(n-1)+f(n-2)    if n>1
 */
public class Fibonacci {

    //BEGIN STRIP
    private static int nCalls = 0;

    public static int getCount(){
        return nCalls;
    }

    public static void resetCount(){
        nCalls = 0;
    }
    //END STRIP


    public static int fiboRecursive(int index){
        //BEGIN STRIP
        nCalls++;
        //END STRIP
        return 0;
    }


    public static int fiboIterative(int index){
        //BEGIN STRIP
        nCalls++;
        //END STRIP
        return 0;
    }

}
