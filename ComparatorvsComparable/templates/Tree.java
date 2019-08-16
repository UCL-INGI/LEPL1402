package templates;

public class Tree extends Plant implements Comparable<Plant>{

    private int height;

    public Tree(String name, int age, int height) {
        super(name, age);
        this.height=height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int compareTo(Plant o) {
        @@studentTreeComparable@@
    }
}
