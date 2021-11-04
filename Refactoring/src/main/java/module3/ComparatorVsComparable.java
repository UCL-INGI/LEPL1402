package module3;

import java.awt.*;
import java.util.List;

/**
 * In this exercise, you have to answer questions about Comparator and Comparable.
 *      - Comparator: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
 *      - Comparable: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
 *
 * We are going to work on the Plant, Tree, Flower and Sorter classes.
 * Read and understand the code in this classes before doing this is exercise!
 *
 * All the information on how the different method should work are given in comments in each class.
 *
 * In the code, when we write "in that order", we mean that you must first sort in function of the first criterion,
 * and in case of equality, sort in function of the second, and so on.
 */
class Sorter {
    /*
     * Should sort the list and order the Flower by color, in this specific order : red, blue, green
     */
    public static void sortFlowerByColor(List<Flower> list){
        //TODO by student
    }
    /*
     * Should sort the Plant by name only
     */
    public static void sortPlantByName(List<Plant> list){
        //TODO by student
    }

    /*
     * Should sort the list and order the Tree by height
     */
    public static void sortTreeByHeight(List<Tree> list){
        //TODO by student
    }
}

class Flower extends Plant implements Comparable<Plant>{

    private Color petalColor;

    public Flower(String name, int age, Color color) {
        super(name, age);
        this.petalColor = color;
    }

    public Color getPetalColor() {
        return petalColor;
    }

    /*
     * Should compare Flower by using name and age in that specific order
     * returns:
     *      > 0 if this is greater then o
     *      0 if they are equal
     *      < 0 if this is less than o
     */
    @Override
    public int compareTo(Plant o) {
        //TODO by student
        return 0;
    }
}

class Plant implements Comparable<Plant>{

    private String name;
    private int age;

    public Plant(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    /*
     * Should compare Plant by using name and age in that specific order
     * returns:
     *      > 0 if this is greater then o
     *      0 if they are equal
     *      < 0 if this is less than o
     */
    @Override
    public int compareTo(Plant o) {
        //TODO by student
        return 0;
    }
}

class Tree extends Plant implements Comparable<Plant>{

    private int height;

    public Tree(String name, int age, int height) {
        super(name, age);
        this.height=height;
    }

    public int getHeight() {
        return height;
    }

    /*
     * Should compare Tree by using name and age in that specific order
     * returns:
     *      > 0 if this is greater then o
     *      0 if they are equal
     *      < 0 if this is less than o
     */
    @Override
    public int compareTo(Plant o) {
        //TODO by student
        return 0;
    }
}

