package com.ioroiko.howork;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.ioroiko.howork.GlobalVars.Way;
import com.ioroiko.howork.HoWorkContract.DAYS;
import com.ioroiko.howork.HoWorkContract.DBINFO;
import com.ioroiko.howork.HoWorkContract.STAMPS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
	 * @param Stamp
	 * @return the ID of the first occurrence of that stamp (normally there is only one occurrence, unless there are more than 1 stamp in the same way and the same time...
	 */
	public int StampExist(Stamp stamp)
	{
		/* select stamps.[_id] from days join stamps on days.[_id]=[STAMPS].[day_id]
		where day = [GIORNO] AND month = [MESE] and year = [ANNO] and way=[WAY] and time = [GETTIME]*/
		String method = "StampExist";
		int stampID = -1;
		SQLiteDatabase db = this.getReadableDatabase();
		String[] cols = new String[]{STAMPS._ID}; 
		//String sel = String.format("%s=? AND %s=? AND %s=?", STAMPS.CN_DAY_ID);
		//String[] selArgs = new String[] { String.valueOf(year),String.valueOf(month), String.valueOf(day) };

		String select = String.format("SELECT %s.%s", STAMPS.TABLE_NAME, STAMPS._ID);
		String from = String.format(" FROM %s JOIN %s ON %s.%s=%s.%s", STAMPS.TABLE_NAME, DAYS.TABLE_NAME, STAMPS.TABLE_NAME,STAMPS.CN_DAY_ID,DAYS.TABLE_NAME,DAYS._ID);
		String where = String.format(" WHERE %s=? AND %s=? AND %s=? AND %s=? AND %s=?", DAYS.CN_DAY, DAYS.CN_MONTH, DAYS.CN_YEAR, STAMPS.CN_WAY, STAMPS.CN_TIME);
		String[] selArgs = new String[]{String.valueOf(stamp.day), String.valueOf(stamp.month), String.valueOf(stamp.year), stamp.way.toString(), stamp.getTime()};
		String rawQuery = select + from + where;
	
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
			stampID = cursor.getInt(cursor.getColumnIndex(DAYS._ID));
			Log.d(method, "Stamp found with _ID = " + stampID);
		}
		return stampID;
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
	
	public boolean UpdateStampTime(Stamp oldStamp, Stamp newStamp)
	{
		String method = "UpdateStamp";
		boolean res=true;
		int existingIDDay = DayExist(oldStamp.year, oldStamp.month, oldStamp.day);
		if (existingIDDay!=-1)
		{
			Log.d(method,String.format("Day exists (ID = %d)",existingIDDay));
			try
			{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			//values.put(STAMPS.CN_WAY, newStamp.way.toString());
			values.put(STAMPS.CN_TIME, newStamp.getTime());
			String[] whereArgs = new String[3];
			whereArgs[0]=oldStamp.way.toString();
			whereArgs[1]=oldStamp.getTime();
			whereArgs[2]=String.valueOf(existingIDDay);
			String whereClause = String.format("%s=? AND %s=? AND %s=?", STAMPS.CN_WAY, STAMPS.CN_TIME, STAMPS.CN_DAY_ID);
			Log.d(method, String.format("Update query parameters: %s=%s - %s=%s - %s=%s",STAMPS.CN_WAY, whereArgs[0], STAMPS.CN_TIME,whereArgs[1], STAMPS.CN_DAY_ID, whereArgs[2]));
			int rows = db.update(STAMPS.TABLE_NAME, values, whereClause, whereArgs);
			Log.d(method,String.format("Rows affected: %d", rows));
			}
			catch (Exception e)
			{
				Log.d(method, "Exception while updating timestamp! " +e.getMessage());
				return false;
			}
			
			
			
		}
		else 
			{
			Log.d(method, "Day does not exist! return FALSE!");
			return false;
			}
			
		return res;
	}
	
	
	/**
	 * 
	 * @param stamp is the stamp you want to remove
	 * @return true if the stamp can be removed, false otherwise. 
	 * Only last stamp can be removed(otherwise it results in two consecutive stamps with the same way).
	 */
	public boolean CanRemoveStamp(Stamp stamp)
	{
		ArrayList<Stamp> stampsOfADay = GetStampsOfDay(stamp.year, stamp.month, stamp.day);
			if (Utils.StampsAreEqual(stampsOfADay.get(stampsOfADay.size()-1),stamp))
				return true;
			return false;
	}

	
	
	public boolean RemoveStamp(Stamp stamp)
	{
		boolean res = true;
		String method = "RemoveStamp";
		//StampExist
		int existingIDStamp = StampExist(stamp);
		if (existingIDStamp!=-1)
		{
			Log.d(method,String.format("Stamp exists (ID = %d)",existingIDStamp));
			try
			{
				
			SQLiteDatabase db = this.getWritableDatabase();
			String table = STAMPS.TABLE_NAME;
			String whereClause = String.format("%s=?", STAMPS._ID);
			String[] whereArgs = new String[]{String.valueOf(existingIDStamp)};
			Log.d(method, String.format("Update query parameters: %s=%s", STAMPS.CN_DAY_ID, whereArgs[0]));
			int rows = db.delete(table, whereClause, whereArgs);
			Log.d(method,String.format("Rows affected: %d", rows));
			}
			catch (Exception e)
			{
				Log.d(method, "Exception while updating timestamp! " +e.getMessage());
				return false;
			}

		}
		else 
			{
			Log.d(method, "Stamp does not exist! return FALSE!");
			return false;
			}
		return res;
	}
	
	public ArrayList<Stamp> GetStampsOfMonth(int year, int month, ArrayList<Integer> daysList)
	{
		ArrayList<Stamp> stamps = new ArrayList<Stamp>();
		
		/*select day, way, time from days join stamps on days.[_id]=[STAMPS].[day_id]
		where month = 10 and year = 2014
		order by day,time*/
		String querySELECT = String.format("SELECT %s, %s, %s", DAYS.CN_DAY, STAMPS.CN_WAY, STAMPS.CN_TIME);
		String queryFROM = String.format(" FROM %s JOIN %s ON %s.%s = %s.%s", DAYS.TABLE_NAME, STAMPS.TABLE_NAME, DAYS.TABLE_NAME, 
				DAYS._ID, STAMPS.TABLE_NAME, STAMPS.CN_DAY_ID);
		String queryWHERE = String.format(" WHERE %s = %s AND %s = %s", DAYS.CN_MONTH, String.valueOf(month), DAYS.CN_YEAR, String.valueOf(year));
		String queryORDER = String.format(" ORDER BY %s, %s", DAYS.CN_DAY, STAMPS.CN_TIME);
		String query = querySELECT + queryFROM + queryWHERE + queryORDER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			int day = cursor.getInt(cursor.getColumnIndex(DAYS.CN_DAY));
			String time = cursor.getString(cursor.getColumnIndex(STAMPS.CN_TIME));
			String[] timeSplit = time.split(":");
			String way = cursor.getString(cursor.getColumnIndex(STAMPS.CN_WAY));
			
			Stamp tempStamp = new Stamp(year, month, day, Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
			if (way.equals(HoWorkContract.STAMPS.WAY_IN))
				tempStamp.way = GlobalVars.Way.IN;
			else
				tempStamp.way = GlobalVars.Way.OUT;
			
			stamps.add(tempStamp);
			if (!daysList.contains(daysList))//sono già ordinati per giorno crescente (dalla query)
				daysList.add(day);
			cursor.moveToNext();
		}
		
		return stamps;
		
	}

}
