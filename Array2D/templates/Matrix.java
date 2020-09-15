package templates;
public class Matrix {

    /**
     * Create a matrix from a String.
     *
     * The string if formatted as follow:
     *  - Each row of the matrix is separated by a newline
     *    character \n
     *  - Each element of the rows are separated by a space
     *
     *  For example, the String "0 1\n 2 3" represent the
     *  matrix:
     *      [0 1]
     *      [2 3]
     *
     *  @param s The input String
     *  @return The matrix represented by the String
     */
    public static int[][] buildFrom(String s) {
        @@build_from@@
    }

    /**
     * Compute the sum of the element in a matrix
     *
     * @param matrix The input matrix
     * @return The sum of the element in matrix
     */
    public static int sum(int[][] matrix) {
        @@sum@@
    }

    /**
     * Compute the transpose of a matrix
     *
     * @param matrix The input matrix
     * @return A new matrix that is the transpose of matrix
     */
    public static int[][] transpose(int[][] matrix) {
        @@transpose@@
    }

    /**
     * Compute the product of two matrix
     *
     * @param matrix1 A n x m matrix
     * @param matrix2 A m x k matrix
     * @return The n x k matrix product of matrix1 and matrix2
     */
    public static int[][] product(int[][] matrix1, int[][] matrix2) {
        @@product@@
    }
}
