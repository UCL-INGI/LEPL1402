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
import java.util.stream.Collectors;
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
    private Supplier<Integer> section_rng = () -> (int) (Math.random() * 10) + 1; // between 1 and 10

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

    private Map<String, Predicate<?>> generateConditions(int limit) {

        // some predicates inside
        Map<String, Predicate<?>> conditions = new HashMap<>();
        String randomName = studentNames[new Random().nextInt(studentNames.length)];
        Predicate<String> p1 = (s) -> s.equalsIgnoreCase(randomName);

        int randomInt = section_rng.get();
        Predicate<Integer> p2 = (i) -> i == randomInt;
        String courseRandom = courses[new Random().nextInt(courses.length)];
        Double d = grade_rng.get();

        Predicate<Map<String, Double>> p3 = (m) -> m.get(courseRandom) >= d;
        conditions.put("firstName", p1);
        conditions.put("lastName", p1);
        conditions.put("section", p2);
        conditions.put("courses_results", p3);

        return conditions
                .entrySet()
                .stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    @Before
    public void setUp(){
        streamFunction = new StudentFunctions();
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true), @GradeFeedback(message = "FindOne() didn't work for a stream of only one student\n", onFail = true, onTimeout = true)})
    public void findOne_simple() {
        Map<String, Double> grades = new HashMap<>();
        Map<String, Predicate<?>> conditions = new HashMap<>();

        Student student = new Student("Jacques","42", 42, grades);
        Student student1 = new Student("Jacques", "42", 12, grades);

        Predicate<String> p1 = (s) -> s.equalsIgnoreCase("jacques");
        Predicate<Integer> p2 = (i) -> i == 42;
        conditions.put("firstName", p1);
        conditions.put("section", p2);

        assertEquals(student, streamFunction.findFirst(Stream.of(student), conditions));
        assertNull(streamFunction.findFirst(Stream.of(student1), conditions));
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true), @GradeFeedback(message = "FindOne() didn't work for advanced data sample\n", onFail = true, onTimeout = true)})
    public void findOne_advanced() {
        // random students
        Student[] random_students = generate_random_students(section_rng.get()).toArray(Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Student expected = handleCondition(Arrays.stream(random_students), testedConditions).findFirst().orElse(null);
            Student result = streamFunction.findFirst(Arrays.stream(random_students), testedConditions);
            assertEquals(expected, result);
        }

    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true), @GradeFeedback(message = "FindAll() didn't work for advanced data sample\n", onFail = true, onTimeout = true)})
    public void findAll_advanced() {
        // random students
        Student[] random_students = generate_random_students(section_rng.get()).toArray(Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Student[] expected = handleCondition(Arrays.stream(random_students), testedConditions).toArray(Student[]::new);
            Student[] result = streamFunction.findAll(Arrays.stream(random_students), testedConditions);
            assertTrue(Arrays.deepEquals(expected, result));
        }

    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true), @GradeFeedback(message = "Exists() didn't work for advanced data sample\n", onFail = true, onTimeout = true)})
    public void exists_advanced() {
        // random students
        Student[] random_students = generate_random_students(section_rng.get()).toArray(Student[]::new);

        for(int i = 0; i < 100; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);
            int atleast = new Random().nextInt(random_students.length);

            boolean expected = handleCondition(Arrays.stream(random_students), testedConditions).count() >= atleast;
            boolean result = streamFunction.exists(Arrays.stream(random_students), testedConditions, atleast);
            assertEquals(expected, result);
        }

    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true), @GradeFeedback(message = "filterThenSort() didn't work for advanced data sample\n", onFail = true, onTimeout = true)})
    public void filterThenSort_advanced() {
        // random students
        Student[] random_students = generate_random_students(section_rng.get()).toArray(Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Comparator<Student> basic = new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.compareTo(o2);
                }
            };

            //
            Comparator<Student> other = new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1
                            .getCourses_results()
                            .values()
                            .stream()
                            .reduce(0.0, (a, b) -> a + b)
                            .compareTo(
                                    o2.getCourses_results().values().stream().reduce(0.0, (a, b) -> a + b)
                            );
                }
            };

            // with the basic
            Student[] expected = handleCondition(Arrays.stream(random_students), testedConditions).sorted(basic).toArray(Student[]::new);
            Student[] result = streamFunction.filterThenSort(Arrays.stream(random_students), testedConditions, basic);
            assertTrue(Arrays.deepEquals(expected, result));

            // with custom comparator
            expected = handleCondition(Arrays.stream(random_students), testedConditions).sorted(other.reversed()).toArray(Student[]::new);
            result = streamFunction.filterThenSort(Arrays.stream(random_students), testedConditions, other.reversed());
            assertTrue(Arrays.deepEquals(expected, result));
        }

    }

    private Stream<Student> handleCondition(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions) {
        for (Map.Entry<String, Predicate<?>> condition : conditions.entrySet()) {
            switch (condition.getKey()) {
                case "firstName":
                    Predicate<String> s1 = (Predicate<String>) condition.getValue();
                    studentsStream = studentsStream.filter( student -> s1.test(student.getFirstName()) );
                    break;
                case "lastName":
                    Predicate<String> s2 = (Predicate<String>) condition.getValue();
                    studentsStream = studentsStream.filter( student -> s2.test(student.getLastName()) );
                    break;
                case "section":
                    Predicate<Integer> s3 = (Predicate<Integer>) condition.getValue();
                    studentsStream = studentsStream.filter( student -> s3.test(student.getSection()) );
                    break;
                case "courses_results":
                    Predicate<Map<String, Double>> s4 = (Predicate<Map<String, Double>>) condition.getValue();
                    studentsStream = studentsStream.filter( student -> s4.test(student.getCourses_results()) );
                    break;
            }
        }
        return studentsStream;
    }
}