package com.ioroiko.howork;

public class Stamp {
	
	public int year;
	public int month;
	public int day;
	public int hour;
	public int minute;
	public GlobalVars.Way way;
	
	public Stamp()
	{
		year = 0;
		month = 0;
		day = 0;
		hour = 0;
		minute = 0;

	}

	
	/**
	 * @return the time of the Stamp in the format hh:mm
	 */
	public String getTime()
	{
		return String.format("%02d:%02d", hour,minute);
	}
	
	public Stamp (int year, int month, int day, int hour, int minute)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	

}
