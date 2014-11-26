package com.ioroiko.howork.CustomAdapter;

import java.util.List;

import com.ioroiko.howork.GlobalVars;
import com.ioroiko.howork.R;
import com.ioroiko.howork.Stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
	
	
	private static class ViewHolder{
		CustomView cvStamp;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Stamp stamp = (Stamp) _objects[position];
		ViewHolder mycustomView = null;
		LayoutInflater inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.custom_list_ltem, parent, false);//Una singola riga Stamp+bottoni
			mycustomView = new ViewHolder();
			mycustomView.cvStamp = (CustomView) convertView.findViewById(R.id.tvCIStamp);
			convertView.setTag(mycustomView);
		}
		else
		{
			mycustomView = (ViewHolder) convertView.getTag(); //riuso inflate precedente (best practice)
		}
		
		String text = String.format("%s\t\t%s", stamp.getTime(),stamp.way.toString());
		mycustomView.cvStamp.setText(text);
		mycustomView.cvStamp.stamp = stamp;
		if (stamp.way.equals(GlobalVars.Way.IN))
			mycustomView.cvStamp.setTextColor(convertView.getResources().getColor(R.color.greenHoWork));
		else
			mycustomView.cvStamp.setTextColor(convertView.getResources().getColor(R.color.redHoWork));
		
		
		return convertView;
	
	}

}
