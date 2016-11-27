package housemodels;

import utilities.DateTimeInterface;

public class ConstantHouseModel implements HouseModel {
	
	private Double value;
	
	public ConstantHouseModel(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getReading(DateTimeInterface dateTime) {
		return this.value;
	}
}
