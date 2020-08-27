public class Cons {
    // The item inside this list node
    public int v;
    // The next element, null if there is none
    public Cons next;

    // Returns a new Cons by applying function f on all elements
    public Cons map(F f) {
        // TODO by student
    }
    // Returns a new Cons containing all elements that match predicate p
    public Cons filter(P p) {
        // TODO by student
    }

    // Constructor
    public Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}