package src;

// filter the number lower than given one
public class FilterElementLowerThan implements P {
    int number;
    FilterElementLowerThan(int number) {
        this.number = number;
    }
    public boolean filter(int v) {
        return v < number;
    }
}