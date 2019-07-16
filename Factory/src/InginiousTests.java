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

    private String [] elems = {"#","K","-","D"};

    private Supplier<String> levels = () -> {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
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
            assertEquals(level.toString(), in);
            LevelComponent[][] levelcomps = level.getComponents();
            int size = level.getSize();

            int idx = 0;
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    assertEquals(levelcomps[i][j].draw(), String.valueOf(in.charAt(idx++)));
                }
                idx++;
            }

        }

    }


    @Test
    @Grade
    @GradeFeedbacks({ @GradeFeedback(message = "Your factory is not a singleton.", onFail = true),
                       @GradeFeedback(message = "", onSuccess = true)})
    public void testSingleton(){
        ElementFactory factory = ElementFactory.getInstance();
        ElementFactory factoryBis = ElementFactory.getInstance();

        assertTrue(factory == factoryBis);
    }
}
