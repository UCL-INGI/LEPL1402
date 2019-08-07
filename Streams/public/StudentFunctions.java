import src.Student;
import src.StudentStreamFunction;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StudentFunctions implements StudentStreamFunction {

  public Stream<Student> find_second_and_third_top_student_for_given_course(Stream<Student> studentStream, String name){
    //TODO YOUR CODE HERE
  }

  public Object[] compute_average_for_student_in_section(Stream<Student> studentStream, int section){
    //TODO YOUR CODE HERE
  }

  public int get_number_of_successful_students(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }

  public Student find_last_in_lexicographic_order(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }

  public double get_full_sum(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }
}
