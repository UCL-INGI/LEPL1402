package src;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

public class ComparatorAndCollectionsTest {

    //BEGIN STRIP
    @Test
    public void testWithTwoPersonsOfSameAge() {

        Person[] person_objects = {new Person("Guillaume", 20), new Person("John", 20)};
        ArrayList<Person> persons = new ArrayList<Person>(Arrays.asList(person_objects));

        // use student code here
        ComparatorAndCollections.sortPerson(persons);

        // verification time
        assertNotEquals("John should be after Guillaume : not the opposite", persons.get(0), person_objects[1]);
    }

    @Test
    public void testWithThreePersonsOfSameName() {

        Person[] person_objects = {
                new Person("Guillaume", 74),
                new Person("Guillaume", 20),
                new Person("Guillaume", 45),
        };

        Person[] correct_order = { person_objects[1], person_objects[2], person_objects[0]};
        ArrayList<Person> expected_persons = new ArrayList<>(Arrays.asList(correct_order));

        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(person_objects));

        // use student code here
        ComparatorAndCollections.sortPerson(persons);

        // verification time
        assertEquals("Not the correct order ", expected_persons, persons);
    }
    //END STRIP

    @Test
    public void testWithGivenExample() {

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
        ComparatorAndCollections.sortPerson(persons);

        // verification time
        assertEquals("Not the correct order ", expected_persons, persons);
    }

}
