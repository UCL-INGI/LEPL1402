package templates;

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
        @@student_doPut@@
    }

    /*
     * Mechanics of getting the first element of the buffer
     */
    private Integer doTake(){
        @@student_doTake@@
    }

    private boolean isFull(){
        return this.data.length == this.size;
    }

    private boolean isEmpty(){
        return size == 0;
    }

    /*
     * put x on the buffer if the buffer is not full
     * if the buffer is full, the thread waits until a place is free
     */
    public synchronized void put(Integer x) throws InterruptedException{
        @@student_put@@
    }

    /*
     * Get the first element of the buffer
     * if the buffer is empty, the thread waits until an element arrives
     */
    public synchronized Integer take() throws InterruptedException{
        @@student_take@@
    }

    /*
     * put x on the buffer if the buffer is not full
     * if the buffer is full, the thread waits ms milliseconds until a place is free
     * if the delay is exceeded, don't put x on the buffer
     * return true if x was added on the buffer, or false
     */
    public synchronized boolean offer(Integer x, long ms){
        @@student_offer@@
    }

    /*
     * get the first element of the buffer
     * if the buffer is empty, the thread waits ms milliseconds until an element arrives
     * if the delay is exceeded return null
     */
    public synchronized Integer poll(long ms){
        @@student_poll@@
    }



}
