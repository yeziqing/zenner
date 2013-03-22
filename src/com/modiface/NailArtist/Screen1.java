package com.modiface.NailArtist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Screen1 extends Activity{
	public boolean paused = false;
	public static Button bOne;
	
	GameView gameView;//extends SurfaceView   
	FrameLayout game;// Sort of "holder" for everything we are placing  
	RelativeLayout GameButtons;//Holder for the buttons  
	
	
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
				//super.onStart();
	
		
        
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		int w = getWindowManager().getDefaultDisplay().getWidth();
        int h = getWindowManager().getDefaultDisplay().getHeight();
        
        GameVariables.isRunning = 1;
        //GameView gameView;
        gameView = new GameView (this, w, h);
        game = new FrameLayout(this);  
        GameButtons = new RelativeLayout(this); 
        
        bOne = new Button(this);  
        bOne.setText("Paint!");  
        bOne.setId(996915094);
        
        RelativeLayout.LayoutParams params = new LayoutParams(  
        	RelativeLayout.LayoutParams.FILL_PARENT,  
        	RelativeLayout.LayoutParams.FILL_PARENT);  
        GameButtons.setLayoutParams(params); 
               
        
        
        RelativeLayout.LayoutParams b1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
        b1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        bOne.setLayoutParams(b1); 
        
        GameButtons.addView(bOne);
        game.addView(gameView);  
        game.addView(GameButtons);  
        setContentView(game);
        
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
        game.setFocusable(true);
        game.setFocusableInTouchMode(true);
        
        
	   	bOne.setOnClickListener(new View.OnClickListener() { //toggle button
	         public void onClick(View v) {
	        	 
	        	 if (GameVariables.listener_bOne == 0) GameVariables.listener_bOne = 1;
	        	 else {GameVariables.listener_bOne = 0; };
	        	// gameView.requestFocus();
	        	// game.requestFocus();
	         }
	     });
        
        
        //setContentView(gameView);
	}
	
	public void refocus (View v) {
		
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
    	GameView.gameLoopThread.onResume();
    	GameView.gameLoopThread.setRunning(true);
    	
    	
    	
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
