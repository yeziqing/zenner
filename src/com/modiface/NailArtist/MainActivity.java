package com.modiface.NailArtist;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.os.Bundle;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	public boolean paused = false;
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        int w = getWindowManager().getDefaultDisplay().getWidth();
        int h = getWindowManager().getDefaultDisplay().getHeight();
      
        setContentView(R.layout.activity_main);
  
    }
	
	public void StartApp(View view) {
		Intent intentStart = new Intent (MainActivity.this, Screen1.class);
		startActivity(intentStart);
    }
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}






	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//recreate();
	}






	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//finish();
	}






	//over ride the back button to do nothing if pressed
	//prevents users from exiting by accident
	@Override
	public void onBackPressed() {
	}


	/*public void fStart(View v) {
		// TODO Auto-generated method stub
		//Intent intent = new Intent (this, MainActivity.class);
		//startActivity(intent);
		Intent intent = new Intent (this, GameStart.class);
		startActivity(intent);
		
	}*/

}

