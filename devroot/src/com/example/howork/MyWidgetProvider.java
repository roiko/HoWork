package com.example.howork;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	public static String W_IN_CLICKED = "com.roiko.HoWork.BtnWIN";
	public static String W_OUT_CLICKED = "com.roiko.HoWork.BtnWOUT";

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Toast.makeText(context, "OnUpdate!", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onEnabled(Context context) {
		Toast.makeText(context, "OnEnabled!", Toast.LENGTH_SHORT).show();
		RemoteViews rViews = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		ComponentName watchWidget = new ComponentName(context,
				MyWidgetProvider.class);
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);

		/* Associo intent a button IN */
		Intent intent = new Intent(W_IN_CLICKED);
		/*
		 * PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
		 * intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 */
		rViews.setOnClickPendingIntent(R.id.btnWIN,
				getPendingSelfIntent(context, W_IN_CLICKED));
		intent = new Intent(W_OUT_CLICKED);
		rViews.setOnClickPendingIntent(R.id.btnWOUT,
				getPendingSelfIntent(context, W_OUT_CLICKED));
		appWidgetManager.updateAppWidget(watchWidget, rViews);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if (intent.getAction().equals(W_IN_CLICKED)) {
			// IN stamp
			Toast.makeText(context, "Premuto IN!", Toast.LENGTH_SHORT).show();
		} else if (intent.getAction().equals(W_IN_CLICKED)) {
			// OUT stamp
			Toast.makeText(context, "Premuto IN!", Toast.LENGTH_SHORT).show();
		}
		// else if
	}

	private boolean Init(Context context, AppWidgetManager appWIdgetManager,
			int[] appWidgetIds) {

		return true;
	}

	protected PendingIntent getPendingSelfIntent(Context context, String action) {
		Intent intent = new Intent(context, getClass());
		intent.setAction(action);
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}

}
