package me.alf21.handling.planeData;

import net.gtaun.shoebill.data.Vector3D;


public class PlaneData {
	
	private Thrust thrust;
	private Yaw yaw;
	private double sideSlip;
	private Roll roll;
	private Pitch pitch;
	private double formLift;
	private double attackLift;
	private Gear gear;
	private double windMult;
	private double moveRes;
	private Vector3D turnRes;
	private Vector3D speedRes;
	
	public PlaneData(
		double thrust, double thrustFallOff,
		double yaw, double yawStab,
		double sideSlip,
		double roll, double rollStab,
		double pitch, double pitchStab,
		double formLift,
		double attackLift,
		double gearUpR, double gearDownL,
		double windMult,
		double moveRes,
		double turnResX, double turnResY, double turnResZ,
		double speedResX, double speedResY, double speedResZ
	) {
		this.thrust = new Thrust(thrust, thrustFallOff);
		this.yaw = new Yaw(yaw, yawStab);
		this.sideSlip = sideSlip;
		this.roll = new Roll(roll, rollStab);
		this.pitch = new Pitch(pitch, pitchStab);
		this.formLift = formLift;
		this.attackLift = attackLift;
		this.gear = new Gear(gearUpR, gearDownL);
		this.windMult = windMult;
		this.moveRes = moveRes;
		this.turnRes = new Vector3D((float) turnResX, (float) turnResY, (float) turnResZ);
		this.speedRes = new Vector3D((float) speedResX, (float) speedResY, (float) speedResZ);
	}
	
	/**
	 * @return Thrust
	 */
	public Thrust getThrust() {
		return thrust;
	}

	/**
	 * @return Yaw
	 */
	public Yaw getYaw() {
		return yaw;
	}

	/**
	 * @return SideSlip
	 */
	public double getSideSlip() {
		return sideSlip;
	}

	/**
	 * @return Roll
	 */
	public Roll getRoll() {
		return roll;
	}

	/**
	 * @return Pitch
	 */
	public Pitch getPitch() {
		return pitch;
	}

	/**
	 * @return FormLift
	 */
	public double getFormLift() {
		return formLift;
	}

	/**
	 * @return AttackLift
	 */
	public double getAttackLift() {
		return attackLift;
	}

	/**
	 * @return Gear
	 */
	public Gear getGear() {
		return gear;
	}

	/**
	 * @return WindMult
	 */
	public double getWindMult() {
		return windMult;
	}

	/**
	 * @return MoveRes
	 */
	public double getMoveRes() {
		return moveRes;
	}

	/**
	 * @return TurnRes
	 */
	public Vector3D getTurnRes() {
		return turnRes;
	}

	/**
	 * @return SpeedRes
	 */
	public Vector3D getSpeedRes() {
		return speedRes;
	}
}
