package templates;

import src.F;
import src.P;

public class Cons {
    // the item inside this list node
    public int v;
    // The next element, null if nothing
    public Cons next;
    // Constructor
    public Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }

    public Cons map(F f) {
      @@map@@
    }

    public Cons filter(P p){
      @@filter@@
    } 
}
