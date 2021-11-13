package module4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class FactoryDesignPatternTest {
    //BEGIN STRIP

    private Supplier<Integer> rng = () -> (int) (Math.random() * 4); // between 0 and 3

    Supplier<Integer> rngSize = () -> (int) Math.max(3, ((Math.random() * 7) + 1)); // between 3 and 7

    private String[] elems = {"#", "K", "-", "D"};

    private Supplier<String> levels = () -> {
        int size = rngSize.get();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str.append(elems[rng.get()]);
            }
            str.append("\n");
        }
        return str.toString();
    };


    @Test
    public void testFactory() {

        // Test with 3 random levels
        String[] input = {levels.get(), levels.get(), levels.get()};
        int errorType = -1;

        try {
            for (String in : input) {

                FactoryDesignPattern.Level level = new FactoryDesignPattern.Level(in);

                // check if their toString method works as expected
                if (!in.equals(level.toString())) {
                    errorType = 1;
                    break;
                }


                FactoryDesignPattern.LevelComponent[][] levelcomps = level.getComponents();

                // check that their levelcomps isn't empty
                if (levelcomps == null || levelcomps.length == 0 || levelcomps[0].length == 0) {
                    errorType = 2;
                    break;
                }

                int size = level.getSize();

                // check if their size is the expected one
                int goodSize = (int) (in.length() - Math.sqrt(size));
                if (size != goodSize) {
                    errorType = 3;
                    break;
                }

                // check if their array contains LevelComponent (and not empty objects)
                boolean error = Arrays.stream(levelcomps).anyMatch(
                        subArray -> Arrays.stream(subArray).anyMatch(Objects::isNull)
                );

                if (error) {
                    errorType = 4;
                    break;
                }

                int idx = 0;
                boolean shouldContinue = true;
                for (int i = 0; shouldContinue && i < Math.sqrt(size); i++) {
                    for (int j = 0; shouldContinue && j < Math.sqrt(size); j++) {
                        String result = levelcomps[i][j].draw();
                        String expected = String.valueOf(in.charAt(idx++));
                        if (!expected.equals(result)) {
                            shouldContinue = false;
                            errorType = 5;
                        }
                    }
                    idx++;
                }

            }
        } catch (Exception e) {

            if (e instanceof IndexOutOfBoundsException) {
                fail("(Array)IndexOutOfBoundsException thrown: " +
                        "did you check whether your Level(String input) works with the given example?");
            }

            // should never reach that but who knows
            fail("An unexpected error occured");
        }

        switch (errorType) {
            case 1:
                fail("Your toString() method doesn't work as expected: It should return the same string you received within the constructor");
            case 2:
                fail("Your getComponents() method shouldn't return an empty object");
            case 3:
                fail("Your getSize() method doesn't give the expected result: read the assignment carefully");
            case 4:
                fail("Your getComponents() method shouldn't contain null objects");
            case 5:
                fail("Not the expected LevelComponent drawn");
            default:
                fail("Nice - You survived all the tests");
        }

    }


    @Test(expected = IllegalAccessException.class)
    public void testSingleton() throws IllegalAccessException, InstantiationException {

        FactoryDesignPattern.ElementFactory factory = FactoryDesignPattern.ElementFactory.getInstance();
        FactoryDesignPattern.ElementFactory factoryBis = FactoryDesignPattern.ElementFactory.getInstance();
        assertTrue(factory == factoryBis);

        Class cls = FactoryDesignPattern.ElementFactory.class;
        cls.newInstance(); // shouldn't be possible to instantiate the factory

    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionBadInput() throws IllegalArgumentException {
        String input = levels.get().replaceFirst("#", "9").replaceFirst("D", "A");
        FactoryDesignPattern.Level level = new FactoryDesignPattern.Level(input);
    }
    //END STRIP
}
