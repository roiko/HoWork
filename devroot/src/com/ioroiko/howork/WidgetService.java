package com.ioroiko.howork;

import java.util.Calendar;

import com.ioroiko.howork.GlobalVars.WriteStampRes;
import com.ioroiko.howork.HoWorkContract.DBINFO;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.ExifInterface;
import android.os.Build;
import android.text.format.Time;
import android.util.Log;
import android.view.WindowId;
import android.widget.Toast;

public class WidgetService extends IntentService {

	private String className = "WidgetService";
	private HoWorkSQLHelper _wHelper = new HoWorkSQLHelper(this);
	
	public WidgetService() {
		super("WidgetService");

	}

	public WidgetService(String name) {
		super(name);

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		// TODO Auto-generated method stub
		Log.i("WidgetService", "Ricevuto intent: " + workIntent.getAction());
		Log.i("WidgetService", "Eseguo DoSomething()");
		DoSomething();
	}

	// Testing purpose: add today and a IN stamp
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void DoSomething() {
		/*Log.d("WidgetService", "Check if exist (year,month,day)");
		int id_day = -1;
		int res = -1;
		int year,month,day;
		year = Calendar.getInstance().get(Calendar.YEAR);
		month = Calendar.getInstance().get(Calendar.MONTH)+1;//JAN = 0
		day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		id_day = _wHelper.DayExist(year, month, day);
		Log.d("WidgetService", "id_day (-1 means does not exist) = " + id_day);
		if (id_day==-1)
		{
			Log.d("WidgetService", "Insert (year,month,day)...");
			res=_wHelper.AddDay(year, month, day);
			Log.d("WidgetService", "...done!");
		}
		else
		{
			Log.d("WidgetService", "Day exist! ID = " + id_day);
		}*/
		
		WriteStampIN();
	}

	public GlobalVars.WriteStampRes WriteStampIN() {
		String method = "WriteStampIN";
		WriteStampRes result = WriteStampRes.Ok;
		SQLiteDatabase db;
		try {
			db = _wHelper.getWritableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(method, "Exception when trying to retrieve writeable database! Exception: "+e.getMessage());
			return WriteStampRes.ExceptionThrown;
		}
		if (!db.isOpen())
			return WriteStampRes.DBClosed;
		
		
		
		
		/*int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int id_Day=-1;
		
		if (_wHelper.DayExist(year, month, day)==-1)
		{
			id_Day = _wHelper.AddDay(year, month, day);
		}*/
		
		/*If any stamp, the last one must be an OUT stamp
		 * TODO*/
		
		/*Add stamp*/
		Stamp nowStamp = Utils.getStampData(this);
		_wHelper.InsertStamp(nowStamp,GlobalVars.Way.IN);
		
		
		return result;

	}
	
	

}
