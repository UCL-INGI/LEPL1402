import java.util.function.Predicate;
import java.util.function.Function;

public class Cons<E> {
    // the item inside this list node
    E v;
    // The next element, null if nothing
    Cons<E> next;
    // creates a new Cons that applies function f on all elements
    <R> Cons<R> map(Function<E, R> function) {
        // TODO by student
    }
    // creates a new Cons with all elements that matches predicate p
    Cons<E> filter(Predicate<E> predicate) {
        // TODO by student
    }
    // Constructor
    Cons(E v, Cons<E> next) {
        this.v = v;
        this.next = next;
    }
}