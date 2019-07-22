import java.util.Comparator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StudentStreamFunction {

    // In order to test efficiently your code, we must a Map<String, Predicate<?>> structured like that :
    //      Key : String that is one of the fields of Student ( "firstName", "lastName", "section", "courses_results")
    //    Value : Predicate bounded to the type of the field to perform a check condition


    // Returns a student if any match the given conditions
    // if it is not possible, you must return null
    public Student findOne(Stream<Student> studentsStream,
                           Map<String, Predicate<?>> conditions);

    // Returns a array of student(s) that match the given conditions
    public Student[] findAll(Stream<Student> studentsStream,
                             Map<String, Predicate<?>> conditions);

    // Return true if we could find n student(s) that match the given condition
    public boolean exists(Stream<Student> studentsStream,
                          Map<String, Predicate<?>> conditions,
                          int n);

    // Returns a ordered array of student(s) that match the given conditions,
    // depending of the given comparator
    public Student[] filterThenSort(Stream<Student> studentsStream,
                                    Map<String, Predicate<?>> conditions,
                                    Comparator<Student> comparator);


    // Returns the result of a mapReduce operation
    // For clarity, the identify and accumulator parameters must be used in your reduce call
    public Double mapReduce(Stream<Student> studentsStream,
                            Function<Student, Double> mapFunction,
                            Double identity,
                            BinaryOperator<Double> accumulator);

}