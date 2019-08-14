import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class FList<A> implements Iterable<A> {

    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    public final int length() {
        // TODO
    }

    public abstract A head();

    public abstract FList<A> tail();

    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }

    public final FList<A> cons(final A a) {
        return new Cons(a, this);
    }

    public final <B> FList<B> map(Function<A,B> f) {
        // TODO
    }

    public final FList<A> filter(Predicate<A> f) {
        // TODO
    }


    public Iterator<A> iterator() {
        return new Iterator<A>() {
            // complete this class


            public boolean hasNext() {
              // TODO
            }

            public A next() {
              // TODO
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();
        // TODO
    }

    private static final class Cons<A> extends FList<A> {
        // TODO
    }


}