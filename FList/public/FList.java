public abstract class FList<A> implements Iterable<A> {

    // creates an empty list
    public static <A> FList<A> nil();

    // prepend a to the list and return the new list
    public final FList<A> cons(final A a);

    public final boolean isNotEmpty();

    public final boolean isEmpty();

    public final int length();

    // return the head element of the list
    public abstract A head();

    // return the tail of the list
    public abstract FList<A> tail();

    // return a list on which each element has been applied function f
    public final <B> FList<B> map(Function<A,B> f);

    // return a list on which only the elements that satisfies predicate are kept
    public final FList<A> filter(Predicate<A> f);

    // return an iterator on the element of the list
    public Iterator<A> iterator();

}