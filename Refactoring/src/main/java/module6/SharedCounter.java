package module6;

/**
 * In this task we will ask you to use monitors for a thread-shared counter.
 * When threads are executed concurrently and they share some piece of memory, unexpected/wrong results are very likely to happen.
 *
 * For example, if you run four threads on the same counter, each thread incrementing ten thousands times the counter,
 * you would of course expect that the final value of the counter will be fourty thousands when all four threads are done...
 * But in practice, if there is no synchronization mechanism of any kind between threads,
 * you will see that the final value will certainly be different from what we expect.
 *
 * Your job for this task is thus to implement a synchronization mechanism for a counter using only java's built-in monitors.
 * We ask you to implement the three following methods:
 *         - void inc() : increment once the counter.
 *         - void dec() : decrement the counter if and only if its current value is positive.
 *                        In fact, the counter we ask you to implement must always be positive.
 *                        If a thread wants to decrement the counter but its value is 0, it has to wait.
 *         - int get() : returns the current value of the counter.
 *
 * Pay attention, you are not allowed to use Lock for this mission, only built-in monitors.
 * You might also face a deadlock problem.
 *      - monitors: https://www.artima.com/insidejvm/ed2/threadsynch.html
 *      - deadlock: https://en.wikipedia.org/wiki/Deadlock
 */
public class SharedCounter {
    private int counter = 0;

    public void inc() {
        //TODO
    }

    public void dec() {
        //TODO
    }

    public int get() {
        //TODO
        return 0;
    }
}
