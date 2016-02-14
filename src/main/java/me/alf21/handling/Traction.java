package me.alf21.handling;


public class Traction {
	
	private double multiplier;
	private double loss;
	private double bias;
	
	public Traction(double multiplier, double loss, double bias) {
		this.multiplier = multiplier;
		this.loss = loss;
		this.bias = bias;
	}
	
	/**
	 * @return fTractionMultiplier, [0.5 to 2.0];
	 */
	public double getMultiplier() {
		return multiplier;
	}
	
	/**
	 * @return fTractionLoss, [0.0 > x > 1.0];
	 */
	public double getLoss() {
		return loss;
	}
	
	/**
	 * @return fTractionBias, [0.0 > x > 1.0];
	 */
	public double getBias() {
		return bias;
	}
	
}
