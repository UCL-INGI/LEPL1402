
package templates;

public class Plant implements Comparable<Plant>{

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

    @Override
    public int compareTo(Plant o) {
        @@studentPlantComparable@@
    }
}
