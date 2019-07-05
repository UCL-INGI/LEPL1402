package BlackBox;

public interface ExerciseFlavour {

    /*
     * An exercise flavour is an exercise where you want to run a single test suite
     * on different implementations of the same algorithm / problem. Each flavour
     * MUST implement the method "correctness" stating whether the flavour is correct
     * or not. You may want to willingly implement wrong algorithm(s) if (for example) you
     * want to assess the quality of a test suite provided by students.
     */


    // Return if the flavour is correct or not.
    public boolean correctness();

}
