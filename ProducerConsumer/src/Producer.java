package src;

public class Producer implements Runnable {

    private final int nb_tests;
    private LockQueue q ;

    public Producer(LockQueue q) {
        this.q = q;
        nb_tests = 1000;
    }

    public Producer(LockQueue q, int nb_tests) {
        this.q = q;
        this.nb_tests = nb_tests;
    }


    public void run() {
        for (int i=0; i<nb_tests; i++) {
            q.enqueue(i);
        }
    }
}