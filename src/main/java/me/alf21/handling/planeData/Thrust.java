package me.alf21.handling.planeData;


public class Thrust {
	
	private double thrust, fallOff;
	
	public Thrust(double thrust, double fallOff) {
		this.thrust = thrust;
		this.fallOff = fallOff;
	}
	
	/**
	 * @return Thrust
	 */
	public double getThrust() {
		return thrust;
	}

	/**
	 * @return ThrustFallOff
	 */
	public double getFallOff() {
		return fallOff;
	}
	
}
