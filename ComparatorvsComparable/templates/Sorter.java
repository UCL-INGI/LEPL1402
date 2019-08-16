package templates;
import java.awt.Color;

import java.util.Comparator;
import java.util.List;

public class Sorter {

    public static void sortFlowerByColor(List<Flower> list){
        @@studentFlowerComparator@@
    }

    public static void sortPlantByName(List<Plant> list){
        @@studentPlantComparator@@
    }

    public static void sortTreeByHeight(List<Tree> list){
        @@studentTreeComparator@@
    }
}
