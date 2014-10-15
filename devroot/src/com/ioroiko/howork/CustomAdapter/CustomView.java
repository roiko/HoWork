package com.ioroiko.howork.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ioroiko.howork.EditStampDialog;
import com.ioroiko.howork.Stamp;


public class CustomView extends TextView{
	
	public Stamp stamp;
	public TextView tvWay;
	public TextView tvStamp;
	public Button btnEdit;
	public Button btnDel;
	
	public CustomView(Context c, AttributeSet attrs, Stamp curStamp)
	{
		super (c,attrs);
	}
	
	public CustomView(Context c)
	{
		super(c);
	}
	
	public CustomView(Context c, AttributeSet attrs)
	{
		//Viene chiamato questo dal CustomListArrayAdapter
		super(c,attrs);
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CustomView thisCustomView = (CustomView) v;
				//Toast.makeText(v.getContext(), a.stamp.getTime(), Toast.LENGTH_SHORT).show();
				
				//Creo il dialog per aggiornare lo stamp
				EditStampDialog myDialog = new EditStampDialog();
				myDialog.diaStamp = thisCustomView.stamp;
				
				//Mostro il dialog (viene chiamato onCreateDialog())
				Activity curAct = (Activity) v.getContext();
				myDialog.show(curAct.getFragmentManager(),"stocazzo");
			}
		});
	}
	
	
	public void OnClick(View v)
	{
		Toast.makeText(v.getContext(), "Onclick!", Toast.LENGTH_SHORT).show();
	}


}
