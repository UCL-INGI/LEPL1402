package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testWithTwoPersonsOfSameAge() throws CustomGradingResult {

        Person[] person_objects = {new Person("Guillaume", 20), new Person("John", 20)};
        ArrayList<Person> persons = new ArrayList<Person>(Arrays.asList(person_objects));

        // use student code here
        Sorter.sortPerson(persons);

        // verification time

        if (persons.get(0).equals(person_objects[1])) {
            throw new CustomGradingResult(TestStatus.FAILED, 0, "John should be after Guillaume : not the opposite");
        }

        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testWithThreePersonsOfSameName() throws CustomGradingResult {

        Person[] person_objects = {
                new Person("Guillaume", 74),
                new Person("Guillaume", 20),
                new Person("Guillaume", 45),
        };

        Person[] correct_order = { person_objects[1], person_objects[2], person_objects[0]};
        ArrayList<Person> expected_persons = new ArrayList<>(Arrays.asList(correct_order));

        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(person_objects));

        // use student code here
        Sorter.sortPerson(persons);

        // verification time

        if ( ! expected_persons.equals(persons) ) {
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Not the correct order ");
        }

        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }

    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testWithGivenExample() throws CustomGradingResult {

        Person[] person_objects = {
                new Person("Guillaume",20),
                new Person("John",20),
                new Person("Guillaume",10),
                new Person("John",10),
                new Person("Luc",5)
        };

        Person[] correct_order = {
                person_objects[2], person_objects[0], person_objects[3], person_objects[1], person_objects[4]
        };
        ArrayList<Person> expected_persons = new ArrayList<>(Arrays.asList(correct_order));

        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(person_objects));

        // use student code here
        Sorter.sortPerson(persons);

        // verification time

        if ( ! expected_persons.equals(persons) ) {
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Not the correct order ");
        }

        throw new CustomGradingResult(TestStatus.SUCCESS, 1);

    }

}
