package me.alf21.handling;

import java.math.BigInteger;


public class HandlingFlags {
	
	private int digit1, digit2, digit3, digit4, digit5, digit6, digit7, digit8;
	
	/**
	 * @param hex the hexadecimal
	 */
	public HandlingFlags(String hex) {
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
	 * @return 1st digit	1: 1G_BOOST			2: 2G_BOOST			4: NPC_ANTI_ROLL		8: NPC_NEUTRAL_HANDL
	 */
	public int getDigit1() {
		return digit1;
	}

	/**
	 * @return 2nd digit	1: NO_HANDBRAKE		2: STEER_REARWHEELS 4: HB_REARWHEEL_STEER	8: ALT_STEER_OPT
	 */
	public int getDigit2() {
		return digit2;
	}

	/**
	 * @return 3rd digit	1: WHEEL_F_NARROW2	2: WHEEL_F_NARROW	4: WHEEL_F_WIDE			8: WHEEL_F_WIDE2
	 */
	public int getDigit3() {
		return digit3;
	}

	/**
	 * @return 4th digit	1: WHEEL_R_NARROW2	2: WHEEL_R_NARROW	4: WHEEL_R_WIDE			8: WHEEL_R_WIDE2
	 */
	public int getDigit4() {
		return digit4;
	}

	/**
	 * @return 5th digit	1: HYDRAULIC_GEOM 	2: HYDRAULIC_INST	4: HYDRAULIC_NONE		8: NOS_INST
	 */
	public int getDigit5() {
		return digit5;
	}

	/**
	 * @return 6th digit	1: OFFROAD_ABILITY	2: OFFROAD_ABILITY2	4: HALOGEN_LIGHTS		8: PROC_REARWHEEL_1ST
	 */
	public int getDigit6() {
		return digit6;
	}

	/**
	 * @return 7th digit	1: USE_MAXSP_LIMIT	2: LOW_RIDER		4: STREET_RACER
	 */
	public int getDigit7() {
		return digit7;
	}

	/**
	 * @return 8th digit	1: SWINGING_CHASSIS
	 */
	public int getDigit8() {
		return digit8;
	}
	
}
