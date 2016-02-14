package me.alf21.handling.bikeData;


public class Stoppie {
	
	private double ang, stabMult;
	
	public Stoppie(double ang, double stabMult) {
		this.ang = ang;
		this.stabMult = stabMult;
	}
	
	/**
	 * @return StoppieAng
	 */
	public double getAng() {
		return ang;
	}

	/**
	 * @return StoppieStabMult
	 */
	public double getStabMult() {
		return stabMult;
	}
	
}
