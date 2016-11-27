package utilities;


public class MathUtilities {

	public static Double[] NormalProbabilityDistributionFunction(Double[] x,
			Double mean, Double std) {
		Double da = 2 * std * std;
		Double ca = 1 / Math.sqrt(Math.PI * da);
		Double[] y = new Double[x.length];
		for (int i = 0; i < x.length; i++) {
			y[i] = applyPDF(x[i], ca, da, mean);
		}
		return y;
	}

	private static Double applyPDF(Double x, Double ca, Double da, Double mean) {
		return ca * Math.exp(-Math.pow(x - mean, 2) / da);
	}
	
	public static Double min(Double[] x) {
		Double min = x[0];
		
		for(Double d : x) {
			if(d < min) {
				min = d;
			} else if(d == min){
				
			}
		}
		return min;
	}
	
	public static Double max(Double[] x) {
		Double max = x[0];
		for(Double d : x) {
			if(d > max) {
				max = d;
			}
		}
		return max;
	}
	
	public static Double range(Double[] x) {
		Double max = x[0];
		Double min = x[0];
		for(Double d : x) {
			if(d > max) {
				max = d;
			} else if(d < min) {
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
	 *            of the sequence
	 * @param end
	 *            of the sequence
	 * @param length
	 *            of the sequence, must be > 2
	 * @return a sequence of length n of {@link Double}s from start to end inclusive
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
	 *            array to add to x2
	 * @param x2
	 *            array to add to x1
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
	 *            an array of values to add to
	 * @param c
	 *            a constant to add
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
	 *            an array of values to multiply
	 * @param c
	 *            a constant to multiply by
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
	 * @param x2
	 * @return
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

	/**
	 * Creates a data distribution from a 2 normal distributions specified by
	 * the arguments. The distributions are combined and loop around the
	 * boundaries of the provided series. Each distribution should share an
	 * index among the argument arrays. for example: distributions 1 = means[0],
	 * sds[0], mult[0]. The range, size and c are shared between all
	 * distributions. The distributions are combined additively.
	 * 
	 * @param means
	 *            an array of means, 1 element per distribution
	 * @param sds
	 *            an array of standard deviations, 1 element per distribution
	 * @param mult
	 *            an array of multipliers, 1 element per distributions. The
	 *            multiplier will be used to scale the given distribution
	 * @param c
	 *            a constant that will be added to all distributions.
	 * @param range
	 *            an array containing 2 elements that specifies the start and
	 *            end of the x axis (inclusive).
	 * @param size
	 *            the number of points to generate in the function - the points
	 *            will be taking at equally sized intervals between the start
	 *            and end range values (inclusive)
	 * @return a 2-dimensional array containing the series specified from range
	 *         and size at index [0][], and the computed values associated with
	 *         the each series value at index [1][]. The data is stored in
	 *         ascending order, with [0][0] being the smallest value in the
	 *         series.
	 */
	public static Double[][] createCombinedLoopedNormalDataset(Double[] means,
			Double[] sds, Double[] mult, Double c, Double[] range, Integer size) {
		if (means.length != sds.length || means.length != mult.length
				|| means.length != range.length)
			throw new IllegalArgumentException();

		if (size % means.length != 0) {
			// TODO do something to make sure that the data divides nicely
		}
		Double[] p = MathUtilities.generateSequence(range[0] + 1 - size / 2,
				range[0], size / 2);
		Double[] a = MathUtilities.generateSequence(range[1], range[1] - 1
				+ size / 2, size / 2);

		Double[] x = MathUtilities.generateSequence(range[0], range[1], size);
		Double[] ap = MathUtilities.combine(a, p);

		Double[] n1ap = MathUtilities.NormalProbabilityDistributionFunction(ap,
				means[0], sds[0]);
		Double[] n2ap = MathUtilities.NormalProbabilityDistributionFunction(ap,
				means[1], sds[1]);

		Double[] ny1 = MathUtilities.add(MathUtilities
				.NormalProbabilityDistributionFunction(x, means[0], sds[0]),
				n1ap);
		Double[] ny2 = MathUtilities.add(MathUtilities
				.NormalProbabilityDistributionFunction(x, means[1], sds[1]),
				n2ap);
		Double[][] r = new Double[2][size];
		r[1] = MathUtilities.add(MathUtilities.add(
				MathUtilities.multiply(ny1, mult[0]),
				MathUtilities.multiply(ny2, mult[1])), c);
		r[0] = x;
		return r;
	}
}
