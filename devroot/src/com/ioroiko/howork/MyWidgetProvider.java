package com.ioroiko.howork;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	private String className = "MyWidgetProvider";
	
	public static String WAY_IN = "com.roiko.HoWork.BtnWIN";
	public static String WAY_OUT = "com.roiko.HoWork.BtnWOUT";
	public static String STAMP_WAY = "com.roiko.HoWork.STAMP_WAY";
	
	public static String BTN_STAMP_CLICK = "com.roiko.HoWork.BTN_STAMP_CLICK";
	
	private HoWorkSQLHelper _wDBHelper;
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Toast.makeText(context, "OnUpdate!", Toast.LENGTH_SHORT).show();
		Init(context, appWidgetManager, appWidgetIds);
		UpdateTodayStamps(context);
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
			Log.d("MyWidgetProvider", "Intent Received: " + intent.getAction());
			if ((intent.getAction().equals(BTN_STAMP_CLICK))) {//useless?
				// IN or OUT? check STAMP_WAY in Extra
			}
			else if (intent.getAction().equals(WidgetService.SERVICE_INTENT_TIMESTAMP_UPDATED)){
				//Service has stored the timestamp. Then it raise the intent to update all widgets IN/OUT items
				UpdateTodayStamps(context);
			}
		} catch (Exception ex) {
			Log.e("MyWidgetProvider", "[onReceive] Exception: " + ex.getMessage());
			Toast.makeText(context, "Eccezione: " + ex.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		// else if
	}

	private boolean Init(Context c, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Loop for all the widget instances
		Stamp today = Utils.getTodayAsStamp(c);
		for (int i = 0; i < appWidgetIds.length; i++) {
			int AppWidgetId = appWidgetIds[i];
			// Retrieve the widget layout
			RemoteViews rViews = new RemoteViews(c.getPackageName(), R.layout.widget);

			//Btn IN
			Intent intentIN = new Intent(c, WidgetService.class);
			intentIN.setAction(BTN_STAMP_CLICK);
			intentIN.putExtra(STAMP_WAY, WAY_IN);
			//use hashcode to avoid Android uses the last declared intent information. 
			//It must be exactly the one declared in previous istruction
			PendingIntent pIntentIN = PendingIntent.getService(c, intentIN.hashCode(), intentIN, 0);
			rViews.setOnClickPendingIntent(R.id.btnWIN, pIntentIN);
	
			//Btn OUT
			Intent intentOUT = new Intent(c, WidgetService.class);
			intentOUT.setAction(BTN_STAMP_CLICK);
			intentOUT.putExtra(STAMP_WAY, WAY_OUT);
			//use hashcode to avoid Android uses the last declared intent information (for example uses extra of intentIN). 
			//It must be exactly the one declared in previous istruction
			PendingIntent pIntentOUT = PendingIntent.getService(c,intentOUT.hashCode(), intentOUT, 0);
			rViews.setOnClickPendingIntent(R.id.btnWOUT, pIntentOUT);
			
			String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
			rViews.setTextViewText(R.id.tvWToday, currentDateTimeString);
			// Appdate the current widget
			appWidgetManager.updateAppWidget(AppWidgetId, rViews);
		}
		return true;
	}
	
	
	//Fires when received an intent like "Update your timestamps" from service
	protected void UpdateTodayStamps (Context c)
	{
		//Retrieve list of stamps for today
		Stamp today = Utils.getTodayAsStamp(c);
		ArrayList<Stamp> stampsOfToday = new ArrayList<Stamp>();
		Intent intent = new Intent(c, WidgetService.class);
		
		_wDBHelper = new HoWorkSQLHelper(c);
		stampsOfToday = _wDBHelper.GetStampsOfDay(today.year, today.month, today.day);//Dovrei usare il service per fare una cosa pulita...
		
		//Loop for all widgets
		ComponentName providerName = new ComponentName(c, MyWidgetProvider.class);
		int[] appWidgetIDs = AppWidgetManager.getInstance(c).getAppWidgetIds(providerName);
		
		for (int j=0;j<appWidgetIDs.length;j++)
		{
			
		int widgetID=appWidgetIDs[j];
		//Set all textviews value IN1/OUT1, IN2/OUT2,...
		//Always use remoteView for widget!
		RemoteViews rViews = new RemoteViews(c.getPackageName(),R.layout.widget);
		for(int i=0; i<stampsOfToday.size(); i++)
		{
			switch (i) //Every index update a textView IN/OUT
			{
			case 0:
				rViews.setTextViewText(R.id.tvTSIN1, stampsOfToday.get(i).getTime());
				break;
			case 1:
				rViews.setTextViewText(R.id.tvTSOUT1, stampsOfToday.get(i).getTime());
				break;
			case 2:
				rViews.setTextViewText(R.id.tvTSIN2, stampsOfToday.get(i).getTime());
				break;
			case 3:
				rViews.setTextViewText(R.id.tvTSOUT2, stampsOfToday.get(i).getTime());
				break;
			case 4:
				rViews.setTextViewText(R.id.tvTSIN3, stampsOfToday.get(i).getTime());
				break;
			case 5:
				rViews.setTextViewText(R.id.tvTSOUT3, stampsOfToday.get(i).getTime());
				break;
			case 6:
				rViews.setTextViewText(R.id.tvTSIN4, stampsOfToday.get(i).getTime());
				break;
			case 7:
				rViews.setTextViewText(R.id.tvTSOUT4, stampsOfToday.get(i).getTime());
				break;
			}
		}
		AppWidgetManager.getInstance(c).updateAppWidget(providerName, rViews);
		}
	}

	
	/*protected void BtnINClicked(Context context) {
		Log.d(className, "Premuto IN!");
		Stamp stamp = Utils.getStampData(context);
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
		Stamp stamp = Utils.getStampData(context);
		String msg = String.format("%02d/%02d/%d OUT at %s", stamp.day,
				stamp.month, stamp.year, stamp.getTime());
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}*/

	protected void InsertStamp(Stamp stamp, String way) {
		// TODO
	}

}
