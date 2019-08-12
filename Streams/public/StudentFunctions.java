import src.Student;
import src.StudentStreamFunction;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StudentFunctions implements StudentStreamFunction {

  public Stream<Student> findSecondAndThirdTopStudentForGivenCourse(Stream<Student> studentStream, String name){
    //TODO YOUR CODE HERE
  }

  public Object[] computeAverageForStudentInSection(Stream<Student> studentStream, int section){
    //TODO YOUR CODE HERE
  }

  public int getNumberOfSuccessfulStudents(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }

  public Student findLastInLexicographicOrder(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }

  public double getFullSum(Stream<Student> studentStream){
    //TODO YOUR CODE HERE
  }
}
