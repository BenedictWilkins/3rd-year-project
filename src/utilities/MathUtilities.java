package utilities;

/**
 * A utility class containing useful math functions.
 * 
 * @author Benedict Wilkins
 *
 */
public class MathUtilities {

  /**
   * Normalises x - the returned values will be between 0 and 1.
   * 
   * @param x
   *          to compute over
   * @return normalised array
   */
  public static Double[] normalise(Double[] x) {
    Double min = min(x);
    Double denom = max(x) - min;
    Double[] result = new Double[x.length];
    for (int i = 0; i < x.length; i++) {
      result[i] = (x[i] - min) / denom;
    }
    return result;
  }

  /**
   * Finds the minimum value in x
   * 
   * @param x
   *          to compute over
   * @return min
   */
  public static Double min(Double[] x) {
    Double min = x[0];
    for (Double d : x) {
      if (d < min) {
        min = d;
      }
    }
    return min;
  }

  /**
   * Finds the maximum value in x
   * 
   * @param x
   *          to compute over
   * @return max
   */
  public static Double max(Double[] x) {
    Double max = x[0];
    for (Double d : x) {
      if (d > max) {
        max = d;
      }
    }
    return max;
  }

  /**
   * Computes the range of the given set of values.
   * 
   * @param x
   *          to compute over
   * @return the range
   */
  public static Double range(Double[] x) {
    Double max = x[0];
    Double min = x[0];
    for (Double d : x) {
      if (d > max) {
        max = d;
      } else if (d < min) {
        min = d;
      }
    }
    return max - min;
  }

  /**
   * Generates a sequence of length n starting at start, and ending at end
   * (inclusive). Note: if start > end, they will be swapped.
   * 
   * @param start
   *          of the sequence
   * @param end
   *          of the sequence
   * @param length
   *          of the sequence, must be > 2
   * @return a sequence of length n of {@link Double}s from start to end
   *         inclusive
   */
  public static Double[] generateSequence(Double start, Double end, Integer n) {
    ArgumentUtilities.checkNullArgs(new Object[] { start, end, n });
    if (n < 2)
      throw new IllegalArgumentException();

    if (start > end) {
      Double t = start;
      start = end;
      end = t;
    }

    Double dif = end - start;
    Double interval = dif / (n - 1);
    Double[] r = new Double[n];
    r[0] = start;
    r[n - 1] = end;
    Double p = interval;
    for (int i = 1; i < n - 1; i++) {
      r[i] = start + p;
      p += interval;
    }
    return r;
  }

  /**
   * Element wise addition of the two supplied arrays.
   * 
   * @param x1
   *          array to add to x2
   * @param x2
   *          array to add to x1
   * @return the result of the elementwise addition.
   */
  public static Double[] add(Double[] x1, Double[] x2) {
    if (x1.length != x2.length)
      throw new IllegalArgumentException();
    Double[] r = new Double[x1.length];
    for (int i = 0; i < r.length; i++) {
      r[i] = x1[i] + x2[i];
    }
    return r;
  }

  /**
   * Adds c to all values in x.
   * 
   * @param x
   *          an array of values to add to
   * @param c
   *          a constant to add
   * @return the result of the addition
   */
  public static Double[] add(Double[] x, Double c) {
    Double[] r = new Double[x.length];
    for (int i = 0; i < r.length; i++) {
      r[i] = x[i] + c;
    }
    return r;
  }

  /**
   * Multiplies all values in x by c.
   * 
   * @param x
   *          an array of values to multiply
   * @param c
   *          a constant to multiply by
   * @return the result of the multiplication
   */
  public static Double[] multiply(Double[] x, Double c) {
    Double[] r = new Double[x.length];
    for (int i = 0; i < r.length; i++) {
      r[i] = x[i] * c;
    }
    return r;
  }

  /**
   * Combines x1 and x2 into one continuous array in the order x1,x2.
   * 
   * @param x1
   *          first array to combine
   * @param x2
   *          second array to combine
   * @return the result of combination
   */
  public static Double[] combine(Double[] x1, Double[] x2) {
    Double[] r = new Double[x1.length + x2.length];
    for (int i = 0; i < x1.length; i++) {
      r[i] = x1[i];
    }
    for (int i = 0; i < x2.length; i++) {
      r[i + x1.length] = x2[i];
    }
    return r;
  }
}
