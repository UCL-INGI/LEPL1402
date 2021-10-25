package src;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.Grade;

import java.util.stream.Collectors;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.Random;
import templates.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    final private Random rand = new Random();

    private int[][] generateRectangularMatrix() {
        int numberOfRow = rand.nextInt(20) + 1;
        int numberOfColumn = rand.nextInt(20) + 1;
        int [][] matrix = new int[numberOfRow][numberOfColumn];
        for (int row = 0; row < numberOfRow; row++) {
            for (int col = 0; col < numberOfColumn; col++) {
                matrix[row][col] = rand.nextInt();
            }
        }
        return matrix;
    }

    private int[][] generateNonRectangularMatrix() {
        int numberOfRow = rand.nextInt(20) + 1;
        int [][] matrix = new int[numberOfRow][];
        for (int row = 0; row < numberOfRow; row++) {
            int numberOfCol = rand.nextInt(20) + 1;
            matrix[row] = new int[numberOfCol];
            for (int col = 0; col < numberOfCol; col ++) {
                matrix[row][col] = rand.nextInt();
            }
        }
        return matrix;
    }

    private String matrixtoStr(int [][] matrix) {
        return Arrays.stream(matrix)
            .map(row -> Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining(" ")))
            .collect(Collectors.joining("\n"));
    }

    @Test
    @Grade(value=1)
    public void testBuildFrom() {
        for(int i = 0; i < 10; i++) {
            int [][] expected = generateNonRectangularMatrix();
            String input = matrixtoStr(expected);
            int [][] result = Matrix.buildFrom(input);
            assertArrayEquals(expected, result);
        }
    }

    @Test
    @Grade(value=1)
    public void testSum() {
        for(int i = 0; i < 10; i++) {
            int [][] matrix = generateNonRectangularMatrix();
            int sum = 0;
            for (int row = 0; row < matrix.length; row ++) {
                for (int col = 0; col < matrix[row].length; col++)
                    sum += matrix[row][col];
            }
            assertEquals(sum, Matrix.sum(matrix));
        }
    }

    @Test
    @Grade(value=1)
    public void testTranspose() {

        for(int i = 0; i < 10; i++) {
            int [][] matrix = generateRectangularMatrix();
            int [][] expected = new int[matrix[0].length][matrix.length];
            for (int row = 0; row < matrix.length; row ++) {
                for (int col = 0; col < matrix[0].length; col++)
                    expected[col][row] = matrix[row][col];
            }
            assertArrayEquals(expected, Matrix.transpose(matrix));
        }
    }

    @Test
    @Grade(value=1)
    public void testProduct() {
        for(int i = 0; i < 10; i++) {
            int [][] m1 = generateRectangularMatrix();
            int [][] m2 = new int[m1[0].length][rand.nextInt(20)+1];

            for (int row = 0; row < m2.length; row++) {
                for (int col=0; col < m2[0].length; col++)
                    m2[row][col] = rand.nextInt();
            }

            int [][] expected = new int[m1.length][m2[0].length];

            for (int row = 0; row < m1.length; row ++) {
                for (int col = 0; col < m2[0].length; col ++) {
                    int sum = 0;
                    for (int k = 0; k < m1[0].length; k++)
                        sum += m1[row][k]*m2[k][col];
                    expected[row][col] = sum;
                }
            }
            assertArrayEquals(expected, Matrix.product(m1, m2));
        }
    }

}
