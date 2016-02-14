package me.alf21.handling;


public class Suspension {

	private	double forceLevel;
	private	double dampingLevel;
	private	double highSpdComDamp;
	private	double upperLimit;
	private	double lowerLimit;
	private	double biasBetweenFrontAndRear;
	private	double antiDiveMultiplier;
	
	public Suspension(double forceLevel, double dampingLevel, double highSpdComDamp, double upperLimit, double lowerLimit, double biasBetweenFrontAndRear, double antiDiveMultiplier) {
		this.forceLevel = forceLevel;
		this.dampingLevel = dampingLevel;
		this.highSpdComDamp = highSpdComDamp;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.biasBetweenFrontAndRear = biasBetweenFrontAndRear;
		this.antiDiveMultiplier = antiDiveMultiplier;
	}
	
	/**
	 * @return fSuspensionForceLevel, not [L/M/H]
	 */
	public double getForceLevel() {
		return forceLevel;
	}
	
	/**
	 * @return fSuspensionDampingLevel, not [L/M/H]
	 */
	public double getDampingLevel() {
		return dampingLevel;
	}
	
	/**
	 * @return fSuspensionHighSpdComDamp, often zero - 200.0 or more for bouncy vehicles
	 */
	public double getHighSpdComDamp() {
		return highSpdComDamp;
	}
	
	/**
	 * @return suspension upper limit
	 */
	public double getUpperLimit() {
		return upperLimit;
	}
	
	/**
	 * @return suspension lower limit
	 */
	public double getLowerLimit() {
		return lowerLimit;
	}
	
	/**
	 * @return suspension bias between front and rear
	 */
	public double getBiasBetweenFrontAndRear() {
		return biasBetweenFrontAndRear;
	}
	
	/**
	 * @return suspension anti-dive multiplier
	 */
	public double getAntiDiveMultiplier() {
		return antiDiveMultiplier;
	}
}
