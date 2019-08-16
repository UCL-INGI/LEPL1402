package templates;

import java.util.NoSuchElementException;

public class Empty<T> implements IStream<T> {
    @Override
    public T head() {
        throw new NoSuchElementException();
    }

    @Override
    public IStream<T> tail() {
        throw new NoSuchElementException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
