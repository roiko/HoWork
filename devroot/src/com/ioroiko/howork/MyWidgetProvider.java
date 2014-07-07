package com.ioroiko.howork;

import java.util.Calendar;
import com.ioroiko.howork.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	public static String W_IN_CLICKED = "com.roiko.HoWork.BtnWIN";
	public static String W_OUT_CLICKED = "com.roiko.HoWork.BtnWOUT";
	private HoWorkSQLHelper wDBHelper;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Toast.makeText(context, "OnUpdate!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEnabled(Context context) {
		Toast.makeText(context, "OnEnabled!", Toast.LENGTH_SHORT).show();
		Init(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try{
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if (intent.getAction().equals(W_IN_CLICKED)) {
			// IN Stamp
			BtnINClicked(context);

		} else if (intent.getAction().equals(W_OUT_CLICKED)) {
			// OUT stamp
			BtnOUTClicked(context);
		}
		}
		catch (Exception ex)
		{
			Toast.makeText(context, "Eccezione: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		// else if
	}

	private boolean Init(Context context) {
		// ==============INIT BUTTONS AND INTENTS BEGIN=========================
		RemoteViews rViews = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		ComponentName watchWidget = new ComponentName(context,
				MyWidgetProvider.class);
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);

		Intent intent = new Intent(W_IN_CLICKED);
		rViews.setOnClickPendingIntent(R.id.btnWIN,
				getPendingSelfIntent(context, W_IN_CLICKED));

		intent = new Intent(W_OUT_CLICKED);

		rViews.setOnClickPendingIntent(R.id.btnWOUT,
				getPendingSelfIntent(context, W_OUT_CLICKED));

		appWidgetManager.updateAppWidget(watchWidget, rViews);
		// ==============INIT BUTTONS AND INTENTS END=========================
		// Init DBHelper
		//wDBHelper = new HoWorkSQLHelper(context);
		// SQLiteDatabase db = wDBHelper.getWritableDatabase(); //Do it when
		// necessary and in a thread!!!

		return true;
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

	protected void BtnINClicked(Context context) {
		// Toast.makeText(context, "Premuto IN!", Toast.LENGTH_SHORT).show();
		Stamp stamp = getStampData(context);
		String msg = String.format("%02d/%02d/%d IN at %s", stamp.day,
				stamp.month, stamp.year, stamp.getTime());
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		DoSomething(context);
	}

	private void DoSomething(Context c) {
		
		int a = 0;
	}

	protected void BtnOUTClicked(Context context) {
		Stamp stamp = getStampData(context);
		String msg = String.format("%02d/%02d/%d OUT at %s", stamp.day,
				stamp.month, stamp.year, stamp.getTime());
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	protected void SwitchINAndOUTVisibility(Context context) {
		// TODO
	}

	protected void InsertStamp(Stamp stamp, String way) {
		SQLiteDatabase db = wDBHelper.getWritableDatabase();

		// TODO
	}

	protected Stamp getStampData(Context context) {
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
