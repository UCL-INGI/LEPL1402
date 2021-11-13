package module5;

import java.util.function.Function;
import java.util.function.Predicate;

public class MapFilterConsWGenerics {
    /**
     * This task is very similar to the previous one, the difference being that you're now asked to implement generic
     * methods and to use Java's built-in Function and Predicate objects.
     * Your code should therefore not be vastly different of the code you've previously written.
     * To help you understand how Predicate and Function works, check the documentation.
     *      - Documentation: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
     *
     * In this task, you have to implement the generic map / filter functions of the following class:
     */
    static class Cons<E> {
        // The item inside this list node
        public E v;
        // The next element, null if there is none
        public Cons<E> next;

        // Returns a new Cons by applying Function function on all elements
        public <R> Cons<R> map(Function<E,R> function) {
            //TODO
            return null;
        }

        // Returns a new Cons containing all elements that match Predicate predicate
        public Cons<E> filter(Predicate<E> predicate) {
            //TODO
            return null;
        }

        // Constructor
        public Cons(E v, Cons<E> next) {
            this.v = v;
            this.next = next;
        }
    }
}
