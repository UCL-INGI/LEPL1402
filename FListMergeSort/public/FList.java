import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

    public abstract class FList<A> implements Iterable<A>{

        public final boolean isNotEmpty() {
            return this instanceof Cons;
        }

        public final boolean isEmpty() {
            return this instanceof Nil;
        }

        public final int length() {
            //CODE HIDDEN
        }

        public abstract A head();

        public abstract FList<A> tail();

        public static <A> FList<A> nil() {
            return (Nil<A>) Nil.INSTANCE;
        }

        public final FList<A> cons(final A a) {
            return new Cons(a, this);
        }
        public Iterator<A> iterator() {
            //CODE HIDDEN
        }

        public static final class Nil<A> extends FList<A> {
            private static  final Nil<Object> INSTANCE = new Nil();

            //CODE HIDDEN

        }

        public static final class Cons<A> extends FList<A> {

            private A head;
            private FList<A> tail;
            
            //CODE HIDDEN
            
        }

    }

