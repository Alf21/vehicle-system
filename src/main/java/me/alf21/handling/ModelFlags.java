package me.alf21.handling;

import java.math.BigInteger;


public class ModelFlags {
	
	private int digit1, digit2, digit3, digit4, digit5, digit6, digit7, digit8;

	/**
	 * @param hex the hexadecimal
	 */
	public ModelFlags(String hex) {
		BigInteger decimal = new BigInteger(hex, 16);
		digit1 = (decimal.intValue() & 0x0f);
		digit2 = (decimal.intValue() & 0xf0) >> 4;
		digit3 = (decimal.intValue() & 0xf00) >> 8;
		digit4 = (decimal.intValue() & 0xf000) >> 12;
		digit5 = (decimal.intValue() & 0xf0000) >> 16;
		digit6 = (decimal.intValue() & 0xf00000) >> 20;
		digit7 = (decimal.intValue() & 0xf000000) >> 24;
		digit8 = (decimal.intValue() & 0xf0000000) >> 28;
	}
	

	
	/**
	 * @return 1st digit	1: IS_VAN			2: IS_BUS			4: IS_LOW				8: IS_BIG
	 */
	public int getDigit1() {
		return digit1;
	}

	/**
	 * @return 2nd digit	1: REVERSE_BONNET	2: HANGING_BOOT		4: TAILGATE_BOOT		8: NOSWING_BOOT
	 */
	public int getDigit2() {
		return digit2;
	}

	/**
	 * @return 3rd digit	1: NO_DOORS			2: TANDEM_SEATS		4: SIT_IN_BOAT			8: CONVERTIBLE
	 */
	public int getDigit3() {
		return digit3;
	}

	/**
	 * @return 4th digit	1: NO_EXHAUST		2: DOUBLE_EXHAUST	4: NO1FPS_LOOK_BEHIND	8: FORCE_DOOR_CHECK
	 */
	public int getDigit4() {
		return digit4;
	}

	/**
	 * @return 5th digit	1: AXLE_F_NOTILT	2: AXLE_F_SOLID		4: AXLE_F_MCPHERSON		8: AXLE_F_REVERSE
	 */
	public int getDigit5() {
		return digit5;
	}

	/**
	 * @return 6th digit	1: AXLE_R_NOTILT	2: AXLE_R_SOLID		4: AXLE_R_MCPHERSON		8: AXLE_R_REVERSE
	 */
	public int getDigit6() {
		return digit6;
	}

	/**
	 * @return 7th digit	1: IS_BIKE			2: IS_HELI			4: IS_PLANE				8: IS_BOAT
	 */
	public int getDigit7() {
		return digit7;
	}

	/**
	 * @return 8th digit	1: BOUNCE_PANELS	2: DOUBLE_RWHEELS	4: FORCE_GROUND_CLEARANCE	8: IS_HATCHBACK
	 */
	public int getDigit8() {
		return digit8;
	}
	
}
