package com.ioroiko.howork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ioroiko.howork.SummaryAdapter.DayStamp;
import com.ioroiko.howork.SummaryAdapter.SummaryAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	private int year, month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		Intent intent = getIntent();
		year = intent.getIntExtra(GlobalVars.EXTRA_YEAR, -1);
		month = intent.getIntExtra(GlobalVars.EXTRA_MONTH, -1);
		GetStampsOfMonth();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
		return true;
	}

	public void GetStampsOfMonth() {
		HoWorkSQLHelper helper = new HoWorkSQLHelper(this);
		ArrayList<Integer> dayList = new ArrayList<Integer>(); // List of day
																// numbers which
																// have stamps
		ArrayList<Stamp> stamps = helper.GetStampsOfMonth(year, month, dayList);
		TextView tvMonth = (TextView) findViewById(R.id.tvSummaryMonth);
		tvMonth.setText(String.format("%s %s", Utils.GetMonthNameFromDate(this, month), year));
		int a = 0;
		List<DayStamp> list = Utils.ConvertStampArrayListToListDayStamp(stamps);
		// ========================================================
		if (!list.isEmpty()) {
			ListView listView = (ListView) findViewById(R.id.listViewSummary);
			SummaryAdapter summaryAdapter = new SummaryAdapter(this,
					R.layout.day_stamp, list);
			listView.setAdapter(summaryAdapter);
		}
		// ========================================================
	}
	
}
