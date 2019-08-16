package templates;

import java.awt.Color;

public class Flower extends Plant implements Comparable<Plant>{

    private Color petalColor;

    public Flower(String name, int age, Color color) {
        super(name, age);
        this.petalColor = color;
    }

    public Color getPetalColor() {
        return petalColor;
    }

    @Override
    public int compareTo(Plant o) {
        @@studentPlantComparable@@
    }
}
