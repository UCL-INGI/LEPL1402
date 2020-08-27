import java.util.function.Predicate;
import java.util.function.Function;

public class Cons<E> {
    // The item inside this list node
    public E v;
    // The next element, null if there is none
    public Cons<E> next;

    // Returns a new Cons by applying Function function on all elements
    public <R> Cons <R> map(Function <E,R> function) {
        //TODO
    }
    // Returns a new Cons containing all elements that match Predicate predicate
    public Cons <E> filter(Predicate <E> predicate) {
        //TODO
    }

    // Constructor
    public Cons(E v, Cons<E> next) {
        this.v = v;
        this.next = next;
    }
}
