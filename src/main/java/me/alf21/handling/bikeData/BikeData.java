package me.alf21.handling.bikeData;


public class BikeData {
	
	private Lean lean;
	private double speedSteer, slipSteer;
	private double noPlayerCOMz;
	private Wheelie wheelie;
	private Stoppie stoppie;
	
	public BikeData(
			double leanFwdCOM, double leanFwdForce, double leanBakCOM, double leanBackForce, double maxLean, double fullAnimLean, double desLean,
			double speedSteer, 
			double slipSteer, 
			double noPlayerCOMz,
			double wheelieAng, 
			double stoppieAng, 
			double wheelieSteer, double wheelieStabMult, 
			double stoppieStabMult
	) {
		this.lean = new Lean(leanFwdCOM, leanFwdForce, leanBakCOM, leanBackForce, maxLean, fullAnimLean, desLean);
		this.speedSteer = speedSteer;
		this.slipSteer = slipSteer;
		this.noPlayerCOMz = noPlayerCOMz;
		this.wheelie = new Wheelie(wheelieAng, wheelieSteer, wheelieStabMult);
		this.stoppie = new Stoppie(stoppieAng, stoppieStabMult);
	}
	
	/**
	 * @return Lean
	 */
	public Lean getLean() {
		return lean;
	}

	/**
	 * @return SpeedSteer
	 */
	public double getSpeedSteer() {
		return speedSteer;
	}

	/**
	 * @return SlipSteer
	 */
	public double getSlipSteer() {
		return slipSteer;
	}

	/**
	 * @return NoPlayerCOMz
	 */
	public double getNoPlayerCOMz() {
		return noPlayerCOMz;
	}

	/**
	 * @return Wheelie
	 */
	public Wheelie getWheelie() {
		return wheelie;
	}

	/**
	 * @return Stoppie
	 */
	public Stoppie getStoppie() {
		return stoppie;
	}
}
