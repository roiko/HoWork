package com.ioroiko.howork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HoWorkSQLHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME="HoWork";
	private static final int DB_VERSION  = 1;
	
	
	public HoWorkSQLHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(HoWorkContract.DBINFO.SQL_CREATE_DBINFO);
		//db.execSQL(HoWorkContract.DAYS.SQL_CREATE_DAYS);
		//db.execSQL(HoWorkContract.STAMPS.SQL_CREATE_DAYS);
		
		//Add version to DB
		/*ContentValues values = new ContentValues();
		values.put(HoWorkContract.DBINFO.CN_KEY, "V");
		values.put(HoWorkContract.DBINFO.CN_VALUE, DB_VERSION);
		db.insert(HoWorkContract.DBINFO.TABLE_NAME, null, values);*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
