package templates;

import src.F;
import src.P;

public class Cons {
    // The item inside this list node
    public int v;
    // The next element, null if there is none
    public Cons next;

    // Returns a new Cons by applying function f on all elements
    public Cons map(F f) {
        @@map@@
    }
    // Returns a new Cons containing all elements that match predicate p
    public Cons filter(P p){
        @@filter@@
    }

    // Constructor
    public Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}
