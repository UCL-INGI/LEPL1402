public class LinkedList {

    private Node first;
    private Node second;
    private Node tail;
    private int len;
    private int nSwaps;
    private int nPops;

    private static class Node{

        private int value;
        private Node next;

        private Node(int v, Node n){
            this.value = v;
            this.next = n;
        }

        private int getValue() {
            return value;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * Create a new "linkedlist" with the content of the given array
     * @param array
     */
    LinkedList(int[] array) {
        nPops = 0;
        nSwaps = 0;
        len = 0;
        for(int elem: array)
            add(elem);
    }

    /**
     * @return the first element of the list
     */
    public int getFirst() {
        return first.value;
    }

    /**
     * @return the second element of the list
     */
    public int getSecond() {
        return second.value;
    }

    /**
     * Swap the first and second element of the list
     */
    public void swap(){
        Node oldFirst = this.first;
        Node third= this.second.next;
        this.first = this.second;
        oldFirst.next = third;
        this.first.next = oldFirst;
        this.second = oldFirst;
        nSwaps++;
    }

    /**
     * Take the first element and put it at the end of the list
     */
    public void pop(){
        Node oldFirst = this.first;
        this.first=this.second;
        this.second=this.first.next;
        oldFirst.next=null;
        this.tail.next = oldFirst;
        this.tail = oldFirst;
        nPops++;
    }

    /**
     * Add an element to the list. You can make it public while debugging, if needed, but this will be private in
     * INGInious.
     * @param v element to add
     */
    private void add(int v){
        if(len==0){
            this.first = new Node(v, null);
            this.len++;
            this.tail = this.first;
        }else if(len==1){
            this.second = new Node(v, null);
            this.first.setNext(this.second);
            this.len++;
            this.tail = this.second;
        }else{
            Node n = new Node(v, null);
            this.tail.next = n;
            this.tail = n;
            this.len++;
        }
    }

    /**
     * @return True if the full list is sorted, False otherwise
     */
    boolean isSorted() {
        Node current = this.first;
        if(current==null) return true;
        for(int i = 0 ; i < getSize()-1 && current.next!=null; i++){
            if(current.getValue()>current.next.getValue()) return false;
            current = current.next;
        }
        return true;
    }

    public int getSize() {
        return len;
    }

    public String toString(){
        String s = "[";
        Node current = this.first;
        while(current!=null){
            s+= current.value + " ";
            current = current.next;
        }

        s += " ]";
        return s;
    }

    public int getnSwaps() { return nSwaps; }
    public int getnPops() { return nPops; }
}