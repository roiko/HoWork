package com.ioroiko.howork;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		SharedPreferences sharedPrefs = getSharedPreferences(GlobalVars.SHARED_PREFERENCES_HOWORK, Context.MODE_PRIVATE);
    	float tariff=0;
    	tariff = sharedPrefs.getFloat(GlobalVars.TARIFF, 0);
    	EditText et = (EditText) findViewById(R.id.etTariff);
    	String sTariff = String.valueOf(tariff);
    	et.setText(sTariff);
    	
    	TextView tvBreak = (TextView) findViewById(R.id.tvLunchBreakVal);
    	String lunchTime = sharedPrefs.getString(GlobalVars.LUNCH_TIME, "00:00");
    	tvBreak.setText(lunchTime);
	}
	
    //Finish the activity when user presses Home/Back button
    @Override
    public void onPause()
    {
    	super.onPause();
    	SharedPreferences sharedPrefs = getSharedPreferences(GlobalVars.SHARED_PREFERENCES_HOWORK, Context.MODE_PRIVATE);
    	Editor editor = sharedPrefs.edit();
    	EditText et = (EditText) findViewById(R.id.etTariff);
    	float tariff=0;
    	try
    	{
    	tariff = Float.parseFloat(et.getText().toString());
    	editor.putFloat(GlobalVars.TARIFF, tariff);
    	editor.commit();
    	}
    	catch (Exception e)
    	{
    		Log.e("onPause", "Cannot store a non-numeric value for tariff!!! Value was: " + tariff);
    	}
    	
    	
    	finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.settings, menu); //Rocco: Non serve qui
		return true;
	}
	
	public void ShowLunchTime(View v)
	{
		Log.d("ShowLunchTime","Start");
		//Creo il dialog per aggiornare lo stamp
		LunchBreakDialog myDialog = new LunchBreakDialog();

		
		//Mostro il dialog (viene chiamato onCreateDialog())
		Activity curAct = (Activity) v.getContext();
		myDialog.show(curAct.getFragmentManager(),getString(R.string.lunchBreakDialogTitle));
	}

}
