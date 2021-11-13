package module6;

/**
 * In this task, we will ask you to implement the init method of this Launcher class :
 *
 * In Java, a thread needs an entry point to know where to start when we want it to run :
 * this entry point can be any object implementing the Runnable interface.
 *      - Runnable : https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html
 *
 * For this exercise, we give you a small implementation of the Runnable interface :
 * Counter, a small class increasing an int variable rnd times.
 */
public class ThreadsIntroduction {
    static class Launcher {
        /*
         * Instantiate and start each thread from "t" with its OWN Counter object,
         * then wait for all threads to finish and return the set of Counter objects
         * the threads ran on. Each thread must be named according to its index in the
         * "t" array.
         */
        public static Counter[] init(Thread[] t){
            //TODO YOUR CODE HERE
            return null;
        }
    }

    class Counter implements Runnable {

        private int count;
        private int rnd;

        public int getCount() {
            return count;
        }

        public int getRnd(){
            return rnd;
        }

        public Counter(){
            this.count = 0;
            this.rnd = (int) ( (Math.random()*100) + 1000);
        }

        @Override
        public void run() {
            for(int i=0; i< getRnd(); i++){
                count += 1;
            }
        }

    }
}
