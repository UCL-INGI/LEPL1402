package src;

// filter the number lower than given one
public class MapBy implements F {
    int number;
    MapBy(int number) {
        this.number = number;
    }
    public int apply(int v) {
        return v * number;
    }
}