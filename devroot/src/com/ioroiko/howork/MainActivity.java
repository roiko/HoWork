package com.ioroiko.howork;



import com.ioroiko.howork.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);//Rocco: removed for now...
        return true;
    }
    
    
    //Finish the activity when user presses Home/Back button
    @Override
    public void onPause()
    {
    	finish();
    	super.onPause();
    }

    
}
