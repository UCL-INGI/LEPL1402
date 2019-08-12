import java.util.stream.Stream;

public interface StudentStreamFunction {

    // Find the N°2 and N°3 top students for the given course name in parameter
    public Stream<Student> findSecondAndThirdTopStudentForGivenCourse(Stream<Student> studentStream,
                                                                              String name);

    // Compute for each student in the given section their average grade result,
    // sorted by their result (ascending) as an array of array structured like that :
    // [ [ "Student FirstName1 LastName1", 7.5 ], [ "Student FirstName2 LastName2", 9.5 ]  ]
    public Object[] computeAverageForStudentInSection(Stream<Student> studentStream,
                                                           int section);

    // Give the number of students that success in all courses (> 10.0)
    public int getNumberOfSuccessfulStudents(Stream<Student> studentStream);

    // Find the student that is the last one in the lexicographic order
    // ( You must first compare students on their lastNames then on their firstNames )
    public Student findLastInLexicographicOrder(Stream<Student> studentStream);


    // Give the full sum of the grade obtained by all students
    public double getFullSum(Stream<Student> studentStream);

}
