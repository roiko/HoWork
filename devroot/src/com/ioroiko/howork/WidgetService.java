package com.ioroiko.howork;

import java.util.ArrayList;
import java.util.Calendar;

import com.ioroiko.howork.GlobalVars.ReadTodayTimeStamps;
import com.ioroiko.howork.GlobalVars.Way;
import com.ioroiko.howork.GlobalVars.WriteStampRes;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class WidgetService extends IntentService {

	// public static final String SERVICE_INTENT = "com.ioroiko.ServiceIntent";
	public static final String SERVICE_INTENT_TIMESTAMP_UPDATED = "SERVICE_UPDATE_TIMESTAMP";
	public static final String STAMP_STORED = "STAMP_STORED";

	private Handler mMainThreadHandler = null; // per fare Toast senza generare
												// eccezione dead thread
	private String className = "WidgetService";
	private HoWorkSQLHelper _wHelper = new HoWorkSQLHelper(this);

	public WidgetService() {
		super("WidgetService");
		mMainThreadHandler = new Handler();
	}

	public WidgetService(String name) {
		super(name);
		mMainThreadHandler = new Handler();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		// TODO Auto-generated method stub
		Log.i("WidgetService", "Ricevuto intent: " + workIntent.getAction());

		if (workIntent.getAction().equals(GlobalVars.BTN_STAMP_CLICK)) {
			String way = workIntent.getStringExtra(GlobalVars.STAMP_WAY);
			// devo controllare se il precedente è uguale al corrente o il
			// massimo numero di stamp è scritto
			// nel caso, valutare se sovrascriverla o no
			ArrayList<Stamp> todayStamps = GetStampsOfToday();
			Stamp now = Utils.getTodayAsStamp(this);
			now.way = (workIntent.getStringExtra(GlobalVars.STAMP_WAY)
					.equals(GlobalVars.WAY_IN)) ? GlobalVars.Way.IN
					: GlobalVars.Way.OUT;
			if (CanWriteNewStamp(todayStamps, now)) {
				Log.i("WidgetService",
						String.format("Eseguo WriteStamp(%s)", now.way));
				WriteStamp(now.way);
			} else {
				String message = String
						.format("Timbratura non ammessa (%s) o massimo raggiunto, non salvo",
								now.way);
				Log.i("WidgetService", message);
				ToastmakeText(message, Toast.LENGTH_SHORT);
			}
		}

	}

	public void ToastmakeText(final String text, int duration) {
		mMainThreadHandler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	// Testing purpose: add today and a IN stamp
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void DoSomething() {
		// Controlla se
		WriteStamp(GlobalVars.Way.IN);
	}

	/**
	 * 
	 * @param storedStamps
	 *            : list of already stored stamps for today
	 * @param wantedStamp
	 *            : the stamp I want to store
	 * @return If the last stamp is the same WAY of the wantedStamp, return
	 *         false. Else, return true
	 */
	private boolean CanWriteNewStamp(ArrayList<Stamp> storedStamps,
			Stamp wantedStamp) {
		if (storedStamps.size() == 0)// non esistono stamps per oggi
		{
			if (wantedStamp.way.equals(GlobalVars.Way.OUT))
				return false;//first stamp can't be OUT
			else
				return true;
		}

		GlobalVars.Way lastWay = storedStamps.get(storedStamps.size() - 1).way;
		if (storedStamps.size() == 8)// maximum stamps limit reached!
			return false;
		if (lastWay == null) {
			Log.e("CanWriteNewStamp",
					"Errore, way non può essere null!!! Ritorno false");
			return false;
		}
		if (lastWay.equals(wantedStamp.way))// Non scrivo due volte lo stesso
											// timestamp (anche se potrei
											// sovrascriverlo...)
			return false;

		return true;

	}

	public GlobalVars.WriteStampRes WriteStamp(GlobalVars.Way way) {
		String method = "WriteStamp";
		WriteStampRes result = WriteStampRes.Ok;
		SQLiteDatabase db;
		try {
			db = _wHelper.getWritableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(method,
					"Exception when trying to retrieve writeable database! Exception: "
							+ e.getMessage());
			return WriteStampRes.ExceptionThrown;
		}
		if (!db.isOpen())
			return WriteStampRes.DBClosed;

		/* Add stamp */
		Stamp nowStamp = Utils.getTodayAsStamp(this);
		Log.d("WidgetService",
				"[WriteStamp] calling InsertStamp(" + way.toString() + ")...");
		_wHelper.InsertStamp(nowStamp, way);
		Log.d("WidgetService", "[WriteStamp] InsertStamp(" + way.toString()
				+ ") end!");

		// Qui voglio inviare come chiave la textView da aggiornare e come
		// valore la stringa di testo

		// Da rimuovere questa riga quando il commento sopra è implementato
		SendBroadcastIntent(SERVICE_INTENT_TIMESTAMP_UPDATED, STAMP_STORED,
				"Update your timestamps textViews!");

		return result;

	}

	private void SendBroadcastIntent(String action, String key, String message) {
		Intent serviceIntent = new Intent(this, MyWidgetProvider.class);
		serviceIntent.setAction(action);
		serviceIntent.putExtra(key, message);
		this.sendBroadcast(serviceIntent);
	}

	public ArrayList<Stamp> GetStampsOfToday() {
		ArrayList<Stamp> stamps = new ArrayList<Stamp>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		stamps = _wHelper.GetStampsOfDay(year, month, day);
		return stamps;
	}

}
