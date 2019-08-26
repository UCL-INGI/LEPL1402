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
    private void doPut2(Integer x){
        @@student_doPut@@
    }
    
    public void doPut(Integer x){ //MIEN
        this.data[putPointer] = x;
        putPointer = (putPointer + 1) % this.data.length;
        ++size;
        notifyAll();
        //TODO
    }

    /*
     * Mechanics of getting the first element of the buffer
     */
    private Integer doTake2(){
        @@student_doTake@@
    }
    
    public Integer doTake(){
        Integer x = this.data[takePointer];
        this.data[takePointer] = null;
        takePointer = (takePointer + 1) % this.data.length;
        --size;
        notifyAll();
        return x;
        //TODO
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
    public synchronized void put2(Integer x) throws InterruptedException{
        @@student_put@@
    }
    
    public synchronized void put(Integer x) throws InterruptedException{
        while(isFull()) wait();
        doPut2(x);
    }

    /*
     * Get the first element of the buffer
     * if the buffer is empty, the thread waits until an element arrives
     */
    public synchronized Integer take2() throws InterruptedException{
        @@student_take@@
    }
    
    public synchronized Integer take() throws InterruptedException{
        while(isEmpty()) wait();
        return doTake2();
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

}
