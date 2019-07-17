package src;

public interface Array2DBuilderInterface {
    // from a String of int, build and return a array of 2D
    // integers are separated by spaces ; lines by the \n character
    // As array 2d aren't always regular matrix, you may build a Irregular matrix with the given input
    // Example :
    //      String s = "1 2 3 \n 4 5 \n 42"; 
    // Gives :
    //      int[][] array2d = { {1, 2, 3}, {4, 5}, {42} };
    public int[][] build_from(String s);
    
    // Compute the sum of all integers in the 2d array (not necessarily a regular matrix )
    public int sum(int[][] array);

    // return the transpose of the matrix (the given parameter is a regular matrix)
    public int[][] transpose(int[][] matrix);

    // return the product of : matrix1 X matrix2
    // (row1 X column1) X (row2 X column2) doesn't meet that row1 and column2 are the same
    public int[][] product(int[][] matrix1, int[][] matrix2);
}