package com.ioroiko.howork;

public class GlobalVars {

	//Intents
	public static String BTN_STAMP_CLICK = "com.roiko.HoWork.BTN_STAMP_CLICK";
	public static String BTN_EDIT_CLICK = "com.roiko.HoWork.BTN_EDIT_CLICK";
	public static String BTN_REFRESH_CLICK = "com.roiko.HoWork.BTN_REFRESH_CLICK";
	
	public static final String SERVICE_INTENT_TIMESTAMP_UPDATED = "SERVICE_UPDATE_TIMESTAMP";
	
	// Extra keys
	public static String STAMP_WAY = "STAMP_WAY";
	public static String EXTRA_YEAR = "EXTRA_YEAR";
	public static String EXTRA_MONTH = "EXTRA_MONTH";
	public static String EXTRA_DAY = "EXTRA_DAY";
	public static final String STAMP_STORED = "STAMP_STORED";
	public static final String STAMP_UPDATED = "STAMP_UPDATED";

	// Extra values
	public static String WAY_IN = "WAY_IN";
	public static String WAY_OUT = "WAY_OUT";

	public enum WriteStampRes {
		Ok, DBClosed, GenericError, ExceptionThrown

	}

	public enum ReadTodayTimeStamps {
		Ok, DBClosed, GenericError, ExceptionThrown

	}

	public enum Way {
		IN, OUT
	}

}
