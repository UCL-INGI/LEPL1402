import java.util.function.Predicate;
import java.util.function.Function;
import java.util.ArrayList;

abstract class GenericList<T> {
    public abstract <R> GenericList<R> map(Function<T, R> function);
    public abstract GenericList<T> filter(Predicate<T> predicate);
    public ArrayList<T> elements;
    public GenericList(ArrayList<T> elements) {
        this.elements = elements;
    }
}