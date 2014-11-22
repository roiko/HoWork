package com.ioroiko.howork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import com.ioroiko.howork.SummaryAdapter.DayStamp;
import com.ioroiko.howork.SummaryAdapter.SummaryAdapter;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class SummaryActivity extends Activity {

	private int year, month;
	ProgressDialog pDialog;
	boolean tStop = false;
	Thread tExport;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		Intent intent = getIntent();
		year = intent.getIntExtra(GlobalVars.EXTRA_YEAR, -1);
		month = intent.getIntExtra(GlobalVars.EXTRA_MONTH, -1);
		Draw();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		year = intent.getIntExtra(GlobalVars.EXTRA_YEAR, -1);
		month = intent.getIntExtra(GlobalVars.EXTRA_MONTH, -1);
		Draw();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		Draw();
	}

	private void Draw() {
		Log.i("Draw","Start");
		HoWorkSQLHelper helper = new HoWorkSQLHelper(this);
		ArrayList<Integer> dayList = new ArrayList<Integer>(); // List of day
																// numbers which
																// have stamps
		ArrayList<Stamp> stamps = helper.GetStampsOfMonth(year, month, dayList);
		TextView tvMonth = (TextView) findViewById(R.id.tvSummaryMonth);
		tvMonth.setText(String.format("%s %s",
				Utils.GetMonthNameFromDate(this, month), year));
		List<DayStamp> list = Utils.ConvertStampArrayListToListDayStamp(stamps);
		// ========================================================
		ListView listView = (ListView) findViewById(R.id.listViewSummary);
		listView.setAdapter(null);// Clear previous children, if any
		if (!(list.size() == 0)) {
			SummaryAdapter summaryAdapter = new SummaryAdapter(this,
					R.layout.day_stamp, list);
			listView.setAdapter(summaryAdapter);
		}
		// ========================================================
		Log.i("Draw","End");
	}

	public void PrevClick(View v) {
		if (month == 1) {
			year--;
			month = 12;
		} else {
			month--;
		}
		Draw();
	}

	public void NextClick(View v) {
		if (month == 12) {
			year++;
			month = 1;
		} else {
			month++;
		}
		Draw();
	}

	public void ExportCSV(View v) {
		Log.i("ExportCSV","Start");
		pDialog = CreateProgressDialog(v.getContext());
		AsyncTaskCreateCSV myTask = new AsyncTaskCreateCSV();
		myTask.execute(null, null, null);
	}

	protected ProgressDialog CreateProgressDialog(Context c) {
		ProgressDialog pd = new ProgressDialog(c);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getResources().getString(R.string.exporting_csv));
		pd.setCancelable(true);
		pd.setProgress(0);
		return pd;
	}



	private class AsyncTaskCreateCSV extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			try {
				Log.d("AsyncTaskCreateCSV", "onPreExecute");
				pDialog.setProgress(0);
				pDialog.show();
			} catch (Exception e) {
				Log.d("eccezione", e.getMessage());
			}

		}

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		@Override
		protected Void doInBackground(Void... params) {
			Log.d("AsyncTaskCreateCSV", "doInBackground");
			// TODO Auto-generated method stub

			WriteCSVFile(getBaseContext());
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

			int value = values[0];
			Log.d("AsyncTaskCreateCSV", "onProgressUpdate " + value);
			pDialog.setProgress(value);
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		@Override
		protected void onPostExecute(Void param) {
			Log.d("AsyncTaskCreateCSV", "onPostExecute");
			pDialog.hide();
			Share();
		}

		public void WriteCSVFile(Context c) {
			String method = "WriteCSVFile";
			Log.i(method, "Start");
			
			int percTotHeader=20;//20% is prepare arraylists of this month
			int percTotFileWrite=100-percTotHeader;
			FileOutputStream fos;
			String header = "Data;IN;OUT;IN;OUT;IN;OUT;IN;OUT\n";
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						+ File.separator + GlobalVars.CSVDirName + File.separator);
				if (!dir.exists())
					dir.mkdirs();
				File file = new File(dir, GlobalVars.CSVFileName);
				publishProgress(10);
				fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos);

				osw.write(header);// Write Header
				// =======Write stamps=======
				HoWorkSQLHelper helper = new HoWorkSQLHelper(c);
				ArrayList<Integer> dayList = new ArrayList<Integer>();
				ArrayList<Stamp> stamps = helper.GetStampsOfMonth(year, month,
						dayList);
				publishProgress(15);
				List<DayStamp> list = Utils
						.ConvertStampArrayListToListDayStamp(stamps);
				ArrayList<Integer> days = Utils.GetDaysOfMonth(
						getBaseContext(), year, month); // get all days in this
														// month (1,2,3,4...31);
				publishProgress(percTotHeader);

				int total = days.size();
				int pos = 0;
				int iPerc = 0;
				for (Integer day : days) {
					pos++;
					float fPerc = (float) pos / (float) total * percTotFileWrite;
					iPerc = (int) fPerc;

					boolean isDayPresent = false;
					StringBuilder sb = new StringBuilder();
					for (DayStamp dayStamp : list) {
						if (dayStamp.Day > day)
							break;
						if (day.equals(dayStamp.Day)) {

								Log.d(method, "Reading stamps of day "
										+ dayStamp.Day);

							// Write stamps to file
							sb.append(dayStamp.getDate());
							for (Stamp stamp : dayStamp.Stamps) {
								sb.append(String.format(";%s", stamp.getTime()));
							}

							sb.append("\n");
							isDayPresent = true;
							break;
						}
					}

					if (!isDayPresent)// Here the stamps do not contain this day: Add an empty line to CSV
						sb.append(String.format("%s/%s/%s\n", year, month, day));

					osw.write(sb.toString());// Finally, write the stamps of this day
					publishProgress(iPerc+percTotHeader);

				}
				// ========================

				osw.close();
			} catch (Exception e) {
				Log.e(method, e.getMessage());
				// TODO: handle exception
			}
			
			Log.i(method, "End");
		}
		
		private void Share() {
			String method = "Share";
			Log.i(method,"Start");
			Log.d("Share!", "Start sharing!");
			File dir = new File(Environment.getExternalStorageDirectory() + File.separator + GlobalVars.CSVDirName + File.separator);
	    	File file = new File(dir, GlobalVars.CSVFileName);
	    	//Share content!
	        Intent intent = new Intent();
	        intent.setAction(Intent.ACTION_SEND);
	        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
	        intent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.mailExportCSVSubject));//Testo della mail
	        intent.putExtra(android.content.Intent.EXTRA_TITLE, "EXTRA_TITLE");//Non usato?!
	        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,GlobalVars.CSVFileName); //Titolo della mail e del file
	        intent.setType("text/csv");
	        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
	        startActivity(Intent.createChooser(intent,""));
	        Log.i(method,"End");
		}
		
		

	}

}
