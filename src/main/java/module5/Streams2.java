package module5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * In order to understand why the Stream class could be useful for genericity,
 * we give you this small exercise where you'll have to work on both casting and streams.
 *
 * Given the Student class, you are asked to write the StudentFunctions class (which implements the StudentStreamFunction interface):
 */
public class Streams2 {

    static class StudentFunctions implements StudentStreamFunction {

        public Student findFirst(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
            //TODO YOUR CODE HERE
            return null;
        }

        public Student[] findAll(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
            //TODO YOUR CODE HERE
            return null;
        }

        public boolean exists(Stream<Student> studentsStream,
                              Map<String, Predicate<?>> conditions,
                              int n)
        {
            //TODO YOUR CODE HERE
            return false;
        }

        public Student[] filterThenSort(Stream<Student> studentsStream,
                                        Map<String, Predicate<?>> conditions,
                                        Comparator<Student> comparator)
        {
            //TODO YOUR CODE HERE
            return null;
        }

        //TODO YOUR CODE HERE
    }

    interface StudentStreamFunction {

        // In order to test your code efficiently, input conditions take the
        // form of a Map<String, Predicate<?>> object structured as follows:
        //    Key   : String corresponding to one of the fields of Student objects
        //            ("firstName", "lastName", "section", "courses_results")
        //    Value : Predicate bound to the type of the field on which the condition applies
        //
        // For example:
        //    Key   : "firstName"
        //    Value : Predicate<String>


        // Returns a student that matches the given conditions
        // If there is none, returns null
        public Student findFirst(Stream<Student> studentsStream,
                                 Map<String, Predicate<?>> conditions);

        // Returns an array of student(s) that match the given conditions
        public Student[] findAll(Stream<Student> studentsStream,
                                 Map<String, Predicate<?>> conditions);

        // Returns true if there are at least n student(s) that match the given conditions
        public boolean exists(Stream<Student> studentsStream,
                              Map<String, Predicate<?>> conditions,
                              int n);

        // Returns an array of student(s) that match the given conditions,
        // ordered according to the given comparator
        public Student[] filterThenSort(Stream<Student> studentsStream,
                                        Map<String, Predicate<?>> conditions,
                                        Comparator<Student> comparator);

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
