package com.ioroiko.howork;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.ioroiko.howork.SummaryAdapter.DayStamp;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.Time;

public class Utils {
	
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
	
	public static List<DayStamp> ConvertStampArrayListToListDayStamp(ArrayList<Stamp> aa)
	{
		List<DayStamp> list = new LinkedList<DayStamp>();
		int day=-1;
		DayStamp tempDayStamp=new DayStamp();
		for (Stamp stamp : aa) {
			if (day==-1)
			{
				day=stamp.day;
				tempDayStamp = new DayStamp();
				tempDayStamp.Day=stamp.day;
				tempDayStamp.Month=stamp.month;
				tempDayStamp.Year=stamp.year;
			}
			if (day==stamp.day)
			{
				//Sto collezionando gli stamp di questo giorno
				tempDayStamp.Stamps.add(stamp);
			}
			if (day!=stamp.day)
			{
				//Ricado qui se, ciclando, ho cambiato giorno. Quindi aggiungo DayStamp alla lista, 
				//altrimenti continuo ad aggiungere stamp a DayStamp
				list.add(tempDayStamp);
				day = stamp.day;
				tempDayStamp=new DayStamp();
				tempDayStamp.Day=stamp.day;
				tempDayStamp.Month=stamp.month;
				tempDayStamp.Year=stamp.year;
				tempDayStamp.Stamps.add(stamp);
			}
		}
		list.add(tempDayStamp);//Aggiungo l'ultimo giorno
		
		return list;
	}
	
	
	public static String GetDayNameFromDate(Context c, int day, int month, int year)
	{
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		calendar.set(year, month-1, day);
		int dayI = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		return GetDayOfWeekNameFromint(c, dayI);
	}
	
	private static String GetDayOfWeekNameFromint(Context c, int dayI)
	{
		//return c.getResources().getString(R.string.SU);
		
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
	
}
