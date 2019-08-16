package templates;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IStream<T> {

    /**
     *
     */
    T head();

    /**
     *
     */
    IStream<T> tail();

    /**
     *
     */
    boolean isEmpty();

    /**
     * Create a finite stream according to predicate.
     */
    default IStream<T> createWhile(Predicate<? super T> predicate){
        @@student_createWhile@@
    }

    default IStream<T> helper(Predicate<? super T> predicate){
        //TODO
        if(this.isEmpty()) {
            return this;
        }
        if(predicate.test(this.head())){
            return new Cons<>(this.head(), () -> this.tail().createWhile(predicate));
        }
        return new Empty<>();
    }

    /**
     * Apply function on each element of the stream.
     * Can be apply on infinite and finite stream.
     */
    default IStream<T> map(Function<? super T, T> function){
        @@student_map@@
    }

    /**
     * Filter the steam according to predicate.
     * Can be apply on infinite and finite stream.
     */
    default IStream<T> filter(Predicate<? super T> predicate){
        @@student_filter@@
    }
}
