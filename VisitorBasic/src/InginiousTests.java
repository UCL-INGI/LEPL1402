package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    private Class [] cls = {Integer.class, Float.class, Double.class, String.class, Long.class};

    private Supplier [] suppliers = new Supplier[]{
            () ->  (int) (Math.random()*100) +1, // int
            () -> "test", // String
            () -> (long) (Math.random()*100) + 1, // long
            () -> (Math.random()*100) + 1 // double
    };

    private Supplier<Integer> rng = () -> (int) (Math.random()*suppliers.length);


    @Test
    @Grade
    public void Randomtest(){

        for(int n = 0; n < 10; n++) { // repeat 10 times the test, in case of luck.

            Object[] random = new Object[30];

            for (int i = 0; i < random.length; i++) {
                random[i] = suppliers[rng.get()].get();
            }

            Visitable list = new VisitableList(random);
            Visitor visitor = new VisitorList();

            list.accept(visitor);

            List<? extends Object> result = visitor.getFiltered();

            for (int i = 0; i < random.length; i++) {

                if (random[i] instanceof Integer) {
                    assertTrue(result.contains(random[i]));
                }
            }

            for (int i = 0; i < result.size(); i++) {
                assertTrue(result.get(i) instanceof Integer);
            }

        }
    }
}
