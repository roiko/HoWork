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
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	private String className = "MyWidgetProvider";
	public static String W_IN_CLICKED = "com.roiko.HoWork.BtnWIN";
	public static String W_OUT_CLICKED = "com.roiko.HoWork.BtnWOUT";
	private HoWorkSQLHelper wDBHelper;

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Toast.makeText(context, "OnUpdate!", Toast.LENGTH_SHORT).show();
		Init(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		Toast.makeText(context, "OnEnabled!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			// TODO Auto-generated method stub
			super.onReceive(context, intent);
			if (intent.getAction().equals(W_IN_CLICKED)) {
				// IN Stamp
				BtnINClicked(context);

			} else if (intent.getAction().equals(W_OUT_CLICKED)) {
				// OUT stamp
				BtnOUTClicked(context);
			}
		} catch (Exception ex) {
			Toast.makeText(context, "Eccezione: " + ex.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		// else if
	}

	private boolean Init(Context c, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Loop for all the widget instances
		for (int i = 0; i < appWidgetIds.length; i++) {
			int AppWidgetId = appWidgetIds[i];

			// Create Intent to launch activity
			Intent intent = new Intent(c, WidgetService.class);
			intent.setAction(W_IN_CLICKED);

			PendingIntent pIntent = PendingIntent.getService(c, 0, intent, 0);

			// Retrieve the widget layout and attach the intent to button IN
			RemoteViews rViews = new RemoteViews(c.getPackageName(),
					R.layout.widget);
			rViews.setOnClickPendingIntent(R.id.btnWIN, pIntent);

			// Appdate the current widget
			appWidgetManager.updateAppWidget(AppWidgetId, rViews);
		}
		return true;
	}

	protected void BtnINClicked(Context context) {
		Log.d(className, "Premuto IN!");
		Stamp stamp = getStampData(context);
		String msg = String.format("%02d/%02d/%d IN at %s", stamp.day,
				stamp.month, stamp.year, stamp.getTime());
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		DoSomething(context);
	}

	private void DoSomething(Context c) {
		Intent intent = new Intent(c, WidgetService.class);
		intent.setAction(W_IN_CLICKED);
		c.startService(intent);
		int a = 0;
	}

	protected void BtnOUTClicked(Context context) {
		Log.d(className, "Premuto IN!");
		Stamp stamp = getStampData(context);
		String msg = String.format("%02d/%02d/%d OUT at %s", stamp.day,
				stamp.month, stamp.year, stamp.getTime());
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	protected void InsertStamp(Stamp stamp, String way) {
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
