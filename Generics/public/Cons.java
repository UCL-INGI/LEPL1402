public class Cons {
    // the item inside this list node
    public int v;
    // The next element, null if nothing
    public Cons next;
    // creates a new Cons that applies function f on all elements
    public Cons map(F f) {
        // TODO by student
    }
    // creates a new Cons with all elements that matches predicate p
    public Cons filter(P p) {
        // TODO by student
    }
    // Constructor
    public Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}