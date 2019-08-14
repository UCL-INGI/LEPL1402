package templates;


import java.util.ConcurrentModificationException;
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
        @@student_length@@
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
        @@student_map@@
    }

    public final FList<A> filter(Predicate<A> f) {
        @@student_filter@@
    }


    public Iterator<A> iterator() {
        return new Iterator<A>() {

            @@student_iterator@@

        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();
        @@student_nil@@
    }

    private static final class Cons<A> extends FList<A> {
        @@student_cons@@
    }


}
