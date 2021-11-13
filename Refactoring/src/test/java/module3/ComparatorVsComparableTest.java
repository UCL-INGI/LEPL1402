package module3;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ComparatorVsComparableTest {


    private int rng(){
        return (new Random().nextInt(200))-100;
    }


    @Test
    public void testPlantComparable() {
        Plant p1 = new Plant("poppy", 3);
        Plant p2 = new Plant("rose", 2);
        Plant p3 = new Plant("Poppy", 4);
        Plant p4 = new Plant("poppy", 2);

        List<Plant> list = new ArrayList<Plant>();
        List<Plant> sortedList = new ArrayList<Plant>();

        sortedList.add(p3);
        sortedList.add(p4);
        sortedList.add(p1);
        sortedList.add(p2);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Collections.sort(list);

        assertTrue(list.equals(sortedList));

    }
    @Test
    public void testPlantComparator() {
        Plant p1 = new Plant("poppy", 3);
        Plant p2 = new Plant("rose", 2);
        Plant p3 = new Plant("Poppy", 4);

        List<Plant> list = new ArrayList<Plant>();
        List<Plant> sortedList = new ArrayList<Plant>();

        sortedList.add(p3);
        sortedList.add(p1);
        sortedList.add(p2);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Sorter.sortPlantByName(list);

        assertTrue(list.equals(sortedList));

    }

    @Test
    public void testFlowerComparable() {
        Flower p1 = new Flower("poppy", 3, new Color(1,1,1));
        Flower p2 = new Flower("rose", 2, new Color(2,1,1));
        Flower p3 = new Flower("Poppy", 4, new Color(2,2,1));
        Flower p4 = new Flower("poppy", 2, new Color(2,2,1));
        Flower p5 = new Flower("poppy", 1, new Color(2,2,2));

        List<Flower> list = new ArrayList<Flower>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        List<Flower> sortedList = new ArrayList<Flower>();

        sortedList.add(p3);
        sortedList.add(p5);
        sortedList.add(p4);
        sortedList.add(p1);
        sortedList.add(p2);

        Collections.sort(list);

        assertTrue(list.equals(sortedList));


    }
    @Test
    public void testFlowerComparator() {
        Flower p1 = new Flower("poppy", 3, new Color(1,1,1));
        Flower p2 = new Flower("rose", 2, new Color(2,1,1));
        Flower p3 = new Flower("Poppy", 4, new Color(2,2,1));
        Flower p4 = new Flower("poppy", 2, new Color(2,2,1));
        Flower p5 = new Flower("poppy", 1, new Color(2,2,2));

        List<Flower> list = new ArrayList<Flower>();
        list.add(p3);
        list.add(p4);
        list.add(p2);
        list.add(p5);
        list.add(p1);

        List<Flower> sortedList = new ArrayList<Flower>();

        sortedList.add(p1);
        sortedList.add(p2);
        sortedList.add(p3);
        sortedList.add(p4);
        sortedList.add(p5);

        Sorter.sortFlowerByColor(list);

        assertTrue(list.equals(sortedList));

    }

    @Test
    public void testTreeComparable() {
        Tree p1 = new Tree("poppy", 3, 10);
        Tree p2 = new Tree("rose", 2, 12);
        Tree p3 = new Tree("Poppy", 4, 9);
        Tree p4 = new Tree("poppy", 2, 11);

        List <Tree> list = new ArrayList <Tree>();
        List <Tree> sortedList = new ArrayList <Tree>();

        sortedList.add(p3);
        sortedList.add(p4);
        sortedList.add(p1);
        sortedList.add(p2);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Collections.sort(list);

        assertTrue(list.equals(sortedList));

    }
    @Test
    public void testTreeComparator() {
        Tree p1 = new Tree("poppy", 3, 10);
        Tree p2 = new Tree("rose", 2, 12);
        Tree p3 = new Tree("Poppy", 4, 9);
        Tree p4 = new Tree("poppy", 2, 11);

        List <Tree> list = new ArrayList <Tree>();
        List <Tree> sortedList = new ArrayList <Tree>();

        sortedList.add(p3);
        sortedList.add(p1);
        sortedList.add(p4);
        sortedList.add(p2);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Sorter.sortTreeByHeight(list);

        assertTrue(list.equals(sortedList));
    }
}
