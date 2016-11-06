package housemodels;

import java.util.Random;

public class HouseModelFactory {

	private static HouseModelFactory factory = new HouseModelFactory();
	private Random r = new Random();
	
	private HouseModelFactory() {
	}

	public ConstantHouseModel createAffluentConstantHouseModel() {
		return createConstantHouseModel(HouseType.AFFLUENT);
	}

	public ConstantHouseModel createAdversityConstantHouseModel() {
		return createConstantHouseModel(HouseType.ADVERSITY);
	}

	public ConstantHouseModel createComfortableConstantHouseModel() {
		return createConstantHouseModel(HouseType.COMFORTABLE);
	}

	private ConstantHouseModel createConstantHouseModel(HouseType type) {
		return new ConstantHouseModel(1 + type.getMean() + r.nextGaussian()
				* type.getStandardDeviation());
	}

	public static HouseModelFactory getFactory() {
		return factory;
	}
}
