package module5;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * In this exercise you will learn to use basic lambda expressions.
 * Since you are going to use them later, they are worth a small introduction exercise.
 *
 * Do note that, as we're working with lambdas, we've decided to block the ``return`` statement.
 */
public class LambdaExpressionInJava {
    /**
     * Write a lambda expression here that takes an integer and return the sum of the integer and two.
     * Don't forget the semicolon!
     */
    public static Function<Integer, Integer> f1 = null; //Your lambda

    /**
     * Write a lambda expression here that takes a String and returns its length.
     * Don't forget the semicolon!
     */
    public static Function<String, Integer> mf = null; //Your lambda

    /**
     * Write a lambda expression here that takes an integer and returns true if it's even and false if it's odd.
     * Don't forget the semicolon!
     */
    public static Predicate<Integer> p1 = null; //Your lambda

    /**
     * Write a lambda expression here that takes two Strings and compares them using compareTo.
     * Don't forget the semicolon!
     */
    public static Comparator<String> comp = null; //Your lambda

    /**
     * Write a lambda expression here that takes two Strings and compares them using their length.
     * Your comparator should return the difference between the first and the second String's length.
     * Don't forget the semicolon!
     */
    public static Comparator<String> compLength = null; //Your lambda
}
