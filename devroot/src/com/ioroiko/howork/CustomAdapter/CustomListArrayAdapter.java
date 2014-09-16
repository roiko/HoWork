package com.ioroiko.howork.CustomAdapter;

import java.util.List;

import com.ioroiko.howork.R;
import com.ioroiko.howork.Stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListArrayAdapter extends ArrayAdapter {

	private Context _c;
	private Object[] _objects;
	
	public CustomListArrayAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	public CustomListArrayAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	public CustomListArrayAdapter(Context context, int resource,
			Object[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		_c = context;
		_objects = objects;
	}

	public CustomListArrayAdapter(Context context, int resource, List objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	public CustomListArrayAdapter(Context context, int resource,
			int textViewResourceId, Object[] objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		_c = context;
		_objects = objects;
	}

	public CustomListArrayAdapter(Context context, int resource,
			int textViewResourceId, List objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Stamp stamp = (Stamp) _objects[position];
		
		LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.custom_list_ltem, parent, false);//Una singola riga Stamp+bottoni
				
		TextView tvTime = (TextView) rowView.findViewById(R.id.tvCIStamp);
		tvTime.setText(stamp.getTime());
		TextView tvWay = (TextView) rowView.findViewById(R.id.tvCIWay);
		tvWay.setText(stamp.way.toString());
				
		return rowView;
	
	}

}
