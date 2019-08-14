package src;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class CorrectFList<A> implements Iterable<A>{

    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    public final int length() {
        if(this.isEmpty()){
            return 0;
        }
        return 1 + tail().length();
    }

    public abstract A head();

    public abstract CorrectFList<A> tail();

    public static <A> CorrectFList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }

    public final CorrectFList<A> cons(final A a) {
        return new Cons(a, this);
    }

    public final <B> CorrectFList<B> map(Function<A,B> f) {

        if(length()>1){
            return new Cons(f.apply(head()), tail().map(f) );
        }else{
            return new Cons(f.apply(head()), (Nil<A>) Nil.INSTANCE );
        }
    }

    public final CorrectFList<A> filter(Predicate<A> f) {
        if(f.test(head())) {
            if (length() > 1) {
                return new Cons(head(), tail().filter(f));
            }else{
                return new Cons(head(), (Nil<A>) Nil.INSTANCE);
            }
        }else{
            if(length()>1){
                return tail().filter(f);
            }else{
                return (Nil<A>) Nil.INSTANCE;
            }
        }
    }
    public Iterator<A> iterator() {
        return new Iterator<A>() {

            private int sizeAtStart= length();
            private CorrectFList<A> current = CorrectFList.this;

            public boolean hasNext() throws ConcurrentModificationException {
                if(sizeAtStart != length()) {
                    System.out.println(sizeAtStart + "    " + length());
                    throw new ConcurrentModificationException();
                }

                return current.isNotEmpty();
            }

            public A next() {
                if(hasNext()){
                    A a = current.head();
                    current = current.tail();
                    return a;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static final class Nil<A> extends CorrectFList<A> {
        private static  final Nil<Object> INSTANCE = new Nil();

        @Override
        public A head() {
            return null;
        }

        @Override
        public CorrectFList<A> tail() {
            return null;
        }

    }

    public static final class Cons<A> extends CorrectFList<A> {

        private A head;
        private CorrectFList<A> tail;


        private Cons(A a, CorrectFList<A> fList){
            this.head = a;
            this.tail = fList;

        }
        @Override
        public A head() {
            return this.head;
        }

        @Override
        public CorrectFList<A> tail() {
            return this.tail;
        }
    }

}
