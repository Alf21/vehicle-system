package me.alf21.handling.planeData;


public class Pitch {
	
	private double pitch, stab;
	
	public Pitch(double pitch, double stab) {
		this.pitch = pitch;
		this.stab = stab;
	}
	
	/**
	 * @return Pitch
	 */
	public double getPitch() {
		return pitch;
	}

	/**
	 * @return PitchStab
	 */
	public double getStab() {
		return stab;
	}
}
