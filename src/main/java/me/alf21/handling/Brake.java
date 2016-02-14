package me.alf21.handling;


public class Brake {
	
	private	double deceleration;
	private	double bias;
	private	boolean ABS;
	
	public Brake(double deceleration, double bias, int ABS) {
		this.deceleration = deceleration;
		this.bias = bias;
		this.ABS = ABS==1?true:false;
	}
	
	/**
	 * @return fBrakeDeceleration, [0.1 to 10.0];
	 */
	public double getDeceleration() {
		return deceleration;
	}
	
	/**
	 * @return fBrakeBias, [0.0 > x > 1.0]
	 */
	public double getBias() {
		return bias;
	}
	
	/**
	 * @return bABS, [0/1]
	 */
	public boolean isABS() {
		return ABS;
	}
	
}
