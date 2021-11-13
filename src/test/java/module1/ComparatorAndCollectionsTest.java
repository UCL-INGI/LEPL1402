package module1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ComparatorAndCollectionsTest {

    //BEGIN STRIP
    @Test
    public void testWithTwoPersonsOfSameAge() {

        ComparatorAndCollections.Person[] person_objects = {new ComparatorAndCollections.Person("Guillaume", 20), new ComparatorAndCollections.Person("John", 20)};
        ArrayList<ComparatorAndCollections.Person> persons = new ArrayList<ComparatorAndCollections.Person>(Arrays.asList(person_objects));

        // use student code here
        ComparatorAndCollections.Sorter.sortPerson(persons);

        // verification time
        assertNotEquals("John should be after Guillaume : not the opposite", persons.get(0), person_objects[1]);
    }

    @Test
    public void testWithThreePersonsOfSameName() {

        ComparatorAndCollections.Person[] person_objects = {
                new ComparatorAndCollections.Person("Guillaume", 74),
                new ComparatorAndCollections.Person("Guillaume", 20),
                new ComparatorAndCollections.Person("Guillaume", 45),
        };

        ComparatorAndCollections.Person[] correct_order = { person_objects[1], person_objects[2], person_objects[0]};
        ArrayList<ComparatorAndCollections.Person> expected_persons = new ArrayList<>(Arrays.asList(correct_order));

        ArrayList<ComparatorAndCollections.Person> persons = new ArrayList<>(Arrays.asList(person_objects));

        // use student code here
        ComparatorAndCollections.Sorter.sortPerson(persons);

        // verification time
        assertEquals("Not the correct order ", expected_persons, persons);
    }
    //END STRIP

    @Test
    public void testWithGivenExample() {

        ComparatorAndCollections.Person[] person_objects = {
                new ComparatorAndCollections.Person("Guillaume",20),
                new ComparatorAndCollections.Person("John",20),
                new ComparatorAndCollections.Person("Guillaume",10),
                new ComparatorAndCollections.Person("John",10),
                new ComparatorAndCollections.Person("Luc",5)
        };

        ComparatorAndCollections.Person[] correct_order = {
                person_objects[2], person_objects[0], person_objects[3], person_objects[1], person_objects[4]
        };
        ArrayList<ComparatorAndCollections.Person> expected_persons = new ArrayList<>(Arrays.asList(correct_order));

        ArrayList<ComparatorAndCollections.Person> persons = new ArrayList<>(Arrays.asList(person_objects));

        // use student code here
        ComparatorAndCollections.Sorter.sortPerson(persons);

        // verification time
        assertEquals("Not the correct order ", expected_persons, persons);
    }

}
