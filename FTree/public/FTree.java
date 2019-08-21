import java.util.function.Function;

public abstract class FTree<A> {

    public final int depth() {
        //TODO by student
    }

    public abstract A value();
    public abstract FTree<A> left();
    public abstract FTree<A> right();

    public final <B> FTree<B> map(Function<A,B> f) {
        //TODO by student
    }

}
