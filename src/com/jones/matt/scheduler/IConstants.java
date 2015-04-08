package com.jones.matt.scheduler;

/**
 *
 */
public interface IConstants
{
	public static final int kSecond = 1000;

	public static final int kMinute = kSecond *  60;

	public static final int kHour = kMinute * 60;

	public static final int kWeekly = 1;

	public static final int kWeekend = 1 << 1;

	public static final int kMonday = 1 << 2;

	public static final int kTuesday = 1 << 3;

	public static final int kWednesday = 1 << 4;

	public static final int kThursday = 1 << 5;

	public static final int kFriday = 1 << 6;

	public static final int kSaturday = 1 << 7;

	public static final int kSunday = 1 << 8;

	public static final int kSunrise = 1 << 9;

	public static final int kSunset = 1 << 10;

	/**
	 * Delay necessary between X10 commands
	 */
	public static final int kX10Delay = 2000;
}
