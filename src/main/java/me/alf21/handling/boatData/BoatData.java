package me.alf21.handling.boatData;

import net.gtaun.shoebill.data.Vector3D;


public class BoatData {
	
	private Thrust thrust;
	private Aq aq;
	private double waveAudioMult;
	private Vector3D moveRes;
	private Vector3D turnRes;
	private double look_L_R_BehindCamHeight;
	
	public BoatData(
		double thrustY, double thrustZ, double thrustAppZ,
		double aqPlaneForce, double aqPlaneLimit, double aqPlaneOffset,
		double waveAudioMult,
		double moveResX, double moveResY, double moveResZ,
		double turnResX, double turnResY, double turnResZ,
		double look_L_R_BehindCamHeight
	) {
		this.thrust = new Thrust(thrustY, thrustZ, thrustAppZ);
		this.aq = new Aq(aqPlaneForce, aqPlaneLimit, aqPlaneOffset);
		this.waveAudioMult = waveAudioMult;
		this.moveRes = new Vector3D((float) moveResX, (float) moveResY, (float) moveResZ);
		this.turnRes = new Vector3D((float) turnResX, (float) turnResY, (float) turnResZ);
		this.look_L_R_BehindCamHeight = look_L_R_BehindCamHeight;
	}

	/**
	 * @return Thrust
	 */
	public Thrust getThrust() {
		return thrust;
	}

	/**
	 * @return Aq
	 */
	public Aq getAq() {
		return aq;
	}

	/**
	 * @return waveAutoMult
	 */
	public double getWaveAudioMult() {
		return waveAudioMult;
	}

	/**
	 * @return moveRes Vector
	 */
	public Vector3D getMoveRes() {
		return moveRes;
	}

	/**
	 * @return turnRes Vector
	 */
	public Vector3D getTurnRes() {
		return turnRes;
	}

	/**
	 * @return Look_L_R_BehindCamHeight
	 */
	public double getLook_L_R_BehindCamHeight() {
		return look_L_R_BehindCamHeight;
	}
	
}
