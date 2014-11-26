package com.ioroiko.howork;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.ioroiko.howork.SummaryAdapter.DayStamp;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

public class Utils {
	
	public static ArrayList<Integer> GetDaysOfMonth(Context c, int year, int month)
	{
		ArrayList<Integer> days = new ArrayList<Integer>();
		Calendar calendar = new GregorianCalendar(year, month-1,1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= maxDay; i++) {
			days.add(i);
		}
		
		return days;
	}

	public static Stamp getTodayAsStamp(Context context) {
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

	public static List<DayStamp> ConvertStampArrayListToListDayStamp(
			ArrayList<Stamp> aa) {
		List<DayStamp> list = new LinkedList<DayStamp>();
		if (!aa.isEmpty()) {

			int day = -1;
			DayStamp tempDayStamp = new DayStamp();
			for (Stamp stamp : aa) {
				if (day == -1) {
					day = stamp.day;
					tempDayStamp = new DayStamp();
					tempDayStamp.Day = stamp.day;
					tempDayStamp.Month = stamp.month;
					tempDayStamp.Year = stamp.year;
				}
				if (day == stamp.day) {
					// Sto collezionando gli stamp di questo giorno
					tempDayStamp.Stamps.add(stamp);
				}
				if (day != stamp.day) {
					// Ricado qui se, ciclando, ho cambiato giorno. Quindi
					// aggiungo
					// DayStamp alla lista,
					// altrimenti continuo ad aggiungere stamp a DayStamp
					list.add(tempDayStamp);
					day = stamp.day;
					tempDayStamp = new DayStamp();
					tempDayStamp.Day = stamp.day;
					tempDayStamp.Month = stamp.month;
					tempDayStamp.Year = stamp.year;
					tempDayStamp.Stamps.add(stamp);
				}
			}
			list.add(tempDayStamp);// Aggiungo l'ultimo giorno
		}

		return list;
	}

	public static String GetDayNameFromDate(Context c, int day, int month,
			int year) {
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
				.getInstance();
		calendar.set(year, month - 1, day);
		int dayI = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		return GetDayOfWeekNameFromint(c, dayI);
	}

	private static String GetDayOfWeekNameFromint(Context c, int dayI) {
		switch (dayI) {
		case 1:
			return c.getResources().getString(R.string.SU);

		case 2:
			return c.getResources().getString(R.string.MO);

		case 3:
			return c.getResources().getString(R.string.TU);

		case 4:
			return c.getResources().getString(R.string.WE);

		case 5:
			return c.getResources().getString(R.string.TH);

		case 6:
			return c.getResources().getString(R.string.FR);

		case 7:
			return c.getResources().getString(R.string.SA);

		default:
			return "";
		}
	}

	public static String GetMonthNameFromDate(Context c, int month) {
		return GetMonthNameFromInt(c, month);
	}
	
	public static boolean StampsAreEqual(Stamp s1, Stamp s2)
	{
		if(s1.day!=s2.day)
			return false;
		if (s1.month!=s2.month)
			return false;
		if (s1.year!=s2.year)
			return false;
		if (s1.minute!=s2.minute)
			return false;
		if (s1.hour!=s2.hour)
			return false;
		if (s1.way!=s2.way)
			return false;
		return true;
	}

	private static String GetMonthNameFromInt(Context c, int month) {
		switch (month) {
		case 1:
			return c.getResources().getString(R.string.JAN);
		case 2:
			return c.getResources().getString(R.string.FEB);
		case 3:
			return c.getResources().getString(R.string.MAR);
		case 4:
			return c.getResources().getString(R.string.APR);
		case 5:
			return c.getResources().getString(R.string.MAY);
		case 6:
			return c.getResources().getString(R.string.JUN);
		case 7:
			return c.getResources().getString(R.string.JUL);
		case 8:
			return c.getResources().getString(R.string.AUG);
		case 9:
			return c.getResources().getString(R.string.SEP);
		case 10:
			return c.getResources().getString(R.string.OCT);
		case 11:
			return c.getResources().getString(R.string.NOV);
		case 12:
			return c.getResources().getString(R.string.DEC);
		default:
			return "";
		}

	}
	
	
	/**
	 * 
	 * @param time a time written as HH:mm
	 * @return a long (representing minutes)
	 */
	public static long TimeToLongAsMinutes(String time)
	{
		long longTime = 0;
		try{
		long hours = Long.parseLong(time.split(":")[0]) * 60;
		long minutes = Long.parseLong(time.split(":")[1]);
		longTime = hours + minutes;
		}
		catch (Exception e)
		{
			Log.e("TimeToLongAsMinutes", "exception in try to convert a time HH:mm to long! " + e.getMessage());
		}
		
		return longTime;
	}
	
	
	public static float TimeToLongAsHours(String time)
	{
		float longTime = 0;
		try{
		float hours = Float.parseFloat(time.split(":")[0]);
		float minutes = Float.parseFloat(time.split(":")[1]) / 60;
		longTime = hours + minutes;
		}
		catch (Exception e)
		{
			Log.e("TimeToLongAsHours", "exception in try to convert a time HH:mm to float! " + e.getMessage());
		}
		
		return longTime;
	}
	
	/**
	 * 
	 * @param time a time as minutes
	 * @return a string representing time written as HH:mm
	 */
	public static String LongToTime(long time)
	{
		String sTime="00:00";
		try{
			long hours = (int)(time / 60);
			String sHours =  String.format("%02d",hours);
			long minutes = time - hours*60;
			String sMin = String.format("%02d", minutes);
			sTime=String.format("%s:%s", sHours, sMin);
		}
		catch (Exception e)
		{
			Log.e("TimeToLong", "exception in try to convert a time HH:mm to long! " + e.getMessage());
		}
		
		return sTime;
	}

}
