package me.alf21.handling.bikeData;


public class Wheelie {
	
	private double ang, steer, stabMult;
	
	public Wheelie(double ang, double steer, double stabMult) {
		this.ang = ang;
		this.steer = steer;
		this.stabMult = stabMult;
	}
	
	/**
	 * @return WheelieAng
	 */
	public double getAng() {
		return ang;
	}

	/**
	 * @return WheelieSteer
	 */
	public double getSteer() {
		return steer;
	}

	/**
	 * @return WheelieStabMult
	 */
	public double getStabMult() {
		return stabMult;
	}
	
}
