package housemodels;

public enum HouseTypeConstant {

	/**
	 * Note that the values given to each type is the a mean value calculated
	 * from a sample data set containing 1 millions entries (KWH per half hour
	 * readings). The mean was taken by grouping different house holds based on
	 * their ACORN group. The households ACORN group was given in the data set.
	 * https://data.london.gov.uk/dataset/smartmeter-energy-use-data-in-london-
	 * households Data set 1 from the separated sets.
	 */
	AFFLUENT(0.27798, 0.46668), COMFORTABLE(0.26464, 0.27996), ADVERSITY(0.17629, 0.30267);

	/**
	 * Note that the modifier provides a nice way to scale some generated sample
	 * data depending on the type of the house.
	 */
	static {
		COMFORTABLE.setModifier(1.0);
		AFFLUENT.setModifier(AFFLUENT.getMean() / COMFORTABLE.getMean());
		ADVERSITY.setModifier(ADVERSITY.getMean() / COMFORTABLE.getMean());
	}

	private Double standardDeviation = null;
	private Double mean = null;
	private Double modifier = null;

	private HouseTypeConstant(Double mean, Double standardDeviation) {
		this.setMean(mean);
		this.setStandardDeviation(standardDeviation);
	}

	public Double getMean() {
		return mean;
	}

	public void setMean(Double mean) {
		this.mean = mean;
	}

	public Double getModifier() {
		return modifier;
	}

	public void setModifier(Double modifier) {
		this.modifier = modifier;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

}
