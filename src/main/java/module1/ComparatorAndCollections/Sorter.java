package templates;

import src.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * In this task, the challenge is to sort a custom class Person described as :
 *
 *
 *         public class Person {
 *
 *             public String name;
 *             public int age;
 *
 *             public Person(String name, int age) {
 *                 this.name = name;
 *                 this.age = age;
 *             }
 *
 *             @Override
 *             public String toString() {
 *                 return name + " " + age;
 *             }
 *         }
 *
 *
 * Your task is to implement the function public static void sortPerson(ArrayList<Person> persons) that should sort an ArrayList of Persons as follows :
 *      - sort in the lexicographical order of their name
 *      - If two persons have the same name, they should be classified according to their age (ascending order).
 *
 * In order to succeed this task (whose solution is contained on 8 lines),
 * we invite you to discover the following JavaDoc resource : https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html
 *
 *  Given this example :
 *
 *
 *         public class Main {
 *
 *           public static void main(String[] args) {
 *             ArrayList<Person> persons = new ArrayList<>();
 *             persons.add(new Person("Guillaume",20));
 *             persons.add(new Person("John",50));
 *             persons.add(new Person("Guillaume",10));
 *             persons.add(new Person("John",10));
 *             persons.add(new Person("Luc",5));
 *
 *             sortPerson(persons);
 *             System.out.println(persons);
 *
 *           }
 *        }
 *
 *
 * You should get this on the output :
 *
 *
 *        [Guillaume 10, Guillaume 20, John 10, John 20, Luc 5]
 */
public class Sorter {

    public static void sortPerson(ArrayList<Person> persons) {
        // TODO
    }
}
