package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class that provides functionality for performing a Haar transform on a
 * 2D matrix of integers. Additionally, it provides functionality for inverse Haar transform
 * on similar 2D matrices of integers.
 * <p> Internally, the functions convert the matrices to doubles to perform calculations with
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
      arr[i] = diff.get(i - n / 2);
    }
  }

  /**
   * Performs a Haar transform on a 2D matrix of integers. It internally changes the
   * representation to double and changes back to integers for greater accuracy.
   *
   * @param matrix the matrix on which Haar 2D transformation will happen
   * @return the transformed matrix
   */
  private static double[][] haar2D(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // set rows and cols to next power of 2
    int r = 1;
    int c = 1;

    while (r < rows) {
      r = r << 1;
    }
    while (c < cols) {
      c = c << 1;
    }

    rows = r;
    cols = c;

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
      result = transpose(result);

      // next iteration
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
   * Assumes that the matrix has dimensions that are a power of 2.
   *
   * @param matrix the matrix on which inverse Haar 2D transformation will happen
   * @return the transformed matrix
   */
  private static double[][] haar2DInverse(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    int r = 2;
    int c = 2;
    while (r <= rows && c <= cols) {
      // apply 1D haar transform on columns (which are now rows)
      matrix = transpose(matrix);
      for (int i = 0; i < c; i++) {
        haar1DInverse(matrix[i]);
      }
      matrix = transpose(matrix);

      // apply 1D haar transform on rows
      for (int i = 0; i < r; i++) {
        haar1DInverse(matrix[i]);
      }

      r *= 2;
      c *= 2;
    }

    return matrix;
  }

  /**
   * Converts a 2D matrix of integers to a doubles matrix.
   *
   * @param matrix the integer matrix to be converted
   * @return the resulting double matrix
   */
  private static double[][] convertIntToDouble(int[][] matrix) {
    double[][] result = new double[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        result[i][j] = matrix[i][j];
      }
    }

    return result;
  }

  /**
   * Applies all necessary Haar transformations in order, to compress a 2D matrix of
   * integers. It uses the ratio for lossy compression. Ratio represents the fraction of non-zero
   * values before and after thresholding.
   *
   * @param initialMatrix the matrix to be compressed
   * @param ratio         the compression ratio, between 0 and 100
   * @return the compressed matrix
   */
  public static int[][] compressMatrix(int[][] initialMatrix, int ratio) {
    int h = initialMatrix.length;
    int w = initialMatrix[0].length;

    double[][] matrix = convertIntToDouble(initialMatrix);

    // haar 2d transform
    matrix = haar2D(matrix);

    // get all values
    Set<Double> allValues = new HashSet<>();
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        allValues.add(Math.abs(matrix[i][j]));
      }
    }

    // sort all values
    List<Double> sortedValues = new ArrayList<>(allValues.size());
    sortedValues.addAll(allValues);
    Collections.sort(sortedValues);

    // calculate threshold
    int termsToZero = (int) Math.round(allValues.size() * ratio / 100.0);
    int threshold = (int) Math.round(sortedValues.get(termsToZero));

    // thresholding
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (Math.abs(matrix[i][j]) <= threshold) {
          matrix[i][j] = 0;
        }
      }
    }

    // inverse haar 2d transform
    matrix = haar2DInverse(matrix);

    // un-padding matrix
    int[][] res = new int[h][w];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        res[i][j] = (int) Math.round(matrix[i][j]);
      }
    }

    return res;
  }
}