package test;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.MathUtilities;

public class TestMathUtilities {

	@Test
	public void testGenerateSequence1() {
		Double[] r = MathUtilities.generateSequence(1.0, 10.0, 10);
		for(int i = 0; i < r.length; i++) {
			assertEquals((double)i+1, r[i], 0);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateSequence2() {
		Double[] r = MathUtilities.generateSequence(1.0, 2.0, 0);
	}
	
	@Test
	public void testGenerateSequence3() {
		Double[] r = MathUtilities.generateSequence(10.0, 1.0, 10);
		for(int i = 0; i < r.length; i++) {
			assertEquals((double)i+1, r[i], 0);
		}
	}
	
	
	
	

}
