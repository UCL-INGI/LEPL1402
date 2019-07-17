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
            int [] seeds = Stream.generate(rng).limit(number_of_column).mapToInt(i -> i).toArray();
            original[row] = seeds;
        }

        return original;
    }

    // compute matrix multiplication
    // Credits to https://www.programiz.com/java-programming/examples/multiply-matrix-function
    // (code on stackoveflow was ugly as hell)
    private int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix, int r1, int c1, int c2) {
        int[][] product = new int[r1][c2];
        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return product;
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
                
                int [] seeds = Stream.generate(rng).limit(rng2.get()).mapToInt(j -> j).toArray();
                for(int v: seeds) {
                    str.append(String.valueOf(v) + " ");
                }
                expected[row] = seeds;
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
                int [] seeds = Stream.generate(rng).limit(rng2.get()).mapToInt(j -> j).toArray();
                for(int v: seeds) {
                    sum += v;
                }
                expected[row] = seeds;
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

    // Test for product
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "int[][] product(int[][] matrix1, int[][] matrix2) does not work\n", onFail = true, onTimeout = true)})
    public void testProduct() {
        
        for(int i = 0; i < 10; i++) {
            
            // generate an 2D array that will be an regular matrix
            int number_of_row = rng2.get();
            int column = rng2.get();
            int [][] matrix1 = generate_regular_matrix(number_of_row, column);
            int column2 = rng2.get();
            int [][] matrix2 = generate_regular_matrix(column, column2);

            // compute the real result
            int [][] expected = multiplyMatrices(matrix1, matrix2, number_of_row, column, column2);

            // Time to test the student
            assertTrue(
                Arrays.deepEquals(
                    expected, 
                    student_code.product(matrix1, matrix2)
                )
            );

        }
    }

}
