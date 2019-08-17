import java.util.function.Function;

public abstract class FTree<A> {

    public final boolean isNotEmpty() {
        return this instanceof Node;
    }

    public final boolean isEmpty() {
        return this instanceof Leaf;
    }

    public final int depth() {
        //TODO by student
        return 0;
    }

    public abstract A value();
    public abstract FTree<A> left();
    public abstract FTree<A> right();

    public final <B> FTree<B> map(Function<A,B> f) {
        //TODO by student
    }

}