package module4;

import org.junit.Test;

public class CoverageTestingIntermediateTest {
    @Test
    public void test() {
        int [] a = new int[]{}; // your test array
        int e = CoverageTestingIntermediate.BinarySearch.binarySearch(a, 3); // find the value
        // some asserts
    }

    //TODO: some other tests in order to have edge and node coverage
}
