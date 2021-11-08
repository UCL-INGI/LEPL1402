package module5;

public class MapFilterCons {
    /**
     * In this task, you have to implement the map / filter functions of the following class:
     */
    static class Cons {
        // The item inside this list node
        public int v;
        // The next element, null if there is none
        public Cons next;

        // Returns a new Cons by applying function f on all elements
        public Cons map(F f) {
            // TODO by student
            return null;
        }

        // Returns a new Cons containing all elements that match predicate p
        public Cons filter(P p) {
            // TODO by student
            return null;
        }

        // Constructor
        public Cons(int v, Cons next) {
            this.v = v;
            this.next = next;
        }
    }

    /**
     * These are the interfaces for the objects used as parameters for filter and map:
     */
    interface F {
        int apply(int v);
    }

    interface P {
        boolean filter(int v);
    }
}
