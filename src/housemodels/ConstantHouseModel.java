package housemodels;

public class ConstantHouseModel implements HouseModel {
	
	private Double value;
	
	public ConstantHouseModel(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getReading() {
		return this.value;
	}
}
