package com.ioroiko.howork.SummaryAdapter;

import java.util.ArrayList;

import com.ioroiko.howork.Stamp;

public class DayStamp {

	public ArrayList<Stamp> Stamps;
	public int Day, Month, Year; 
	
	
	public DayStamp() {
		Stamps = new ArrayList<Stamp>();
		// TODO Auto-generated constructor stub
	}
	
	public String getDate()
	{
		String date=String.format("%s/%s/%s", Year,Month,Day);
		return date;
	}

}
