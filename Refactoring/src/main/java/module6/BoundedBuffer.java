package module6;

import java.util.List;
import java.util.function.Supplier;

/**
 * A bounded buffer is a way for multiple producers and consumers to synchronize.
 * Indeed multiple producers are going to fill the buffer while consumers are going to clear out this buffer.
 *
 * You have to implement the class BoundedBuffer.
 * In a bounded buffer, the element window grows to the right, shrinks to the left and slides to the right on the buffer.
 * Here is a little example about the fonctioning of a bounded buffer :
 *
 *     .. image:: /course/LEPL1402/BoundedBuffer/BoundedBuffer.png
 *        :scale: 100%
 *        :align: center
 *        :height: 461px
 *        :width: 801px
 *
 * Before submitting don't hesitate to create a producer and a consumer and to test your code.
 */
public class BoundedBuffer {
    private Integer[] data;
    private int putPointer, takePointer, size;

    public BoundedBuffer(int capacity){
        this.data = new Integer[capacity];
        this.putPointer = 0;
        this.takePointer = 0;
        this.size = 0;
    }

    /*
     * Mechanics of putting x on the buffer.
     * x is added at the end of the buffer.
     */
    private void doPut(Integer x){
        //TODO
    }

    /*
     * Mechanics of getting the first element of the buffer
     */
    private Integer doTake(){
        //TODO
        return null;
    }

    public boolean isFull(){
        return this.data.length == this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /*
     * put x on the buffer if the buffer is not full
     * if the buffer is full, the thread waits until a place is free
     */
    public synchronized void put(Integer x) throws InterruptedException{
        //TODO
    }

    /*
     * Get the first element of the buffer
     * if the buffer is empty, the thread waits until an element arrives
     */
    public synchronized Integer take() throws InterruptedException{
        //TODO
        return null;
    }

    /*
     * put x on the buffer if the buffer is not full
     * if the buffer is full, the thread waits ms milliseconds until a place is free
     * if the delay is exceeded, don't put x on the buffer
     * return true if x was added on the buffer, or false
     * return false if an exception occurs
     */
    public synchronized boolean offer(Integer x, long ms){
        //TODO
        return false;
    }

    /*
     * get the first element of the buffer
     * if the buffer is empty, the thread waits ms milliseconds until an element arrives
     * if the delay is exceeded or an exception occurs return null
     */
    public synchronized Integer poll(long ms){
        //TODO
        return null;
    }

    //BEGIN STRIP
    public Integer[] getData(){
        return this.data;
    }
    public int getPutPointer() {
        return putPointer;
    }
    public int getTakePointer() {
        return takePointer;
    }
    public int getSize() {
        return size;
    }

    public synchronized void put2(Integer x) throws InterruptedException{
        while(isFull()) wait();
        doPut2(x);
    }

    public synchronized Integer take2() throws InterruptedException{
        while(isEmpty()) wait();
        return doTake2();
    }

    public Integer doTake2(){
        Integer x = this.data[takePointer];
        this.data[takePointer] = null;
        takePointer = (takePointer + 1) % this.data.length;
        --size;
        notifyAll();
        return x;
    }

    public void doPut2(Integer x){ //MIEN
        this.data[putPointer] = x;
        putPointer = (putPointer + 1) % this.data.length;
        ++size;
        notifyAll();
    }
    //END STRIP
}
