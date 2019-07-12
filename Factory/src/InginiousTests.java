package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    @Test
    @Grade
    @GradeFeedback(message= "There is a problem in your level construction. Check your factory and your level class", onFail = true)
    public void testFactory(){

        String [] input = {"#####\n#K--#\n#---D\n##--#\n#####\n"};

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
    @GradeFeedback(message = "Your factory is not a singleton.", onFail = true)
    public void testSingleton(){
        ElementFactory factory = ElementFactory.getInstance();
        ElementFactory factoryBis = ElementFactory.getInstance();

        assertTrue(factory == factoryBis);
    }
}
