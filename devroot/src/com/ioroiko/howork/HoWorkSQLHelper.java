package com.ioroiko.howork;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import com.ioroiko.howork.GlobalVars.Way;
import com.ioroiko.howork.GlobalVars.WriteStampRes;
import com.ioroiko.howork.HoWorkContract.DAYS;
import com.ioroiko.howork.HoWorkContract.DBINFO;
import com.ioroiko.howork.HoWorkContract.STAMPS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.ExifInterface;
import android.util.Log;

public class HoWorkSQLHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "HoWork";
	private static final int DB_VERSION = 1;

	public HoWorkSQLHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		Log.d("HoWorkSQLHelper", "onCreate: DB creation started...");

		Log.d("HoWorkSQLHelper", "onCreate: " + DBINFO.SQL_CREATE_DBINFO_TABLE);
		db.execSQL(HoWorkContract.DBINFO.SQL_CREATE_DBINFO_TABLE);
		Log.d("HoWorkSQLHelper", "onCreate: " + DBINFO.TABLE_NAME + " created!");

		Log.d("HoWorkSQLHelper", "onCreate: " + DAYS.SQL_CREATE_DAYS_TABLE);
		db.execSQL(HoWorkContract.DAYS.SQL_CREATE_DAYS_TABLE);
		Log.d("HoWorkSQLHelper", "onCreate: " + DAYS.TABLE_NAME + " created!");

		Log.d("HoWorkSQLHelper", "onCreate: " + STAMPS.SQL_CREATE_STAMPS_TABLE);
		db.execSQL(HoWorkContract.STAMPS.SQL_CREATE_STAMPS_TABLE);
		Log.d("HoWorkSQLHelper", "onCreate: " + STAMPS.TABLE_NAME + " created!");

		Log.d("HoWorkSQLHelper", "onCreate: DB creation complete successfully!");

		// Add version to DB
		Log.d("HoWorkSQLHelper", "onCreate: Setting DB version " + DB_VERSION
				+ " into " + DBINFO.TABLE_NAME + "...");
		ContentValues values = new ContentValues();
		values.put(HoWorkContract.DBINFO.CN_KEY, "V");
		values.put(HoWorkContract.DBINFO.CN_VALUE, DB_VERSION);
		db.insert(HoWorkContract.DBINFO.TABLE_NAME, null, values);
		Log.d("HoWorkSQLHelper", "onCreate: DB version set!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return the ID value of the day if exist, otherwise -1
	 */
	public int DayExist(int year, int month, int day) {

		int id_Day = -1;
		// Retrieve all days of a specific year,month,day. days must contain
		// only one element!!!!

		SQLiteDatabase db = this.getReadableDatabase();
		String[] selArgs = new String[] { String.valueOf(year),
				String.valueOf(month), String.valueOf(day) };

		String rawQuery = "SELECT * FROM " + DAYS.TABLE_NAME + " WHERE "
				+ DAYS.CN_YEAR + " =? AND " + DAYS.CN_MONTH + "=? AND "
				+ DAYS.CN_DAY + "=?";

		Cursor cursor = db.rawQuery(rawQuery, selArgs);
		/*
		 * String[] columns = new String[]{DAYS.CN_YEAR, DAYS.CN_MONTH,
		 * DAYS.CN_DAY}; String where = DAYS.CN_YEAR+" =? AND " + DAYS.CN_MONTH
		 * +"=? AND "+ DAYS.CN_DAY +"=?"; String[] selArgs = new
		 * String[]{String.valueOf(year), String.valueOf(month),
		 * String.valueOf(day)}; String orderBy = DAYS.CN_YEAR +","+
		 * DAYS.CN_MONTH+","+ DAYS.CN_DAY; db.query(true,
		 * DAYS.TABLE_NAME,columns, DAYS.CN_YEAR , selArgs, null, null, orderBy,
		 * null, null);
		 */

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			id_Day = cursor.getInt(cursor.getColumnIndex(DAYS._ID));
			Log.d("HoWorkSQLHelper", "[DayExist] Day found with _ID = "
					+ id_Day);
		}
		return id_Day;

	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return the ID of the inserted element
	 */
	public int AddDay(int year, int month, int day) {
		int id_day = -1;
		SQLiteDatabase db = this.getReadableDatabase();

		/*
		 * String myQuery = DAYS.SQL_INSERT_DAY; String[] insArgs = new String[]
		 * { String.valueOf(year), String.valueOf(month), String.valueOf(day) };
		 * Cursor cursor = db.rawQuery(myQuery, insArgs);
		 */

		ContentValues values = new ContentValues();
		values.put(DAYS.CN_YEAR, String.valueOf(year));
		values.put(DAYS.CN_MONTH, String.valueOf(month));
		values.put(DAYS.CN_DAY, String.valueOf(day));
		long id_Created = db.insert(DAYS.TABLE_NAME, null, values);
		id_day = (int) id_Created;
		return id_day;

	}
	
	public int InsertStamp(Stamp stamp, Way way)
	{
		int id_Stamp = -1;
		int id_Day = -1;
		id_Day = DayExist(stamp.year, stamp.month, stamp.day); 
		if (id_Day == -1)
		{
			Log.d("HoWorkSQLHelper", "Need to create today!");
			id_Day = AddDay(stamp.year, stamp.month, stamp.day);
			Log.d("HoWorkSQLHelper", String.format("Today created (ID = %d)!", id_Day));
		}
		else 
		{
			Log.d("HoWorkSQLHelper", String.format("Today already exists (ID =%d)! No need to create it.",id_Day));
		}
			
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//Insert timestamp
		ContentValues values = new ContentValues();
		values.put(STAMPS.CN_DAY_ID, id_Day);
		values.put(STAMPS.CN_TIME, String.format("%02d:%02d", stamp.hour, stamp.minute));
		Log.d("HoWorkSqlHelper", "[InsertStamp] saving " + String.format("%02d:%02d", stamp.hour, stamp.minute)+"...");
		if (way.equals(GlobalVars.Way.IN))
			values.put(STAMPS.CN_WAY, STAMPS.WAY_IN);
		else
			values.put(STAMPS.CN_WAY, STAMPS.WAY_OUT);
		
		long id_Added = db.insert(STAMPS.TABLE_NAME, null, values);
		Log.d("HoWorkSqlHelper", "[InsertStamp] ...saved!");
		id_Stamp  = (int) id_Added;
		
		return id_Stamp;
	}

	public ArrayList<Stamp> GetStampsOfDay(int year, int month, int day) {
		ArrayList<Stamp> stamps = new ArrayList<Stamp>();
		int id_day = -1;
		if (DayExist(year, month, day) != -1)// cerco timbrature oslo se esiste
												// il giorno
		{
			String[] selArgs = new String[] { String.valueOf(year),
					String.valueOf(month), String.valueOf(day) };
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery(STAMPS.SQL_GET_STAMPS_OF_A_DAY, selArgs);
			if (cur.getCount() > 0) {
				cur.moveToFirst();
				boolean loop = true;
				do {
					// TODO riempire la lista di timbrature
					String timeString = cur.getString(cur.getColumnIndex(HoWorkContract.STAMPS.CN_TIME)); //Time value
					String[] timeSplit = timeString.split(":");
					Stamp tempStamp = new Stamp(year, month, day, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
					String way = cur.getString(cur.getColumnIndex(HoWorkContract.STAMPS.CN_WAY));
					if (cur.getString(cur.getColumnIndex(HoWorkContract.STAMPS.CN_WAY)).equals(HoWorkContract.STAMPS.WAY_IN))
						tempStamp.way=Way.IN;
					else if (cur.getString(cur.getColumnIndex(HoWorkContract.STAMPS.CN_WAY)).equals(HoWorkContract.STAMPS.WAY_OUT))
						tempStamp.way=Way.OUT;
					stamps.add(tempStamp);
					
					if (cur.isLast())
						loop=false;
					else
						cur.moveToNext();
					
				} while (loop);
			}

		}

		return stamps;
	}

}
