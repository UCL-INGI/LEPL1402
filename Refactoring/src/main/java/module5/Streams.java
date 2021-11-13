package module5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Given the Student class, you are asked to write the StudentFunctions class (which implements the StudentStreamFunction interface):
 */
public class Streams {

    static class StudentFunctions implements StudentStreamFunction {

        public Stream<Student> findSecondAndThirdTopStudentForGivenCourse(Stream<Student> studentStream, String name){
            //TODO YOUR CODE HERE
            return null;
        }

        public Object[] computeAverageForStudentInSection(Stream<Student> studentStream, int section){
            //TODO YOUR CODE HERE
            return null;
        }

        public int getNumberOfSuccessfulStudents(Stream<Student> studentStream){
            //TODO YOUR CODE HERE
            return 0;
        }

        public Student findLastInLexicographicOrder(Stream<Student> studentStream){
            //TODO YOUR CODE HERE
            return null;
        }

        public double getFullSum(Stream<Student> studentStream){
            //TODO YOUR CODE HERE
            return 0.0;
        }
    }

    interface StudentStreamFunction {

        // Finds the N°2 and N°3 top students for the course name given as parameter
        Stream<Student> findSecondAndThirdTopStudentForGivenCourse(Stream<Student> studentStream, String name);

        // Computes, for each student in the given section, their average grade result,
        // in the form of an array of arrays structured as follows:
        // [ ["Student FirstName1 LastName1", 7.5], ["Student FirstName2 LastName2", 9.5] ]
        Object[] computeAverageForStudentInSection(Stream<Student> studentStream, int section);

        // Gives the number of students that passed all courses (all grades > 10.0)
        int getNumberOfSuccessfulStudents(Stream<Student> studentStream);

        // Finds the last student in lexicographic order
        // (First compare students on their lastNames and then on their firstNames)
        Student findLastInLexicographicOrder(Stream<Student> studentStream);

        // Gives the total sum of all grades obtained by all students
        double getFullSum(Stream<Student> studentStream);

    }

    static class Student implements Comparable<Student> {
        private String firstName;
        private String lastName;
        private int section;
        private Map<String, Double> coursesResults = new HashMap<>();

        public Student(String firstName, String lastName, int section, Map<String, Double> coursesResults) {
            this.coursesResults = coursesResults;
            this.firstName = firstName;
            this.lastName = lastName;
            this.section = section;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getSection() {
            return section;
        }

        public Map<String, Double> getCoursesResults() {
            return coursesResults;
        }

        @Override
        public int compareTo(Student student) {
            // Advanced sorting
            Comparator<Student> myComparator = Comparator
                    .comparing(Student::getSection)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getLastName)
                    .thenComparing(
                            (s) -> s.getCoursesResults().values().stream().reduce(0.0, Double::sum),
                            Comparator.reverseOrder()
                    );
            return myComparator.compare(this, student);
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == null || !(obj instanceof Student)) {
                return false;
            }

            return this.compareTo((Student) obj) == 0;
        }
    }
}
