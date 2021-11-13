package module5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MapFilterConsTest {

    // generate random number
    private Supplier<Integer> rng = () -> (int) ((Math.random()*100) + 1); // never generate 0

    // function to collect elements
    private ArrayList<Integer> collectCons(MapFilterCons.Cons student) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        MapFilterCons.Cons currentElement = student;
        while(currentElement != null) {
            result.add(currentElement.v);
            currentElement = currentElement.next;
        }
        return result;
    }

    // Test for filter
    @Test
    public void testFilter(){

        for(int i=0; i<100; i++){


            Integer [] seeds = Stream.generate(rng).limit(3).toArray(Integer[]::new);

            MapFilterCons.Cons list = new MapFilterCons.Cons(seeds[0], new MapFilterCons.Cons(seeds[1], new MapFilterCons.Cons(seeds[2], null)));

            // result
            int randomValue = rng.get();
            MapFilterCons.P filterFunction = new FilterElementLowerThan(randomValue);
            MapFilterCons.Cons filterResult = list.filter(filterFunction);

            // assert
            ArrayList<Integer> elements = collectCons(list);
            ArrayList<Integer> collectedResult = collectCons(filterResult);
            ArrayList<Integer> expectedResult = new ArrayList<Integer>();
            for (Integer element : elements) {
                if ( filterFunction.filter((int) element) ) {
                    expectedResult.add(element);
                }
            }
            assertEquals(expectedResult, collectedResult);
        }
    }

    // Test for map
    @Test
    public void testMap(){
        Integer [] seeds = Stream.generate(rng).limit(3).toArray(Integer[]::new);

       MapFilterCons.Cons list = new MapFilterCons.Cons(seeds[0], new MapFilterCons.Cons(seeds[1], new MapFilterCons.Cons(seeds[2], null)));

        // results
        int randomValue = rng.get();
        MapFilterCons.F mapFunction = new MapBy(randomValue);
        MapFilterCons.Cons expectedList = new MapFilterCons.Cons(seeds[0] * randomValue, new MapFilterCons.Cons(seeds[1] * randomValue, new MapFilterCons.Cons(seeds[2] * randomValue, null)));
        MapFilterCons.Cons mapResult = list.map(mapFunction);

        // assert
        ArrayList<Integer> collectedResult = collectCons(mapResult);
        ArrayList<Integer> expectedResult = collectCons(expectedList);
        assertEquals(expectedResult, collectedResult);
    }

    class FilterElementLowerThan implements MapFilterCons.P {
        int number;
        FilterElementLowerThan(int number) {
            this.number = number;
        }
        public boolean filter(int v) {
            return v < number;
        }
    }

    class MapBy implements MapFilterCons.F {
        int number;
        MapBy(int number) {
            this.number = number;
        }
        public int apply(int v) {
            return v * number;
        }
    }
}
