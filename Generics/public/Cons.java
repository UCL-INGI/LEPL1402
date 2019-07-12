public class Cons {
    // the item inside this list node
    int v;
    // The next element, null if nothing
    Cons next;
    // creates a new Cons that applies function f on all elements
    Cons map(F f) {
        // TODO by student
    }
    // creates a new Cons with all elements that matches predicate p
    Cons filter(P p) {
        // TODO by student
    }
    // Constructor
    Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}