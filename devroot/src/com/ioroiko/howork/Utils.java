package com.ioroiko.howork;

import java.util.Calendar;

import android.content.Context;
import android.text.format.Time;

public class Utils {
	
	public static Stamp getStampData(Context context) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		Time now = new Time();
		now.setToNow();
		int hour = now.hour;
		int minute = now.minute;
		Stamp stamp = new Stamp(year, month, day, hour, minute);
		return stamp;
	}

}
