package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import templates.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {
    private StudentStreamFunction streamFunction;

    // For simplicity , use them both for first name / last name
    private String[] studentNames = new String[] {
            "Jacques", "John", "Marie", "Emma", "Olivia", "Alice", "Juliette",
            "Louise", "Jules", "Victor", "Lucas", "Gabriel", "Noah", "Hugo"
    };

    // for the section field
    private Supplier<Integer> section_rng = () -> (int) (Math.random() * 4); // between 0 and 3

    // for course grade
    private Supplier<Double> grade_rng = () -> Math.random() * 20;
    private String[] courses = new String[] {"Algorithmn", "Proba & Stat", "ORG"};

    // generate random students
    private Stream<Student> generate_random_students(int number) {
        List<Student> my_list = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            // get random index / values
            int index = new Random().nextInt(studentNames.length);
            int index2 = new Random().nextInt(studentNames.length);
            int section = section_rng.get();
            Map<String, Double> grades = new HashMap<>();
            for(String course : courses) {
                grades.put(course, grade_rng.get());
            }
            my_list.add(new Student(studentNames[index], studentNames[index2], section, grades));
        }

        return my_list.stream();
    }

    @Before
    public void setUp(){
        streamFunction = new StudentFunctions();
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "FindOne() didn't work for a stream of only one student", onSuccess = true), @GradeFeedback(message = "The )\n", onFail = true, onTimeout = true)})
    public void findOne_simple_example() {
        Map<String, Double> grades = new HashMap<>();
        Map<String, Predicate<?>> conditions = new HashMap<>();
        Student student = new Student("Jacques","42", 42, grades);

        // predicate

        Predicate<String> p1 = (s) -> s.equalsIgnoreCase("jacques");
        Predicate<Integer> p2 = (i) -> i == 42;
        conditions.put("firstName", p1);
        conditions.put("section", p2);

        assertEquals(student, streamFunction.findOne(Stream.of(student), conditions));
    }



}