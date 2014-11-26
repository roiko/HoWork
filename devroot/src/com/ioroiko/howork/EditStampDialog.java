package com.ioroiko.howork;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * @author roiko This class represent a Fragment containing a Dialog with layout
 *         specified in edit_stamp_dialog.xml
 * 
 */
public class EditStampDialog extends DialogFragment {

	public Stamp diaStamp;
	View dialogView;

	// public AlertDialog dialog;

	public EditStampDialog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		// Get and store the View representing the Dialog with my custom layout
		dialogView = inflater.inflate(R.layout.edit_stamp_dialog, null);

		final TimePicker tp = (TimePicker) dialogView
				.findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);// set 24h style
		builder.setView(dialogView);

		if (diaStamp != null)// Update TimePicker with current stamp
			setTime();

		builder.setMessage(String.format("%s: %s",
				getString(R.string.dialogTitle), this.diaStamp.getTime()));

		builder.setNegativeButton(getString(R.string.dialogPositive),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User confirms save
						HoWorkSQLHelper helper = new HoWorkSQLHelper(
								getActivity());
						Stamp oldStamp = new Stamp(diaStamp.year,
								diaStamp.month, diaStamp.day, diaStamp.hour,
								diaStamp.minute);
						oldStamp.way = diaStamp.way;
						Stamp newStamp = new Stamp(diaStamp.year,
								diaStamp.month, diaStamp.day, diaStamp.hour,
								diaStamp.minute);
						newStamp.hour = tp.getCurrentHour();
						newStamp.minute = tp.getCurrentMinute();
						boolean result = helper.UpdateStampTime(oldStamp,
								newStamp);
						if (result) {
							Log.d("onCreateDialog", "Stamp time updated!");
							// Update ActivityEdit and Widget
							ActivityEdit curActivity = (ActivityEdit) getActivity();
							curActivity.updateStamps();
							Intent updateIntent = new Intent(dialogView
									.getContext(), MyWidgetProvider.class);
							updateIntent
									.setAction(GlobalVars.SERVICE_INTENT_TIMESTAMP_UPDATED);
							curActivity.sendBroadcast(updateIntent);
						} else {
							Log.e("onCreateDialog",
									"Error in updating the Stamp!");
						}
					}
				});

		builder.setPositiveButton(getString(R.string.dialogNegative),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Remove the stamp!
						HoWorkSQLHelper helper = new HoWorkSQLHelper(
								getActivity());
						Stamp delStamp = new Stamp(diaStamp.year,
								diaStamp.month, diaStamp.day, diaStamp.hour,
								diaStamp.minute);
						delStamp.way = diaStamp.way;
						boolean res = false;
						ActivityEdit curActivity = (ActivityEdit) getActivity();
						if (helper.CanRemoveStamp(delStamp)) {
							res = helper.RemoveStamp(delStamp);
							if (res) {

								curActivity.updateStamps();
								Intent updateIntent = new Intent(dialogView
										.getContext(), MyWidgetProvider.class);
								updateIntent
										.setAction(GlobalVars.SERVICE_INTENT_TIMESTAMP_UPDATED);
								curActivity.sendBroadcast(updateIntent);
							} else {
								Log.e("onCreateDialog",
										"Error in removing the Stamp!");
							}
						} else {
							Toast.makeText(
									curActivity,
									curActivity.getResources().getString(
											R.string.cantRemove),
									Toast.LENGTH_SHORT).show();
							Log.d("onCreateDialog",
									"Cannot remove the stamp! It is not first or last!");
						}
					}
				});

		builder.setNeutralButton(getString(R.string.dialogNeutral),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		// Create the AlertDialog object and return it
		return builder.create();
	}

	public void setStamp(Stamp stamp) {
		diaStamp = stamp;
	}

	private void setTime() {
		TimePicker tp = (TimePicker) dialogView.findViewById(R.id.timePicker1);
		tp.setCurrentHour(diaStamp.hour);
		tp.setCurrentMinute(diaStamp.minute);

	}

}
