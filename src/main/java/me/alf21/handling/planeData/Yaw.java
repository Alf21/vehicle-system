package me.alf21.handling.planeData;


public class Yaw {
	
	private double yaw, stab;
	
	public Yaw(double yaw, double stab) {
		this.yaw = yaw;
		this.stab = stab;
	}
	
	/**
	 * @return Yaw
	 */
	public double getYaw() {
		return yaw;
	}

	/**
	 * @return YawStab
	 */
	public double getStab() {
		return stab;
	}
	
}
