package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import templates.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Your act method in animal didn't work as expected")})
    public void testActAnimal() {
        Animal a1 = new Animal("Unknown animal");
        Animal a2 = new Cat();
        String[] acttions = new String[] {"EAT", "SLEEP", "CODE", "REPEAT"};
        StringBuilder expected = new StringBuilder();

        for(String action : acttions) {
            a1.act(action);
            a2.act(action);
            expected.append("%s is performing the following action: ").append(action).append("\n");
        }

        assertEquals(expected.toString().replaceAll("%s", "Unknown animal"), a1.logs());
        assertEquals(expected.toString().replaceAll("%s", "Cat"), a2.logs());

    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Your constructor for Cat didn't work as expected")})
    public void testConstructorCat() {
        Animal a1 = new Cat();
        assertEquals("Cat", a1.getName());
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Your actForTestMethod method in Cat didn't work as expected")})
    public void testActForTestMethod() {
        Cat a1 = new Cat();
        a1.actForTestMethod();
        assertEquals("Cat is performing the following action: Thinking\n", a1.logs());
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(onSuccess = true, message = ""),
            @GradeFeedback(onFail = true, message = "Your clearLog method in SuperCat didn't work as expected")})
    public void testClearLog() {
        SuperCat a1 = new SuperCat();
        a1.clearLog();
        assertTrue(a1.logs().isEmpty());
    }

}