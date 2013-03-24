package com.modiface.NailArtist;

import android.graphics.Canvas;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ToneGenerator;
import android.os.Handler;

public class GameLoopThread extends Thread {
	
	
	private Object mPauseLock;
	private boolean mPaused;
	private boolean mFinished;

	AudioTrack track;
	//static final long FPS = 60;
	private GameView view;
	private boolean running = false;
       
       Handler handler = new Handler();
      
       public GameLoopThread(GameView view) {
             this.view = view;
             
             mPauseLock = new Object();
             mPaused = false;
             mFinished = false;
       }
 
       public void setRunning(boolean run) {
             running = run;
             GameVariables.isRunning = 1;
       }
 
       @Override
       public void run() {
    	   	long ticksPS = 1000/GameVariables.FPS; //100 is minimum in which each loop has to last
    	   	long startTime;
    	   	long sleepTime;
    	   	
    	   	
             while (running) {
            	 
                    Canvas c = null;
                    startTime = System.currentTimeMillis();
                    
                    try {
                           c = view.getHolder().lockCanvas();
                           synchronized (view.getHolder()) {
                        	   view.onDraw(c);
                        	   
                        	 
                        	   //view.drawBackground(c);

                        		   
                           }

                    } finally {
                           if (c != null) {
                                  view.getHolder().unlockCanvasAndPost(c);
                           }
                    }
                    
                    sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                    try {
                        if (sleepTime > 0)
                               sleep(sleepTime);
                       // else
                               //sleep(10);
                 } catch (Exception e) {}
                    
                    
                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
             }
             
       }
       
       public void onPause() {
           synchronized (mPauseLock) {
               mPaused = true;
           }
           
           
       }
       
       public void onResume() {
           synchronized (mPauseLock) {
               mPaused = false;
               mPauseLock.notifyAll();
           }
       }

}  

