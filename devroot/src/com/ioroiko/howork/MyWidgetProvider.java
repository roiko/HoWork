package com.ioroiko.howork;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ioroiko.howork.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

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
		UpdateTodayStamps(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			// TODO Auto-generated method stub
			super.onReceive(context, intent);
			Log.d("MyWidgetProvider", "Intent Received: " + intent.getAction());
			if ((intent.getAction().equals(GlobalVars.BTN_STAMP_CLICK))) {//useless?
				// IN or OUT? check STAMP_WAY in Extra
			}
			else if (intent.getAction().equals(WidgetService.SERVICE_INTENT_TIMESTAMP_UPDATED)){
				//Service has stored the timestamp. Then it raised the intent to update all widgets IN/OUT items
				Toast.makeText(context, "Updating widget UI...", Toast.LENGTH_SHORT).show();
				UpdateTodayStamps(context);
				Toast.makeText(context, "UI updated!", Toast.LENGTH_SHORT).show();
				
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
			
			String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
			rViews.setTextViewText(R.id.tvWToday, currentDateTimeString);

			//Btn IN
			Intent intentIN = new Intent(c, WidgetService.class);
			intentIN.setAction(GlobalVars.BTN_STAMP_CLICK);
			intentIN.putExtra(GlobalVars.STAMP_WAY, GlobalVars.WAY_IN);
			//use hashcode to avoid Android uses the last declared intent information. 
			//It must be exactly the one declared in previous istruction
			PendingIntent pIntentIN = PendingIntent.getService(c, intentIN.hashCode(), intentIN, 0);
			rViews.setOnClickPendingIntent(R.id.btnWIN, pIntentIN);
	
			//Btn OUT
			Intent intentOUT = new Intent(c, WidgetService.class);
			intentOUT.setAction(GlobalVars.BTN_STAMP_CLICK);
			intentOUT.putExtra(GlobalVars.STAMP_WAY, GlobalVars.WAY_OUT);
			//use hashcode to avoid Android uses the last declared intent information (for example uses extra of intentIN). 
			//It must be exactly the one declared in previous istruction
			PendingIntent pIntentOUT = PendingIntent.getService(c,intentOUT.hashCode(), intentOUT, 0);
			rViews.setOnClickPendingIntent(R.id.btnWOUT, pIntentOUT);
			
			//Btn EDIT
			Intent intentEdit = new Intent(c, ActivityEdit.class);
			intentEdit.setAction(GlobalVars.BTN_EDIT_CLICK);
			intentEdit.putExtra(GlobalVars.EXTRA_YEAR, today.year);
			intentEdit.putExtra(GlobalVars.EXTRA_MONTH, today.month);
			intentEdit.putExtra(GlobalVars.EXTRA_DAY, today.day);
			PendingIntent pIntentEDIT =  PendingIntent.getActivity(c, intentEdit.hashCode(), intentEdit, 0);
			rViews.setOnClickPendingIntent(R.id.btnWEdit, pIntentEDIT);
				
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
		
		_wDBHelper = new HoWorkSQLHelper(c);
		stampsOfToday = _wDBHelper.GetStampsOfDay(today.year, today.month, today.day);//Dovrei usare il service per fare una cosa pulita...
		
		//Loop for all widgets
		ComponentName providerName = new ComponentName(c, MyWidgetProvider.class);
		int[] appWidgetIDs = AppWidgetManager.getInstance(c).getAppWidgetIds(providerName);
		
		for (int j=0;j<appWidgetIDs.length;j++)
		{
		//Set all textviews value IN1/OUT1, IN2/OUT2,...
		//Always use remoteView for widget!
		RemoteViews rViews = new RemoteViews(c.getPackageName(),R.layout.widget);
		for(int i=0; i<stampsOfToday.size(); i++)
		{
			switch (i) //Every index update a textView IN/OUT
			{
			case 0:
				rViews.setViewVisibility(R.id.tvTSIN1, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSIN1, stampsOfToday.get(i).getTime());
				break;
			case 1:
				rViews.setViewVisibility(R.id.tvTSOUT1, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSOUT1, stampsOfToday.get(i).getTime());
				break;
			case 2:
				rViews.setViewVisibility(R.id.tvTSIN2, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSIN2, stampsOfToday.get(i).getTime());
				break;
			case 3:
				rViews.setViewVisibility(R.id.tvTSOUT2, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSOUT2, stampsOfToday.get(i).getTime());
				break;
			case 4:
				rViews.setViewVisibility(R.id.tvTSIN3, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSIN3, stampsOfToday.get(i).getTime());
				break;
			case 5:
				rViews.setViewVisibility(R.id.tvTSOUT3, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSOUT3, stampsOfToday.get(i).getTime());
				break;
			case 6:
				rViews.setViewVisibility(R.id.tvTSIN4, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSIN4, stampsOfToday.get(i).getTime());
				break;
			case 7:rViews.setViewVisibility(R.id.tvTSOUT4, View.VISIBLE);
				rViews.setTextViewText(R.id.tvTSOUT4, stampsOfToday.get(i).getTime());
				break;
			}
		}
		AppWidgetManager.getInstance(c).updateAppWidget(providerName, rViews);
		}
	}

}
