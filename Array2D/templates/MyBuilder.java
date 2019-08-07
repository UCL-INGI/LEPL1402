package templates;

import src.Array2DBuilderInterface;

public class MyBuilder implements Array2DBuilderInterface {
  public int[][] build_from(String s) {
    @@build_from@@
  }
  public int sum(int[][] array) {
      @@sum@@
  }

  public int[][] transpose(int[][] matrix) {
      @@transpose@@
  }
  public int[][] product(int[][] matrix1, int[][] matrix2) {
    @@product@@
  }
}
