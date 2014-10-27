package com.ioroiko.howork.SummaryAdapter;

import java.util.List;

import com.ioroiko.howork.R;
import com.ioroiko.howork.Stamp;
import com.ioroiko.howork.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
	        
	        DayStamp dayStamp = getItem(position);
	        //Rocco: (miglioramento) qui usare il locale
	        tvDate.setText(String.format("%s/%s/%s - %s", dayStamp.Day, dayStamp.Month, dayStamp.Year, Utils.GetDayNameFromDate(convertView.getContext(), dayStamp.Day, dayStamp.Month,dayStamp.Year)));
	        String sStamps = "";
	        for (Stamp stamp : dayStamp.Stamps) {
				sStamps +=  String.format(" %s\t\t %s\n", stamp.getTime(),stamp.way.toString());
			}

	        tvStamps.setText(sStamps);
	        return convertView;
	
	}

}
