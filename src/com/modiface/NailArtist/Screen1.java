package com.modiface.NailArtist;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Screen1 extends Activity{
	public boolean paused = false;
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		
		/*if (GameVariables.s == 1) {
			restartActivity();
			GameVariables.s = 0;
		}*/
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		// TODO Auto-generated method stub
				super.onStart();
				int w = getWindowManager().getDefaultDisplay().getWidth();
		        int h = getWindowManager().getDefaultDisplay().getHeight();
		        
		        GameVariables.isRunning = 1;
		        GameView gameView;
		        gameView = new GameView (this, w, h);
		        
		        
		        
		        setContentView(gameView);
		
        
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}


	@Override
    protected void onPause() {
        super.onPause();
        GameVariables.pause = 1;
        GameView.gameLoopThread.setRunning(false);
        //finish();
        
        /*synchronized (GameView.gameLoopThread){
	        try {
				GameView.gameLoopThread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        GameView.gameLoopThread.onPause();

    }
	
	@Override
    protected void onResume(){
    	super.onResume();
    	GameVariables.pause = 0;
    	GameVariables.isRunning = 1;
    	//GameView.gameLoopThread.setRunning(true);
    	
    	//synchronized (GameView.gameLoopThread) {    
          //  GameView.gameLoopThread.notify();
        //}
    	
    	GameView.gameLoopThread.setRunning(true);
    	
    	GameView.gameLoopThread.onResume();
    	
    }
	
	/*@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
		finish();
		System.gc();
	}*/


	@Override
    protected void onStop() {
        super.onStop();
        GameView.gameLoopThread.setRunning(false);
        System.gc();
    }
	
	@Override
    protected void onRestart(){
    	super.onRestart();
    	GameView.gameLoopThread.setRunning(true);
    	
    	GameVariables.pause = 0;
    	GameVariables.isRunning = 1;
    	
    	//GameView.gameLoopThread.setRunning(true);
    	
     }
	//over ride the back button to do nothing if pressed
	//prevents users from exiting by accident
	@Override
	public void onBackPressed() {

		//GameStart.finishActivity(0);
	}



}
