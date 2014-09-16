package com.ioroiko.howork;

public class GlobalVars {

	//Intents
	public static String BTN_STAMP_CLICK = "com.roiko.HoWork.BTN_STAMP_CLICK";
	public static String BTN_EDIT_CLICK = "com.roiko.HoWork.BTN_EDIT_CLICK";
	
	// Extra keys
	public static String STAMP_WAY = "STAMP_WAY";
	public static String DAY_TO_EDIT = "DAY_TO_EDIT";

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
