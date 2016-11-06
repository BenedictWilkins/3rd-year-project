package test;

import org.junit.Test;

import utilities.ArgumentUtilities;

public class TestArgumentUtilities {
	
	@Test
	public void testCheckNullArgs1() {
		ArgumentUtilities.checkNullArgs(new Object[] { new Object(), new Object() });
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckNullArgs2() {
		ArgumentUtilities.checkNullArgs(new Object[] {new Object(), null, new Object()});
	}
	
	@Test
	public void testCheckNullArgs3() {
		ArgumentUtilities.checkNullArgs(null);
	}
	
	@Test
	public void testCheckEmptyArray1() {
		ArgumentUtilities.checkEmptyArray(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckEmptyArray2() {
		ArgumentUtilities.checkEmptyArray(new Object[] {});
	}
	
	@Test
	public void testCheckEmptyArray3() {
		ArgumentUtilities.checkEmptyArray(new Object[] { new Object() });
	}
	
	

}
