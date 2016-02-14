package me.alf21.handling;

import me.alf21.handling.animData.AnimData;
import me.alf21.handling.bikeData.BikeData;
import me.alf21.handling.boatData.BoatData;
import me.alf21.handling.planeData.PlaneData;
import net.gtaun.shoebill.data.Vector3D;

public class Handling {
		private String name;
		private VehicleType vehicleType;
		private double mass;
		private	double turnmass; 
		private	double drag; 
		private	Vector3D centreOfMass; 
		private	int boy; 
		private	Traction traction;
		private Transmission transmission;
		private Brake brake;
		private	double steeringLock;
		private Suspension suspension;
		private double seat;
		private double col;
		private int cost;
		private	String modelFlagsHEX;
		private	String handlingFlagsHEX;
		private	int frontLights;
		private	int rearLights;
		private	AnimData animData;
		private BoatData boatData;
		private BikeData bikeData;
		private PlaneData planeData;
		
	/**
	 * @param name vehicle identifier [14 characters max]
	 * @param vehicleType type of the vehicle
	 * @param mass fMass, [1.0 to 50000.0]
	 * @param turnmass fTurnMass, //was////Dimensions.x [0.0 > x > 20.0]
	 * @param drag fDragMult, //was////Dimensions.y [0.0 > x > 20.0]
	 * @param centreOfMassX CentreOfMass.x, [-10.0 > x > 10.0]
	 * @param centreOfMassY CentreOfMass.y, [-10.0 > y > 10.0]
	 * @param centreOfMassZ CentreOfMass.z, [-10.0 > z > 10.0]
	 * @param boy nPercentSubmerged,	[10 to 120]
	 * @param tractionMultiplier fTractionMultiplier, [0.5 to 2.0]
	 * @param tractionLoss fTractionLoss, [0.0 > x > 1.0]
	 * @param tractionBias fTractionBias, [0.0 > x > 1.0]
	 * @param transmissionNumberOfGears TransmissionData.nNumberOfGears, [1 to 4]
	 * @param transmissionMaxVelocity TransmissionData.fMaxVelocity, [5.0 to 150.0]
	 * @param transmissionEngineAcceleration TransmissionData.fEngineAcceleration, [0.1 to 10.0]
	 * @param transmissionEngineInertia TransmissionData.fEngineInertia,	[0.0 to 50.0]
	 * @param driveType TransmissionData.nDriveType,	[F/R/4 (FOUR)]
	 * @param engineType TransmissionData.nEngineType, [P/D/E]
	 * @param brakeDeceleration fBrakeDeceleration, [0.1 to 10.0]
	 * @param brakeBias fBrakeBias, [0.0 > x > 1.0]
	 * @param bABS bABS, [0/1]
	 * @param steeringLock fSteeringLock, [10.0 to 40.0]
	 * @param suspensionForceLevel  fSuspensionForceLevel, not [L/M/H]
	 * @param suspensionDampingLevel fSuspensionDampingLevel, not [L/M/H]
	 * @param suspensionHighSpdComDamp fSuspensionHighSpdComDamp, often zero - 200.0 or more for bouncy vehicles
	 * @param suspensionUpperLimit suspension upper limit
	 * @param suspensionLowerLimit suspension lower limit
	 * @param suspensionBiasBetweenFrontAndRear suspension bias between front and rear
	 * @param suspensionAntiDiveMultiplier suspension anti-dive multiplier
	 * @param seat fSeatOffsetDistance, // ped seat position offset towards centre of car
	 * @param col fCollisionDamageMultiplier, [0.2 to 5.0]
	 * @param cost nMonetaryValue, [1 to 100000]
	 * @param modelFlagsHEX modelFlags!!!  WARNING - Now written HEX for easier reading of flags
	 * @param handlingFlagsHEX handlingFlags - written in HEX
	 * @param frontLights front lights (0 = long, 1 = small, 2 = big, 3 = tall)
	 * @param rearLights rear lights (0 = long, 1 = small, 2 = big, 3 = tall)
	 * @param vehicleAnimGroup vehicle anim group (based on enum values of AnimData)
	 */
	public Handling(
			String name,
			VehicleType vehicleType,
			double mass, 
			double turnmass, 
			double drag, 
			double centreOfMassX, double centreOfMassY, double centreOfMassZ, 
			int boy, 
			double tractionMultiplier, double tractionLoss, double tractionBias,
			int transmissionNumberOfGears, double transmissionMaxVelocity, double transmissionEngineAcceleration, double transmissionEngineInertia, DriveType driveType, EngineType engineType,
			double brakeDeceleration, double brakeBias, int bABS,
			double steeringLock,
			double suspensionForceLevel, double suspensionDampingLevel, double suspensionHighSpdComDamp, double suspensionUpperLimit, double suspensionLowerLimit, double suspensionBiasBetweenFrontAndRear, double suspensionAntiDiveMultiplier,
			double seat,
			double col,
			int cost,
			String modelFlagsHEX,
			String handlingFlagsHEX,
			int frontLights,
			int rearLights,
			int vehicleAnimGroup
		) {
		this.name = name;
		this.vehicleType = vehicleType;
		this.mass = mass;
		this.turnmass = turnmass;
		this.drag = drag;
		this.centreOfMass = new Vector3D((float) centreOfMassX, (float) centreOfMassY, (float) centreOfMassZ);
		this.boy = boy;
		this.traction = new Traction(tractionMultiplier, tractionLoss, tractionBias);
		this.transmission = new Transmission(
				transmissionNumberOfGears, 
				transmissionMaxVelocity, 
				transmissionEngineAcceleration,
				transmissionEngineInertia,
				driveType,
				engineType
		);
		this.brake = new Brake(brakeDeceleration, brakeBias, bABS);
		this.steeringLock = steeringLock;
		this.suspension = new Suspension(
				suspensionForceLevel, 
				suspensionDampingLevel, 
				suspensionForceLevel, 
				suspensionUpperLimit, 
				suspensionLowerLimit, 
				suspensionBiasBetweenFrontAndRear, 
				suspensionAntiDiveMultiplier
		);
		this.seat = seat;
		this.col = col;
		this.cost = cost;
		this.modelFlagsHEX = modelFlagsHEX;
		this.handlingFlagsHEX = handlingFlagsHEX;
		this.frontLights = frontLights;
		this.rearLights = rearLights;
		int i = 0;
		for(AnimData animData : AnimData.values()) {
			if(i == vehicleAnimGroup) {
				this.animData = animData;
				break;
			}
			i++;
		}
	}
	
	/**
	 * @return vehicle identifier [14 characters max]
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the type of vehicle
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	/**
	 * @return fMass [1.0 to 50000.0]
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * @return fTurnMass //was////Dimensions.x [0.0 > x > 20.0]
	 */
	public double getTurnmass() {
		return turnmass;
	}
	
	/**
	 * @return fDragMult //was////Dimensions.y [0.0 > y > 20.0]
	 */
	public double getDrag() {
		return drag;
	}
	
	/**
	 * @return CentreOfMass [-10.0 > x,y,z > 10.0]
	 */
	public Vector3D getCentreOfMass() {
		return centreOfMass;
	}
	
	/**
	 * @return nPercentSubmerged [10 to 120]
	 */
	public int getBoy() {
		return boy;
	}
	
	/**
	 * @return traction
	 */
	public Traction getTraction() {
		return traction;
	}
	
	/**
	 * @return transmission
	 */
	public Transmission getTransmission() {
		return transmission;
	}
	
	/**
	 * @return brake
	 */
	public Brake getBrake() {
		return brake;
	}
	
	/**
	 * @return fSteeringLock [10.0 to 40.0];
	 */
	public double getSteeringLock() {
		return steeringLock;
	}
	
	/**
	 * @return suspension
	 */
	public Suspension getSuspension() {
		return suspension;
	}
	
	/**
	 * @return fSeatOffsetDistance, // ped seat position offset towards centre of car
	 */
	public double getSeat() {
		return seat;
	}
	
	/**
	 * @return fCollisionDamageMultiplier, [0.2 to 5.0]
	 */
	public double getCol() {
		return col;
	}
	
	/**
	 * @return nMonetaryValue, [1 to 100000]
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * 1st digit	1: IS_VAN			2: IS_BUS			4: IS_LOW				8: IS_BIG<br>
	 * 2nd digit	1: REVERSE_BONNET	2: HANGING_BOOT		4: TAILGATE_BOOT		8: NOSWING_BOOT<br>
	 * 3rd digit	1: NO_DOORS			2: TANDEM_SEATS		4: SIT_IN_BOAT			8: CONVERTIBLE<br>
	 * 4th digit	1: NO_EXHAUST		2: DOUBLE_EXHAUST	4: NO1FPS_LOOK_BEHIND	8: FORCE_DOOR_CHECK<br>
	 * 5th digit	1: AXLE_F_NOTILT	2: AXLE_F_SOLID		4: AXLE_F_MCPHERSON		8: AXLE_F_REVERSE<br>
	 * 6th digit	1: AXLE_R_NOTILT	2: AXLE_R_SOLID		4: AXLE_R_MCPHERSON		8: AXLE_R_REVERSE<br>
	 * 7th digit	1: IS_BIKE			2: IS_HELI			4: IS_PLANE				8: IS_BOAT<br>
	 * 8th digit	1: BOUNCE_PANELS	2: DOUBLE_RWHEELS	4: FORCE_GROUND_CLEARANCE	8: IS_HATCHBACK<br>
	 * @return modelFlags!!!  WARNING - Now written HEX for easier reading of flags
	 */
	public String getModelFlagsHEX() {
		return modelFlagsHEX;
	}
	
	/**
	 * 1st digit	1: 1G_BOOST			2: 2G_BOOST			4: NPC_ANTI_ROLL		8: NPC_NEUTRAL_HANDL<br>
	 * 2nd digit	1: NO_HANDBRAKE		2: STEER_REARWHEELS 4: HB_REARWHEEL_STEER	8: ALT_STEER_OPT<br>
	 * 3rd digit	1: WHEEL_F_NARROW2	2: WHEEL_F_NARROW	4: WHEEL_F_WIDE			8: WHEEL_F_WIDE2<br>
	 * 4th digit	1: WHEEL_R_NARROW2	2: WHEEL_R_NARROW	4: WHEEL_R_WIDE			8: WHEEL_R_WIDE2<br>
	 * 5th digit	1: HYDRAULIC_GEOM 	2: HYDRAULIC_INST	4: HYDRAULIC_NONE		8: NOS_INST<br>
	 * 6th digit	1: OFFROAD_ABILITY	2: OFFROAD_ABILITY2	4: HALOGEN_LIGHTS		8: PROC_REARWHEEL_1ST<br>
	 * 7th digit	1: USE_MAXSP_LIMIT	2: LOW_RIDER		4: STREET_RACER<br>
	 * 8th digit	1: SWINGING_CHASSIS<br>
	 * @return handlingFlags - written in HEX
	 */
	public String getHandlingFlagsHEX() {
		return handlingFlagsHEX;
	}
	
	/**
	 * @return front lights (0 = long, 1 = small, 2 = big, 3 = tall)
	 */
	public int getFrontLights() {
		return frontLights;
	}
	
	/**
	 * @return rear lights (0 = long, 1 = small, 2 = big, 3 = tall)
	 */
	public int getRearLights() {
		return rearLights;
	}
	
	/**
	 * @return vehicle anim data (instead of the vehicle's anim group)
	 */
	public AnimData getVehAnimData() {
		return animData;
	}
	
	/**
	 * set the boat data
	 * @param boatData the boatData
	 */
	public void setBoatData(BoatData boatData) {
		this.boatData = boatData;
	}
	
	/**
	 * @return if its not a boat or "SEAPLANE" the returned value will be NULL
	 */
	public BoatData getBoatData() {
		return boatData;
	}
	
	/**
	 * set the bike data
	 * @param bikeData the bikeData
	 */
	public void setBikeData(BikeData bikeData) {
		this.bikeData = bikeData;
	}
	
	/**
	 * @return if its not a bike the returned value will be NULL
	 */
	public BikeData getBikeData() {
		return bikeData;
	}
	
	/**
	 * set the plane data
	 * @param planeData the planeData
	 */
	public void setPlaneData(PlaneData planeData) {
		this.planeData = planeData;
	}
	
	/**
	 * @return if its not a plane the returned value will be NULL
	 */
	public PlaneData getPlaneData() {
		return planeData;
	}
}
