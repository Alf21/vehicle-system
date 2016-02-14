package me.alf21.handling.planeData;


public class Roll {
	
	private double roll, stab;
	
	public Roll(double roll, double stab) {
		this.roll = roll;
		this.stab = stab;
	}
	
	/**
	 * @return Roll
	 */
	public double getRoll() {
		return roll;
	}

	/**
	 * @return RollStab
	 */
	public double getStab() {
		return stab;
	}
	
}
