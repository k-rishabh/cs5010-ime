package util;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class that provides functionality for performing a Haar transform on a
 * 2D matrix of integers. Additionally, it provides functionality for inverse Haar transform
 * on similar 2D matrices of integers.
 *
 * <p>Internally, the functions convert the matrices to doubles to perform calculations with
 * a higher accuracy. They are converted back at the end.
 */
public class HaarTransform {

  /**
   * Transposes a 2D matrix of double values.
   *
   * @param matrix double values to be transposed
   * @return the transposed matrix
   */
  private static double[][] transpose(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    double[][] result = new double[cols][rows];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result[j][i] = matrix[i][j];
      }
    }
    return result;
  }

  /**
   * Performs exactly one Haar transform on a 1D array of double values.
   *
   * @param arr the array to be transformed
   */
  private static void haar1D(double[] arr) {
    int n = arr.length;
    List<Double> avg = new ArrayList<>(n / 2);
    List<Double> diff = new ArrayList<>(n / 2);

    for (int i = 0; i < n; i += 2) {
      avg.add((arr[i] + arr[i + 1]) / Math.sqrt(2));
      diff.add((arr[i] - arr[i + 1]) / Math.sqrt(2));
    }

    for (int i = 0; i < n / 2; i++) {
      arr[i] = avg.get(i);
    }

    for (int i = n / 2; i < n; i++) {
      arr[i] = diff.get(i);
    }
  }

  /**
   * Performs a Haar transform on a 2D matrix of integers. It internally changes the
   * representation to double and changes back to integers for greater accuracy.
   *
   * @param matrix the matrix on which Haar 2D transformation will happen
   * @return the transformed matrix
   */
  public static double[][] haar2D(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // set rows and cols to next power of 2
    rows = 32 - Integer.numberOfLeadingZeros(rows - 1);
    cols = cols == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(cols - 1);

    // create new padded matrix
    double[][] result = new double[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (i >= matrix.length || j >= matrix[0].length) {
          result[i][j] = 0;
        } else {
          result[i][j] = matrix[i][j];
        }
      }
    }

    int r = rows;
    int c = cols;
    while (r > 1 && c > 1) {
      // apply 1D haar transform on rows
      for (int i = 0; i < r; i++) {
        haar1D(result[i]);
      }

      // apply 1D haar transform on columns (which are now rows)
      result = transpose(result);
      for (int i = 0; i < c; i++) {
        haar1D(result[i]);
      }

      // transpose back to original orientation
      result = transpose(result);

      r /= 2;
      c /= 2;
    }

    return result;
  }

  /**
   * Performs an inverse Haar transform on an array of double values.
   *
   * @param arr the array to be transformed
   */
  private static void haar1DInverse(double[] arr) {
    int n = arr.length;
    List<Double> avg = new ArrayList<>(n / 2);
    List<Double> diff = new ArrayList<>(n / 2);

    for (int i = 0, j = n / 2; i < n / 2 && j < n; i++, j++) {
      avg.add((arr[i] + arr[j]) / Math.sqrt(2));
      diff.add((arr[i] - arr[j]) / Math.sqrt(2));
    }

    for (int i = 0, index = 0; i < n / 2; i++) {
      arr[index++] = avg.get(i);
      arr[index++] = diff.get(i);
    }
  }

  /**
   * Performs an inverse Haar transform on a 2D matrix of integers. It internally changes the
   * representation to double and changes back to integers for greater accuracy.
   *
   * @param matrix the matrix on which inverse Haar 2D transformation will happen
   * @return the transformed matrix
   */
  public static int[][] haar2DInverse(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // set rows and cols to next power of 2
    rows = 32 - Integer.numberOfLeadingZeros(rows - 1);
    cols = cols == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(cols - 1);

    // create new padded matrix
    double[][] result = new double[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (i >= matrix.length || j >= matrix[0].length) {
          result[i][j] = 0;
        } else {
          result[i][j] = matrix[i][j];
        }
      }
    }

    int r = 2;
    int c = 2;
    while (r <= rows && c <= cols) {
      // apply 1D haar transform on columns (which are now rows)
      result = transpose(result);
      for (int i = 0; i < c; i++) {
        haar1DInverse(result[i]);
      }

      // transpose back to original orientation
      result = transpose(result);

      // apply 1D haar transform on rows
      for (int i = 0; i < r; i++) {
        haar1DInverse(result[i]);
      }

      r *= 2;
      c *= 2;
    }

    // convert result to integers
    int[][] intRes = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        intRes[i][j] = (int) result[i][j];
      }
    }

    return intRes;
  }
}