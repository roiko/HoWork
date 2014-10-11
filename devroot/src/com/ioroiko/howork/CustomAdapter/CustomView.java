package com.ioroiko.howork.CustomAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ioroiko.howork.Stamp;


//questa classe è inutilizzata?
public class CustomView extends TextView{
	
	public Stamp stamp;
	public TextView tvWay;
	public TextView tvStamp;
	public Button btnEdit;
	public Button btnDel;
	
	public CustomView(Context c, AttributeSet attrs, Stamp curStamp)
	{
		super (c,attrs);
		//stamp = curStamp;
		setOnClickListener((OnClickListener) this);
	}
	
	public CustomView(Context c)
	{
		super(c);
		setOnClickListener((OnClickListener) this);
	}
	
	public CustomView(Context c, AttributeSet attrs)
	{
		super(c,attrs);
	}
	
	/*public CustomListItem(Stamp stamp) {
		this.stamp = stamp;
	}*/

}
