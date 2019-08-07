import src.Student;
import src.StudentStreamFunction;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StudentFunctions implements StudentStreamFunction {

  public Student findFirst(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
      //TODO YOUR CODE HERE
  }

  public Student[] findAll(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
      //TODO YOUR CODE HERE
  }

  public boolean exists(Stream<Student> studentsStream,
                        Map<String, Predicate<?>> conditions,
                        int n)
  {
      //TODO YOUR CODE HERE
  }

  public Student[] filterThenSort(Stream<Student> studentsStream,
                                  Map<String, Predicate<?>> conditions,
                                  Comparator<Student> comparator)
  {
      //TODO YOUR CODE HERE
  }

  //TODO YOUR CODE HERE
}
