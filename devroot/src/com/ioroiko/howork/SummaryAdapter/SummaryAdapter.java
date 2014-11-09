package com.ioroiko.howork.SummaryAdapter;

import java.util.ArrayList;
import java.util.List;

import com.ioroiko.howork.ActivityEdit;
import com.ioroiko.howork.GlobalVars;
import com.ioroiko.howork.R;
import com.ioroiko.howork.Stamp;
import com.ioroiko.howork.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SummaryAdapter extends ArrayAdapter<DayStamp> {

	public SummaryAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	public SummaryAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public SummaryAdapter(Context context, int resource, DayStamp[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public SummaryAdapter(Context context, int resource, List<DayStamp> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public SummaryAdapter(Context context, int resource,
			int textViewResourceId, DayStamp[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	public SummaryAdapter(Context context, int resource,
			int textViewResourceId, List<DayStamp> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
	             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	        convertView = inflater.inflate(R.layout.day_stamp, null);
	        TextView tvDate = (TextView)convertView.findViewById(R.id.tvDate);
	        TextView tvStamps = (TextView)convertView.findViewById(R.id.tvStamps);
	        
	        final DayStamp dayStamp = getItem(position);
	        tvDate.setText(String.format("%s/%s/%s - %s", dayStamp.Day, dayStamp.Month, dayStamp.Year, Utils.GetDayNameFromDate(convertView.getContext(), dayStamp.Day, dayStamp.Month,dayStamp.Year)));
	        String sStamps = "";
	       // ArrayList<Spannable> spannables = new ArrayList<Spannable>();
	        
	        for (Stamp stamp : dayStamp.Stamps) {
	        	
	        	Spannable tempSpannable = new SpannableString(stamp.getTime()+"\n");
	        	if (stamp.way.toString().equals(GlobalVars.Way.IN.toString()))
	        	tempSpannable.setSpan(new ForegroundColorSpan(convertView.getResources().getColor(R.color.greenHoWork)), 0, tempSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	        	else
	        		tempSpannable.setSpan(new ForegroundColorSpan(convertView.getResources().getColor(R.color.redHoWork)), 0, tempSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);	
	        	//spannables.add(tempSpannable);
	        	tvStamps.append(tempSpannable);
				//sStamps +=  String.format(" %s\t\t %s\n", stamp.getTime(),stamp.way.toString());
				
				
			}
	        
	        tvStamps.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//Open ActivityEdit
					Intent intentEdit = new Intent(getContext(), ActivityEdit.class);
					intentEdit.setAction(GlobalVars.BTN_EDIT_CLICK);
					intentEdit.putExtra(GlobalVars.EXTRA_YEAR, dayStamp.Year);
					intentEdit.putExtra(GlobalVars.EXTRA_MONTH, dayStamp.Month);
					intentEdit.putExtra(GlobalVars.EXTRA_DAY, dayStamp.Day);
					getContext().startActivity(intentEdit);
				}
			});

	        //tvStamps.setText(sStamps);
	        return convertView;
	
	}

}
