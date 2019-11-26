package src;

import com.github.guillaumederval.javagrading.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

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
    @Grade(custom = true)
    public void testFactory() throws CustomGradingResult {

        // Test with 3 random levels
        String[] input = {levels.get(), levels.get(), levels.get()};
        int errorType = -1;

        try {
            for (String in : input) {

                Level level = new Level(in);

                // check if their toString method works as expected
                if (!in.equals(level.toString())) {
                    errorType = 1;
                    break;
                }

                LevelComponent[][] levelcomps = level.getComponents();

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

                // check if their array contains LevelComponent ( and not empty objects)
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

            if (e instanceof CustomGradingResult) {
                throw new CustomGradingResult(TestStatus.FAILED, 0, "Well tried, but we are protected against that");
            }
            if (e instanceof IndexOutOfBoundsException) {
                throw new CustomGradingResult(TestStatus.FAILED, 0, "(Array)IndexOutOfBoundsException thrown " +
                        ": Did you check that your Level(String input) works with the given example ?");
            }

            // should never reach that but who knows
            throw new CustomGradingResult(TestStatus.FAILED, 0, "An unexpected error occured");
        }

        switch (errorType) {
            case 1:
                throw new CustomGradingResult(TestStatus.FAILED, 0, "Your toString() method doesn't work as expected : " +
                        "It should be the same string you received with the constructor");
            case 2:
                throw new CustomGradingResult(TestStatus.FAILED, 0.2, "Your getComponents() method should not be empty");
            case 3:
                throw new CustomGradingResult(TestStatus.FAILED, 0.4, "Your getSize() method doesn't give the expected result : Read carefully the assignment");
            case 4:
                throw new CustomGradingResult(TestStatus.FAILED, 0.6, "Your getComponents() method should not contain null object");
            case 5:
                throw new CustomGradingResult(TestStatus.FAILED, 0.6, "Not the expected LevelComponent drawn");
            default:
                throw new CustomGradingResult(TestStatus.SUCCESS, 1, "Nice - You survived all the tests");
        }

    }


    @Test(expected = IllegalAccessException.class)
    @Grade
    @GradeFeedback(message = "Your factory is not a singleton.", onFail = true)
    public void testSingleton() throws IllegalAccessException, InstantiationException {

        ElementFactory factory = ElementFactory.getInstance();
        ElementFactory factoryBis = ElementFactory.getInstance();
        assertTrue(factory == factoryBis);

        Class cls = ElementFactory.class;
        cls.newInstance(); // shouldn't be possible to instantiate the factory

    }

    @Test(expected = IllegalArgumentException.class)
    @Grade
    @GradeFeedback(message = "You are not checking the input.", onFail = true)
    public void testExceptionBadInput() throws IllegalArgumentException {
        String input = levels.get().replaceFirst("#", "9").replaceFirst("D", "A");
        Level level = new Level(input);
    }


}
