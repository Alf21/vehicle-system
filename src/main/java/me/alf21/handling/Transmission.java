package me.alf21.handling;



public class Transmission {

	private	int numberOfGears;
	private	double maxVelocity;
	private	double engineAcceleration;
	private	double engineInertia;
	private DriveType driveType;
	private EngineType engineType;
	
	public Transmission(int numberOfGears, double maxVelocity, double engineAcceleration, double engineInertia, DriveType driveType, EngineType engineType) {
		this.numberOfGears = numberOfGears;
		this.maxVelocity = maxVelocity;
		this.engineAcceleration = engineAcceleration;
		this.engineInertia = engineInertia;
		this.driveType = driveType;
		this.engineType = engineType;
	}
	
	/**
	 * @return TransmissionData.nNumberOfGears, [1 to 4]
	 */
	public int getNumberOfGears() {
		return numberOfGears;
	}
	
	/**
	 * @return TransmissionData.fMaxVelocity, [5.0 to 150.0]
	 */
	public double getMaxVelocity() {
		return maxVelocity;
	}
	
	/**
	 * @return TransmissionData.fEngineAcceleration, [0.1 to 10.0]
	 */
	public double getEngineAcceleration() {
		return engineAcceleration;
	}
	
	/**
	 * @return TransmissionData.fEngineInertia, [0.0 to 50.0]
	 */
	public double getEngineInertia() {
		return engineInertia;
	}
	
	/**
	 * engine drive :- (F)ront, (R)ear, (4)-wheel drive
	 * @return TransmissionData.nDriveType, [F/R/4]
	 */
	public DriveType getDriveType() {
		return driveType;
	}
	
	/**
	 * engine type :- (P)etrol, (D)iesel, (E)lectric
	 * @return TransmissionData.nEngineType, [P/D/E];
	 */
	public EngineType getEngineType() {
		return engineType;
	}
}
