package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    // generate random number
    private Supplier<Integer> rng = () -> (int) ((Math.random()*100) + 1); // never generate 0

    // function to collect elements
    private ArrayList<Integer> collectCons(Cons student) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Cons currentElement = student;
        while(currentElement != null) {
            result.add(currentElement.v);
            currentElement = currentElement.next;
        }
        return result;
    }

    // Test for filter
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Filter does not work\n", onFail = true, onTimeout = true)})
    public void testFilter(){
        
        Integer [] seeds = Stream.generate(rng).limit(3).toArray(Integer[]::new);
        
        Cons list = new Cons(seeds[0], new Cons(seeds[1], new Cons(seeds[2], null)));

        // filter the number lower than given one
        class FilterElementLowerThan extends P {
            int number;
            FilterElementLowerThan(int number) {
                this.number = number;
            }
            boolean filter(int v) {
                return v < number;
            }
        }

        int randomValue = rng.get();
        F filterFunction = new FilterElementLowerThan(randomValue);
        Cons filterResult = list.filter(filterFunction);

        // assert
        ArrayList<Integer> collectedResult = collectCons(filterResult);
        ArrayList<Integer> expectedResult = collectCons(list);
        // remove the elements that didn't match
        expectedResult.removeIf(i -> !v < number);
        assertEquals(expectedResult, collectedResult);
    }

    // Test for map
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Map does not work\n", onFail = true, onTimeout = true)})
    public void testMap(){
        
        List<String> v = Arrays.asList("Java", "jAVA", "I love code");
        ArrayList<String> init = new ArrayList(v);
        GenericList<String> test = new MyList<String>(init);

        // map
        GenericList<String> mapResult = test.map(s -> s.toUpperCase());

        // assert
        List<String> result = Arrays.asList("JAVA", "JAVA", "I LOVE CODE");
        ArrayList<String> expected = new ArrayList<String>(result);
        assertTrue(expected.equals(mapResult.elements));
    }

}
