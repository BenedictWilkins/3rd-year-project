package test;

import static org.junit.Assert.*;
import housemodels.ConstantHouseModel;
import housemodels.HouseModelFactory;

import org.junit.Test;

public class TestHouseModelFactory {

	@Test
	public void testSingleton() {
		assertNotNull(HouseModelFactory.getFactory());
	}
}
