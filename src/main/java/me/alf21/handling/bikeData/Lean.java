package me.alf21.handling.bikeData;


public class Lean {
	
	private double fwdCOM;
	private double fwdForce;
	private double backCOM;
	private double backForce;
	private double max;
	private double fullAnim;
	private double des;
	
	public Lean(double fwdCOM, double fwdForce, double backCOM, double backForce, double max, double fullAnim, double des) {
		this.fwdCOM = fwdCOM;
		this.fwdForce = fwdForce;
		this.backCOM = backCOM;
		this.backForce = backForce;
		this.max = max;
		this.fullAnim = fullAnim;
		this.des = des;
	}
	
	/**
	 * @return LeanFwdCOM
	 */
	public double getFwdCOM() {
		return fwdCOM;
	}

	/**
	 * @return LeanFwdForce
	 */
	public double getFwdForce() {
		return fwdForce;
	}

	/**
	 * @return LeanBackCOM
	 */
	public double getBackCOM() {
		return backCOM;
	}

	/**
	 * @return LeanBackForce
	 */
	public double getBackForce() {
		return backForce;
	}

	/**
	 * @return MaxLean
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @return FullAnimLean
	 */
	public double getFullAnim() {
		return fullAnim;
	}

	/**
	 * @return DesLean
	 */
	public double getDes() {
		return des;
	}
	
}
