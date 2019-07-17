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
    // random number of row line (just in order to limit the matrix product or other)
    private Supplier<Integer> rng2 = () -> (int) ((Math.random()*10) + 1); // never generate 0
    // Student code
    private Array2DBuilderInterface student_code = new MyBuilder();

    // build a regular matrix
    private int[][] generate_regular_matrix(int number_of_row, int number_of_column) {
        int [][] original = new int[number_of_row][];

        for(int row = 0; row < number_of_row; row++) {
            Integer [] seeds = Stream.generate(rng).limit(number_of_column).toArray(Integer[]::new);
            original[i] = seeds;
        }

        return original;
    }

    // Test for build_from
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "build_from(String s) does not work\n", onFail = true, onTimeout = true)})
    public void testBuildFrom() {
        
        for(int i = 0; i < 10; i++) {
            
            // generate an 2D array that will mostly be an irregular matrix
            int number_of_row = rng2.get();
            int [][] expected = new int[number_of_row][];
            StringBuilder str = new StringBuilder(); 

            for(int row = 0; row < number_of_row; row++) {
                // random number for column
                Integer [] seeds = Stream.generate(rng).limit(rng2.get()).toArray(Integer[]::new);
                for(Integer v: seeds) {
                    str.append(v.toString() + " ")
                }
                expected[i] = seeds;
                str.append("\n");
                if (row != number_of_row -1) {
                    str.append(" ");
                }
            }

            // Time to stringify this thing for student
            String input = str.toString();
            assertTrue(
                Arrays.deepEquals(
                    expected, 
                    student_code.build_from(input)
                )
            );

        }
    }

    // Test for sum
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "sum(int[][] array) does not work\n", onFail = true, onTimeout = true)})
    public void testSum() {
        
        for(int i = 0; i < 10; i++) {
            
            // generate an 2D array that will mostly be an irregular matrix
            int number_of_row = rng2.get();
            int [][] expected = new int[number_of_row][];
            int sum = 0;

            for(int row = 0; row < number_of_row; row++) {
                // random number for column
                Integer [] seeds = Stream.generate(rng).limit(rng2.get()).toArray(Integer[]::new);
                for(Integer v: seeds) {
                    sum += v;
                }
                expected[i] = seeds;
            }

            // Time to test the student
            assertEquals(sum, student_code.sum(expected));

        }
    }

    // Test for transpose
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "int[][] transpose(int[][] matrix) does not work\n", onFail = true, onTimeout = true)})
    public void testTranspose() {
        
        for(int i = 0; i < 10; i++) {
            
            // generate an 2D array that will be an regular matrix
            int number_of_row = rng2.get();
            int column = rng2.get();
            int [][] original = generate_regular_matrix(number_of_row, column);

            // compute the real transpose
            int [][] expected = new int[column][number_of_row];
            for(int a = 0 ; a < column; a++) {
                for(int b = 0; b < number_of_row; b++) {
                    expected[a][b] = original[b][a]; 
                }
            }

            // Time to test the student
            assertTrue(
                Arrays.deepEquals(
                    expected, 
                    student_code.transpose(original)
                )
            );

        }
    }

}
