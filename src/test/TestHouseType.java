package test;

import static org.junit.Assert.*;
import housemodels.HouseType;

import org.junit.Test;

public class TestHouseType {
	
	@Test
	public void testNonNull() {
		assertNotNull(HouseType.ADVERSITY.getMean());
		assertNotNull(HouseType.ADVERSITY.getModifier());
		assertNotNull(HouseType.AFFLUENT.getMean());
		assertNotNull(HouseType.AFFLUENT.getModifier());
		assertNotNull(HouseType.COMFORTABLE.getMean());
		assertNotNull(HouseType.COMFORTABLE.getModifier());
	}
}
