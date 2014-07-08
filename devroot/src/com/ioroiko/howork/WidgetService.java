package com.ioroiko.howork;

import com.ioroiko.howork.HoWorkContract.DBINFO;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.WindowId;
import android.widget.Toast;

public class WidgetService extends IntentService {

	
	public WidgetService(){
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
		DoSomething();
	}
	
	
	//Testing purpose: Leggo e stampo versione del database
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN) private void DoSomething()
	{
		HoWorkSQLHelper wHelper = new HoWorkSQLHelper(this);
		SQLiteDatabase db = wHelper.getReadableDatabase();

		
		//Try to retrieve the inserted values
		String[] columns = new String[2];
		columns[0] = DBINFO.CN_KEY;
		columns[1] = DBINFO.CN_VALUE;
		Cursor cur = db.query(false, HoWorkContract.DBINFO.TABLE_NAME, columns, null, null, null, null, null, null, null);
		cur.moveToFirst();
		for (int i = 0; i < cur.getCount(); i++) {
			String record = i+1+ ")";
			for (int j = 0; j < cur.getColumnCount(); j++) {
				record+=cur.getColumnName(j) +": "+ cur.getString(j)+"\n";
			}
			Log.d("", record);
		}
		int a=0;
	}

}
