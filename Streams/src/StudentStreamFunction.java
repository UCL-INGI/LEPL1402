package src;

import java.util.stream.Stream;

public interface StudentStreamFunction {

    // Finds the N°2 and N°3 top students for the course name given as parameter
    public Stream<Student> find_second_and_third_top_student_for_given_course(Stream<Student> studentStream,
                                                                              String name);

    // Computes, for each student in the given section, their average grade result,
    // in the form of an array of arrays structured as follows:
    // [ ["Student FirstName1 LastName1", 7.5], ["Student FirstName2 LastName2", 9.5] ]
    public Object[] compute_average_for_student_in_section(Stream<Student> studentStream,
                                                           int section);

    // Gives the number of students that passed all courses (all grades > 10.0)
    public int get_number_of_successful_students(Stream<Student> studentStream);

    // Finds the last student in lexicographic order
    // (First compare students on their lastNames and then on their firstNames)
    public Student find_last_in_lexicographic_order(Stream<Student> studentStream);


    // Gives the full sum of the grade obtained by all students
    public double get_full_sum(Stream<Student> studentStream);

}