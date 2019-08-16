public class PlayWithStreams {

    /**
     * Generate an infinite stream that starts at from
     * and increments by one at each element.
     */
    public static IStream<Integer> fromInteger(int from){
        //TODO
    }

    /**
     * Generate an infinite stream of Boolean.
     * The current element is false if a mod b == 1
     * or true if a mod b == 0.
     * The next element of the stream is generate from a+2 and b+1.
     */
    public static IStream<Boolean> booleanFromModulo(int a, int b){
        //TODO
    }

    /**
     * Generate a finite stream of Integer that starts at from
     * and ends at to.
     */
    public static IStream<Integer> finiteStream(int from, int to){
        //TODO
    }

    /**
     * Generate an infinite stream of prime numbers.
     * Use the helper function sieve in your implementation
     */
    public static IStream<Integer> primeStream(){
        //TODO
    }

    /**
     * Use recursively the Sieve of Eratosthenes algorithm to generate prime numbers.
     * https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
     */
    public static IStream<Integer> sieve(IStream<Integer> s){
        //TODO
    }

    /**
     * return an infinite stream with all elements of s mod n
     */
    public static IStream<Integer> infiniteModulo(IStream<Integer> s, int n){
        //TODO
    }

    /**
     * Generate an infinite stream of Fibonacci numbers.
     * You can use an additional function for your implementation.
     */
    public static IStream<Integer> fibonacci(){
        //TODO
    }

    //TODO

}
