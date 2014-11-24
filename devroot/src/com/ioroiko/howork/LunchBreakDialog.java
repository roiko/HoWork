package com.ioroiko.howork;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class LunchBreakDialog extends DialogFragment {

	// public Stamp diaStamp;
	View dialogView;
	private SharedPreferences sharedPref;

	public LunchBreakDialog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		// Get and store the View representing the Dialog with my custom layout
		dialogView = inflater.inflate(R.layout.edit_stamp_dialog, null);
		
		sharedPref = getActivity().getSharedPreferences(GlobalVars.SHARED_PREFERENCES_HOWORK, Context.MODE_PRIVATE);
		String lunchTime = sharedPref.getString(GlobalVars.LUNCH_TIME, "00:00");
		String hour = lunchTime.split(":")[0];
		String minutes = lunchTime.split(":")[1];
		final TimePicker tp = (TimePicker) dialogView
				.findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);// set 24h style
		try
		{
			tp.setCurrentHour(Integer.parseInt(hour));
			tp.setCurrentMinute(Integer.parseInt(minutes));
		}
		catch (Exception e)//in case of error
		{
			Log.e("onCreateDialog", "Cannot convert a non-integer value for lunch time!!!");
			tp.setCurrentHour(0);
			tp.setCurrentMinute(0);
		}
		
		builder.setView(dialogView);
		builder.setMessage(getString(R.string.lunchBreakDialogTitle));
		
		builder.setNegativeButton(getResources().getString(R.string.dialogPositive), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = sharedPref.edit();
				String newLunchTime= String.format("%02d:%02d",tp.getCurrentHour(), tp.getCurrentMinute());
				editor.putString(GlobalVars.LUNCH_TIME, newLunchTime);
				editor.commit();
				
				TextView tvBreak = (TextView)getActivity().findViewById(R.id.tvLunchBreakVal);
				tvBreak.setText(newLunchTime);
			}
		});
		
		builder.setPositiveButton(getResources().getString(R.string.dialogNeutral), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Nothing to do here
			}
		});

		// Create the AlertDialog object and return it
		return builder.create();
	}

}
