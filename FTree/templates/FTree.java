package templates;

import java.util.function.Function;

public abstract class FTree<A> {

    public static int counter=0;

    public static void resetCounter(){
        counter=0;

    }

    public final int depth() {
        counter++;
        @@studentDepth@@
    }

    public abstract A value();
    public abstract FTree<A> left();
    public abstract FTree<A> right();

    public final <B> FTree<B> map(Function<A,B> f) {
        counter++;

        @@studentMap@@
    }

}
