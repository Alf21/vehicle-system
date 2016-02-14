package me.alf21.handling.animData;


public enum AnimData {
	STANDARD_CAR(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 0),
	LOW_CAR(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 0),
	TRUCK(2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.03, 0.23, 0.20, 0.43, 40),
	STD_BIKE(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	SPORT_BIKE(4, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	VESPA_BIKE(5, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	HARLEY_BIKE(6, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	DIRT_BIKE(7, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	WAYFARER_BIKE(8, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	BMX_BIKE(9, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	MTB_BIKE(10, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	CHOPPA_BIKE(11, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	QUAD_BIKE(12, 3, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0),
	VAN(13, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 0),
	RUSTLER(14, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 1),
	COACH(15, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 2),
	BUS(16, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 2),
	DOZER(17, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 8),
	CART(18, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 4),
	CONVERTIBLE(19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.43, 0),
	MTRUCK(20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 11.5, 0.0, -0.8, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.03, 0.23, 0.1, 0.23, 41),
	TRAIN_CARRIAGE(21, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 66),
	STD_HIGH(22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.3, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 0),
	HOVER(23, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 4),
	TANK(24, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0.25, 0.0, -0.75, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 1),
	BF_INJ(25, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.25, 0.0, -0.75, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.43, 0),
	LEAR(26, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.25, 0.0, -0.75, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 3),
	HARRIER(27, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.1, 0.23, 1),
	STD_BUT_UPRIGHT(28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.20, 0.43, 8),
	NEVADA(29, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.0, -0.5, -0.3, 0.3, 0.41, 0.8, 0.3, 0.45, 0.06, 0.43, 0.08, 0.43, 3);
	
	private int group1, group2;
	private boolean align,
					openOutF,
					openOutR,
					getInF,
					getInR,
					jack,
					closeInsF,
					closeInsR,
					shuffle,
					getOutF,
					getOutR,
					beJacked,
					closeOutF,
					closeOutR,
					jumpOut,
					closeRoll,
					fallDie,
					openLocked;
	private double 	getInTime,
					jumpOutTime,
					getOutTime,
					jkdOutTime,
					fallTime,
					openOutStart,
					openOutStop,
					closeInStart,
					closeInStop,
					openInStart,
					openInStop,
					closeOutStart,
					closeOutStop;
	private int specialFlag;
	
	AnimData(
		int group1, 
		int group2, 
		int align,
		int openOutF,
		int openOutR,
		int getInF,
		int getInR,
		int jack,
		int closeInsF,
		int closeInsR,
		int shuffle,
		int getOutF,
		int getOutR,
		int beJacked,
		int closeOutF,
		int closeOutR,
		int jumpOut,
		int closeRoll,
		int fallDie,
		int openLocked,
		double getInTime,
		double jumpOutTime,
		double getOutTime,
		double jkdOutTime,
		double fallTime,
		double openOutStart,
		double openOutStop,
		double closeInStart,
		double closeInStop,
		double openInStart,
		double openInStop,
		double closeOutStart,
		double closeOutStop,
		int specialFlag
	) {
		this.group1 = group1;
		this.group2 = group2;
		this.align = align==1?true:false;
		this.openOutF = openOutF==1?true:false;
		this.openOutR = openOutR==1?true:false;
		this.getInF = getInF==1?true:false;
		this.getInR = getInR==1?true:false;
		this.jack = jack==1?true:false;
		this.closeInsF = closeInsF==1?true:false;
		this.closeInsR = closeInsR==1?true:false;
		this.shuffle = shuffle==1?true:false;
		this.getOutF = getOutF==1?true:false;
		this.getOutR = getOutR==1?true:false;
		this.beJacked = beJacked==1?true:false;
		this.closeOutF = closeOutF==1?true:false;
		this.closeOutR = closeOutR==1?true:false;
		this.jumpOut = jumpOut==1?true:false;
		this.closeRoll = closeRoll==1?true:false;
		this.fallDie = fallDie==1?true:false;
		this.openLocked = openLocked==1?true:false;
		this.getInTime = getInTime;
		this.jumpOutTime = jumpOutTime;
		this.getOutTime = getOutTime;
		this.jkdOutTime = jkdOutTime;
		this.fallTime = fallTime;
		this.openOutStart = openOutStart;
		this.openOutStop = openOutStop;
		this.closeInStart = closeInStart;
		this.closeInStop = closeInStop;
		this.openInStart = openInStart;
		this.openInStop = openInStop;
		this.closeOutStart = closeOutStart;
		this.closeOutStop = closeOutStop;
		this.specialFlag = specialFlag;
	}
	
	/**
	 * @return group id of one of the two anim groups making up the anim group
	 */
	public int getGroup1() {
		return group1;
	}
	
	/**
	 * @return group id of the other of the two anim groups making up the anim group
	 */
	public int getGroup2() {
		return group2;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isAlign() {
		return align;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isOpenOutF() {
		return openOutF;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isOpenOutR() {
		return openOutR;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isGetInF() {
		return getInF;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isGetInR() {
		return getInR;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isJack() {
		return jack;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isCloseInsF() {
		return closeInsF;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isCloseInsR() {
		return closeInsR;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isShuffle() {
		return shuffle;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isGetOutF() {
		return getOutF;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isGetOutR() {
		return getOutR;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isBeJacked() {
		return beJacked;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isCloseOutF() {
		return closeOutF;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isCloseOutR() {
		return closeOutR;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isJumpOut() {
		return jumpOut;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isCloseRoll() {
		return closeRoll;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isFallDie() {
		return fallDie;
	}

	/**
	 * @return choose 0 to use group first group id and choose 1 to use second group id for each specific anim
	 */
	public boolean isOpenLocked() {
		return openLocked;
	}

	/**
	 * @return times to start/stop z blend
	 */
	public double getGetInTime() {
		return getInTime;
	}

	/**
	 * @return times to start/stop z blend
	 */
	public double getJumpOutTime() {
		return jumpOutTime;
	}

	/**
	 * @return times to start/stop z blend
	 */
	public double getGetOutTime() {
		return getOutTime;
	}

	/**
	 * @return times to start/stop z blend
	 */
	public double getJkdOutTime() {
		return jkdOutTime;
	}

	/**
	 * @return times to start/stop z blend
	 */
	public double getFallTime() {
		return fallTime;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getOpenOutStart() {
		return openOutStart;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getOpenOutStop() {
		return openOutStop;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getCloseInStart() {
		return closeInStart;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getCloseInStop() {
		return closeInStop;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getOpenInStart() {
		return openInStart;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getOpenInStop() {
		return openInStop;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getCloseOutStart() {
		return closeOutStart;
	}

	/**
	 * @return times to start/stop opening door
	 */
	public double getCloseOutStop() {
		return closeOutStop;
	}

	/**
	 * 1=dont close door after getting out, <br>
	 * 2=don't close door after getting in, <br>
	 * 4=use kart drive anims <br>
	 * 8=use truck drive anims <br>
	 * 16=use hover drive anims <br>
	 * 32=run special locked door <br>
	 * 64=don't open door when getting in <br>
	 * @return special flags
	 */
	public int getSpecialFlag() {
		return specialFlag;
	}
}
