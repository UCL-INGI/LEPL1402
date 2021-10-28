package module1;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Array2DTest {

    //BEGIN STRIP
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
    public void testBuildFrom() {
        for(int i = 0; i < 10; i++) {
            int [][] expected = generateNonRectangularMatrix();
            String input = matrixtoStr(expected);
            int [][] result = Array2D.buildFrom(input);
            assertArrayEquals(expected, result);
        }
    }

    @Test
    public void testSum() {
        for(int i = 0; i < 10; i++) {
            int [][] matrix = generateNonRectangularMatrix();
            int sum = 0;
            for (int row = 0; row < matrix.length; row ++) {
                for (int col = 0; col < matrix[row].length; col++)
                    sum += matrix[row][col];
            }
            assertEquals(sum, Array2D.sum(matrix));
        }
    }

    @Test
    public void testTranspose() {

        for(int i = 0; i < 10; i++) {
            int [][] matrix = generateRectangularMatrix();
            int [][] expected = new int[matrix[0].length][matrix.length];
            for (int row = 0; row < matrix.length; row ++) {
                for (int col = 0; col < matrix[0].length; col++)
                    expected[col][row] = matrix[row][col];
            }
            assertArrayEquals(expected, Array2D.transpose(matrix));
        }
    }

    @Test
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
            assertArrayEquals(expected, Array2D.product(m1, m2));
        }
    }
    //END STRIP

    @Test
    public void testBuildFromStudent() {
        int [][] matrix = new int[][]{
                new int[]{1, 2, 3},
                new int[]{10, 11}
        };
        String repr = "1 2 3\n10 11";
        assertEquals(matrix, Array2D.buildFrom(repr));
    }

    @Test
    public void testSumStudent() {
        int [][] matrix = new int[][]{
                new int[]{1, 2, 3},
                new int[]{10, 11}
        };
        assertEquals(27, Array2D.sum(matrix));
    }

    @Test
    public void testTransposeStudent() {
        int [][] matrix = new int[][]{
                new int[]{1, 2, 3},
                new int[]{10, 11, 12}
        };
        int [][] transposed = new int[][]{
                new int[]{1, 10},
                new int[]{2, 11},
                new int[]{3, 12}
        };
        assertArrayEquals(transposed, Array2D.transpose(matrix));
    }

    @Test
    public void testProductStudent() {
        int [][] matrix = new int[][]{
                new int[]{1, 2, 3},
                new int[]{10, 11, 12}
        };
        int [][] mult = new int[][]{
                new int[]{4, 13},
                new int[]{5, 14},
                new int[]{6, 15}
        };

        int [][] product = new int[][]{
                new int[]{32, 86},
                new int[]{167, 464}
        };
        assertArrayEquals(product, Array2D.product(matrix, mult));
    }

}
