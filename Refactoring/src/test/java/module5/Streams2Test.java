package module5;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams2Test {
    private Streams2.StudentStreamFunction streamFunction;

    //BEGIN STRIP
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
    private Stream<Streams2.Student> generate_random_students(int number) {
        List<Streams2.Student> my_list = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            // get random index / values
            int index = new Random().nextInt(studentNames.length);
            int index2 = new Random().nextInt(studentNames.length);
            int section = section_rng.get();
            Map<String, Double> grades = new HashMap<>();
            for(String course : courses) {
                grades.put(course, grade_rng.get());
            }
            my_list.add(new Streams2.Student(studentNames[index], studentNames[index2], section, grades));
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
    //END STRIP

    @Before
    public void setUp(){
        streamFunction = new Streams2.StudentFunctions();
    }

    @Test
    public void findOne_simple() {
        Map<String, Double> grades = new HashMap<>();
        Map<String, Predicate<?>> conditions = new HashMap<>();

        Streams2.Student student = new Streams2.Student("Jacques", "42", 42, grades);
        Streams2.Student student1 = new Streams2.Student("Jacques", "42", 12, grades);

        Predicate<String> p1 = (s) -> s.equalsIgnoreCase("jacques");
        Predicate<Integer> p2 = (i) -> i == 42;
        conditions.put("firstName", p1);
        conditions.put("section", p2);

        assertEquals(student, streamFunction.findFirst(Stream.of(student), conditions));
        assertNull(streamFunction.findFirst(Stream.of(student1), conditions));
    }

    //BEGIN STRIP
    @Test
    public void findOne_advanced() {
        // random students
        Streams2.Student[] random_students = generate_random_students(section_rng.get()).toArray(Streams2.Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Streams2.Student expected = handleCondition(Arrays.stream(random_students), testedConditions).findFirst().orElse(null);
            Streams2.Student result = streamFunction.findFirst(Arrays.stream(random_students), testedConditions);
            assertEquals(expected, result);
        }

    }

    @Test
    public void findAll_advanced() {
        // random students
        Streams2.Student[] random_students = generate_random_students(section_rng.get()).toArray(Streams2.Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Streams2.Student[] expected = handleCondition(Arrays.stream(random_students), testedConditions).toArray(Streams2.Student[]::new);
            Streams2.Student[] result = streamFunction.findAll(Arrays.stream(random_students), testedConditions);
            assertTrue(Arrays.deepEquals(expected, result));
        }

    }

    @Test
    public void exists_advanced() {
        // random students
        Streams2.Student[] random_students = generate_random_students(section_rng.get()).toArray(Streams2.Student[]::new);

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
    public void filterThenSort_advanced() {
        // random students
        Streams2.Student[] random_students = generate_random_students(section_rng.get()).toArray(Streams2.Student[]::new);

        for(int i = 0; i < 10; i++) {

            int limit = new Random().nextInt(4);
            Map<String, Predicate<?>> testedConditions = generateConditions(limit);

            Comparator<Streams2.Student> basic = new Comparator<Streams2.Student>() {
                @Override
                public int compare(Streams2.Student o1, Streams2.Student o2) {
                    return o1.compareTo(o2);
                }
            };

            //
            Comparator<Streams2.Student> other = new Comparator<Streams2.Student>() {
                @Override
                public int compare(Streams2.Student o1, Streams2.Student o2) {
                    return o1
                            .getCoursesResults()
                            .values()
                            .stream()
                            .reduce(0.0, (a, b) -> a + b)
                            .compareTo(
                                    o2.getCoursesResults().values().stream().reduce(0.0, (a, b) -> a + b)
                            );
                }
            };

            // with the basic
            Streams2.Student[] expected = handleCondition(Arrays.stream(random_students), testedConditions).sorted(basic).toArray(Streams2.Student[]::new);
            Streams2.Student[] result = streamFunction.filterThenSort(Arrays.stream(random_students), testedConditions, basic);
            assertTrue(Arrays.deepEquals(expected, result));

            // with custom comparator
            expected = handleCondition(Arrays.stream(random_students), testedConditions).sorted(other.reversed()).toArray(Streams2.Student[]::new);
            result = streamFunction.filterThenSort(Arrays.stream(random_students), testedConditions, other.reversed());
            assertTrue(Arrays.deepEquals(expected, result));
        }

    }

    private Stream<Streams2.Student> handleCondition(Stream<Streams2.Student> studentsStream, Map<String, Predicate<?>> conditions) {
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
                    studentsStream = studentsStream.filter( student -> s4.test(student.getCoursesResults()) );
                    break;
            }
        }
        return studentsStream;
    }
    //END STRIP
}