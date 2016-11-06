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

	@Test
	public void testConstantHouseModels() {
		HouseModelFactory f = HouseModelFactory.getFactory();
		ConstantHouseModel ad, af, co;
		assertNotNull((ad = f.createAdversityConstantHouseModel()));
		assertNotNull((af = f.createAffluentConstantHouseModel()));
		assertNotNull((co = f.createComfortableConstantHouseModel()));
		System.out.println(ad.getReading());
		System.out.println(af.getReading());
		System.out.println(co.getReading());
		assertTrue(ad.getReading() >= 0);
		assertTrue(af.getReading() >= 0);
		assertTrue(co.getReading() >= 0);
	}
}
