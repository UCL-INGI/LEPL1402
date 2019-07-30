package src;


public class SharedCounter {

    private int counter = 0;

    public synchronized void inc() {
        counter++;
        notify();
    }

    public synchronized void dec() {
        while(counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        counter--;
    }

    public synchronized int get() { return counter; }


}