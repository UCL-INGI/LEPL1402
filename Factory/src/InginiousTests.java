package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    private Supplier<Integer> rng = () -> (int) (Math.random() * 4); // between 0 and 3

    Supplier<Integer> rngSize = () -> (int) Math.max(3, ((Math.random() * 7)+1)); // between 3 and 7

    private String [] elems = {"#","K","-","D"};

    private Supplier<String> levels = () -> {
        int size = rngSize.get();
        StringBuilder str = new StringBuilder();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                str.append(elems[rng.get()]);
            }
            str.append("\n");
        }
        return str.toString();
    };


    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback( message= "There is a problem in your level construction. Check your factory and your level class", onFail = true),
                    @GradeFeedback(message = "", onSuccess = true)})
    public void testFactory(){

        // Test with 3 random levels
        String [] input = {levels.get(), levels.get(), levels.get()};

        for(String in : input) {

            Level level = new Level(in);
            assertEquals(in, level.toString());
            LevelComponent[][] levelcomps = level.getComponents();
            int size = level.getSize();

            int idx = 0;
            for(int i=0; i<Math.sqrt(size); i++){
                for(int j=0; j<Math.sqrt(size); j++){
                    assertEquals(levelcomps[i][j].draw(), String.valueOf(in.charAt(idx++)));
                }
                idx++;
            }

            assertEquals(size, (int)(in.length()-Math.sqrt(size)));

        }

    }


    @Test(expected = IllegalAccessException.class)
    @Grade
    @GradeFeedbacks({ @GradeFeedback(message = "Your factory is not a singleton.", onFail = true),
                       @GradeFeedback(message = "", onSuccess = true)})
    public void testSingleton() throws IllegalAccessException, InstantiationException {

        ElementFactory factory = ElementFactory.getInstance();
        ElementFactory factoryBis = ElementFactory.getInstance();
        assertTrue(factory == factoryBis);

        Class cls = ElementFactory.class;
        cls.newInstance(); // shouldn't be possible to instantiate the factory

    }


}
