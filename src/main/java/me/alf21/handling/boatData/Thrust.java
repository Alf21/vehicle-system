package me.alf21.handling.boatData;


public class Thrust {
	
	private double y, z, appZ;
	
	public Thrust(double y, double z, double appZ) {
		this.y = y;
		this.z = z;
		this.appZ = appZ;
	}
	
	/**
	 * @return thrustY
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @return thrustZ
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * @return thrustAppZ
	 */
	public double getAppZ() {
		return appZ;
	}
	
}
