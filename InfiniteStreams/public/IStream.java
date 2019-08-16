import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IStream<T> {

    /**
     * return the head of the stream
     */
    T head();

    /**
     * return the tail of the stream
     */
    IStream<T> tail();

    /**
     * return true if the stream is empty
     */
    boolean isEmpty();

    /**
     * Create a finite IStream respecting the predicate.
     * The IStream is composed of Cons objects and the end of this stream is an Empty object.
     */
    default IStream<T> createWhile(Predicate<? super T> predicate){
        //TODO
    }

    /**
     * Apply function on each element of the stream.
     * Can be apply on infinite and finite stream.
     */
    default IStream<T> map(Function<? super T, T> function){
        //TODO
    }

    /**
     * Filter the steam according to predicate.
     * Can be apply on infinite and finite stream.
     */
    default IStream<T> filter(Predicate<? super T> predicate){
        //TODO
    }
}
