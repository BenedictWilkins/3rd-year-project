package test;

import static org.junit.Assert.*;
import housemodels.HouseTypeConstant;

import org.junit.Test;

public class TestHouseType {
	
	@Test
	public void testNonNull() {
		assertNotNull(HouseTypeConstant.ADVERSITY.getMean());
		assertNotNull(HouseTypeConstant.ADVERSITY.getModifier());
		assertNotNull(HouseTypeConstant.AFFLUENT.getMean());
		assertNotNull(HouseTypeConstant.AFFLUENT.getModifier());
		assertNotNull(HouseTypeConstant.COMFORTABLE.getMean());
		assertNotNull(HouseTypeConstant.COMFORTABLE.getModifier());
	}
}
