import java.util.function.Predicate;
import java.util.function.Function;

public class Cons<E> {
    // the item inside this list node
    public E v;
    // The next element, null if nothing
    public Cons<E> next;
    // Constructor
    public Cons(E v, Cons<E> next) {
        this.v = v;
        this.next = next;
    }

    public <R> Cons <R> map(Function <E,R> function) {
      //TODO
    }

    public Cons <E> filter(Predicate <E> predicate) {
      //TODO
    } 
}
