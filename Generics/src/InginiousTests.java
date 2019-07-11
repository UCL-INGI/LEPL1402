package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    // Test for filter

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Filter does not work\n", onFail = true, onTimeout = true)})
    public void testFilter(){
        
        List<String> v = Arrays.asList("Java", "jAVA", "I love code");
        ArrayList<String> init = new ArrayList(v);
        GenericList<String> test = new MyList(init);

        // filter 
        GenericList<String> filterResult = test.filter(s -> !s.equalsIgnoreCase("java"));

        // assert
        List<String> result = Arrays.asList("I love code");
        ArrayList<String> expected = new ArrayList<String>(result);
        assertTrue(expected.equals(filterResult.elements));
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Filter does not work (empty array)\n", onFail = true, onTimeout = true)})
    public void testFilter2(){
        GenericList<Integer> test = new MyList<Integer>(new ArrayList());

        // filter
        GenericList<Integer> filterResult = test.filter(i -> i > 0);

        // assert
        ArrayList<Integer> expected = new ArrayList<Integer>();
        assertTrue(expected.equals(filterResult.elements));
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

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Map does not work\n", onFail = true, onTimeout = true)})
    public void testMap2(){
        
        List<String> v = Arrays.asList("java is great", "java is too old", "I love java");
        ArrayList<String> init = new ArrayList(v);
        GenericList<String> test = new MyList<String>(init);

        // map
        GenericList<String> mapResult = test.map(s -> s.replace("java", "NODE.JS"));

        // assert
        List<String> result = Arrays.asList("NODE.JS is great", "NODE.JS is too old", "I love NODE.JS");
        ArrayList<String> expected = new ArrayList<String>(result);
        assertTrue(expected.equals(mapResult.elements));
    }

}
