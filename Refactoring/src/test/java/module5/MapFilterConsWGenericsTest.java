package module5;

import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.*;

import static org.junit.Assert.*;

public class MapFilterConsWGenericsTest {

    // generate random number
    private Supplier<Integer> rng = () -> (int) ((Math.random()*100) + 2); // never generate 0 or 1

    // function to collect elements
    private ArrayList<Integer> collectCons(MapFilterConsWGenerics.Cons<Integer> student) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        MapFilterConsWGenerics.Cons<Integer> currentElement = student;
        while(currentElement != null) {
            result.add( (Integer) currentElement.v);
            currentElement = currentElement.next;
        }
        return result;
    }

    // Test for filter
    @Test
    public void testFilter(){

        for(int i=0; i < 100; i++){
            Integer [] seeds = Stream.generate(rng).limit(3).toArray(Integer[]::new);

            MapFilterConsWGenerics.Cons<Integer> list = new MapFilterConsWGenerics.Cons(seeds[0], new MapFilterConsWGenerics.Cons(seeds[1], new MapFilterConsWGenerics.Cons(seeds[2], null)));

            // result
            int randomValue = rng.get();
            MapFilterConsWGenerics.Cons<Integer> filterResult = list.filter(p -> p < randomValue);

            // assert
            ArrayList<Integer> elements = collectCons(list);
            ArrayList<Integer> collectedResult = collectCons(filterResult);
            ArrayList<Integer> expectedResult = new ArrayList<Integer>();
            for (Integer element : elements) {
                if ( element < randomValue ) {
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

        MapFilterConsWGenerics.Cons<Integer> list = new MapFilterConsWGenerics.Cons(seeds[0], new MapFilterConsWGenerics.Cons(seeds[1], new MapFilterConsWGenerics.Cons(seeds[2], null)));

        // results
        int randomValue = rng.get();
        MapFilterConsWGenerics.Cons<Integer> expectedList = new MapFilterConsWGenerics.Cons(seeds[0] * randomValue, new MapFilterConsWGenerics.Cons(seeds[1] * randomValue, new MapFilterConsWGenerics.Cons(seeds[2] * randomValue, null)));
        MapFilterConsWGenerics.Cons<Integer> mapResult = list.map(i -> i * randomValue);

        // assert
        ArrayList<Integer> collectedResult = collectCons(mapResult);
        ArrayList<Integer> expectedResult = collectCons(expectedList);
        assertEquals(expectedResult, collectedResult);
    }

}
