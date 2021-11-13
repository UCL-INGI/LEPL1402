package module3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InheritanceFillTheGapsTest {

    @Test
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
    public void testConstructorCat() {
        Animal a1 = new Cat();
        assertEquals("Cat", a1.getName());
    }

    @Test
    public void testActForTestMethod() {
        Cat a1 = new Cat();
        a1.actForTestMethod();
        assertEquals("Cat is performing the following action: Thinking\n", a1.logs());
    }

    @Test
    public void testClearLog() {
        SuperCat a1 = new SuperCat();
        a1.clearLog();
        assertTrue(a1.logs().isEmpty());
    }

}