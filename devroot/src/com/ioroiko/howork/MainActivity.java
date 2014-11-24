package com.ioroiko.howork;



import com.ioroiko.howork.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Log.d("", "Premuto settings");
			startActivity(new Intent(this, SettingsActivity.class));

			return true;
		}
		return false;
	}
    
    
    //Finish the activity when user presses Home/Back button
    @Override
    public void onPause()
    {
    	finish();
    	super.onPause();
    }

    
}
