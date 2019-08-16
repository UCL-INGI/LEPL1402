package templates;

import java.util.function.Supplier;

public class Cons<T> implements IStream<T> {

    @@student_variables_constructor@@

    @Override
    public T head() {
        return this.head;
    }

    @Override
    public IStream<T> tail() {
        return this.tail.get();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
