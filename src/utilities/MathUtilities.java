package utilities;

public class MathUtilities {
	
	public static Double[] NormalProbabilityDistributionFunction(Double[] x, Double mean, Double std) {
		Double da = 2*std*std;
		Double ca = 1/Math.sqrt(Math.PI*da);
		Double[] y = new Double[x.length];
		for(int i = 0; i < x.length; i++) {
			y[i] = applyPDF(x[i], ca, da, mean);
		}
		return y;
	}
	
	private static Double applyPDF(Double x, Double ca, Double da, Double mean) {
		return ca*Math.exp(-Math.pow(x-mean,2)/da);
	}
	
	/**
	 * Note that if start > end they will be swapped. 
	 * @param start
	 * @param end
	 * @param length of the sequence, must be > 2
	 * @return a sequence of length n of Doubles from start to end inclusive
	 */
	public static Double[] generateSequence(Double start, Double end, Integer n) {
		ArgumentUtilities.checkNullArgs(new Object[] { start, end, n});
		if(n < 2) throw new IllegalArgumentException();
		
		if(start > end) {
			Double t = start;
			start = end;
			end = t;
		}
		
		Double dif = end-start;
		Double interval = dif/(n-1);
		Double[] r = new Double[n];
		r[0] = start;
		r[n-1] = end;
		Double p = interval;
		for(int i = 1; i < n - 1; i++) {
			r[i] = start + p;
			p += interval;
		}
		return r;
	}
}
