public class Cons {
    // the item inside this list node
    int v;
    // The next element, null if nothing
    Cons next;
    // Apply function f on all the list
    Cons map(F f);
    // Constructor
    Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}

interface F {
    int apply(int v);
}

interface P {
    boolean filter(int v);
}

public class Cons {
    // the item inside this list node
    int v;
    // The next element, null if nothing
    Cons next;
    // creates a new Cons that applies function f on all elements
    Cons map(F f);
    // creates a new Cons with all elements that matches predicate p
    Cons filter(P p);
    // Constructor
    Cons(int v, Cons next) {
        this.v = v;
        this.next = next;
    }
}

import java.util.function.Predicate;
import java.util.function.Function;

public class Cons<E> {
    // the item inside this list node
    E v;
    // The next element, null if nothing
    Cons<E> next;
    // creates a new Cons that applies function f on all elements
    <R> Cons<R> map(Function<E, R> function);
    // creates a new Cons with all elements that matches predicate p
    Cons<E> filter(Predicate<E> predicate);
    // Constructor
    Cons(E v, Cons<E> next) {
        this.v = v;
        this.next = next;
    }
}

// example :
// 2 , 2, 