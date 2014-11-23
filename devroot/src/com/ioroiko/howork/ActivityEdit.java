package com.ioroiko.howork;

import java.util.ArrayList;

import com.ioroiko.howork.CustomAdapter.CustomListArrayAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityEdit extends Activity {

	protected HoWorkSQLHelper _aHelper;
	protected int year, month, day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_edit);
		updateStamps();

	}

	public void updateStamps() {
		_aHelper = new HoWorkSQLHelper(this);
		Intent receivedIntent = getIntent();

		year = receivedIntent.getIntExtra(GlobalVars.EXTRA_YEAR, -1);
		month = receivedIntent.getIntExtra(GlobalVars.EXTRA_MONTH, -1);
		day = receivedIntent.getIntExtra(GlobalVars.EXTRA_DAY, -1);

		TextView tvDate = (TextView) findViewById(R.id.tvDate);
		tvDate.setText(String.format("%s %s %s, %s", Utils.GetDayNameFromDate(this, day, month, year), day, Utils.GetMonthNameFromDate(this, month), year));
		ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayList<Stamp> todayStamps = _aHelper
				.GetStampsOfDay(year, month, day);
		CustomListArrayAdapter myAdapter = new CustomListArrayAdapter(this,
				R.layout.custom_list_ltem, todayStamps.toArray());
		listView.setAdapter(myAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_edit, menu); Rocco: removed for now..
		return true;
	}
	
	//Finish the activity when user presses Back/Home button
	@Override
	public void onPause()
	{
		finish();
		super.onPause();
	}
	
}
