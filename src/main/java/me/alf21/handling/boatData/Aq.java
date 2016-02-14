package me.alf21.handling.boatData;


public class Aq {
	
	private double planeForce, planeLimit, planeOffset;
	
	public Aq(double planeForce, double planeLimit, double planeOffset) {
		this.planeForce = planeForce;
		this.planeLimit = planeLimit;
		this.planeOffset = planeOffset;
	}
	
	/**
	 * @return AqPlaneForce
	 */
	public double getPlaneForce() {
		return planeForce;
	}
	
	/**
	 * @return AqPlaneLimit
	 */
	public double getPlaneLimit() {
		return planeLimit;
	}
	
	/**
	 * @return AqPlaneOffset
	 */
	public double getPlaneOffset() {
		return planeOffset;
	}
	
}
