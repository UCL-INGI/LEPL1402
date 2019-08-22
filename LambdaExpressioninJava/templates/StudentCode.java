package templates;



import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class StudentCode{

    public static Function<Integer, Integer> f1 = @@studentAdd@@
    public static Function<String, Integer> mf = @@studentStringLength@@
    public static Predicate<Integer> p1 = @@studentPredicate@@
    public static Comparator<String> comp = @@studentComparator@@
    public static Comparator<String> compLength = @@studentStringLengthComp@@

}
