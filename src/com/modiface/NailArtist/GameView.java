package com.modiface.NailArtist;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private Context mContext;
	private SensorManager mSensorManager;
	private Display mDisplay;
	private WindowManager mWindowManager;

	final public int width, height;

	private SurfaceHolder holder;
	public static GameLoopThread gameLoopThread;

	private static int centerx;
	private static int centery;
	
	private static int centerx2;
	private static int centery2;
	
	public static int screenID = 0;
	public static int touchX;
	public static int touchY;
	
	public static boolean shifting = false;

	private Paint middle = new Paint();
	private Paint middle2 = new Paint();
	private Paint paintBG = new Paint();
	private Paint paintText = new Paint();
	
	public static Bitmap test;
	public static Bitmap testScaled;


	
	public GameView(Context context, int w, int h) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		mContext = context;

		width = w;
		height = h;
		centerx = w/2;
		centery = h/2;
		centerx2 = w/2 + w;

		paintBG.setColor(Color.YELLOW);
		middle.setColor(Color.CYAN);
		middle2.setColor(Color.GREEN);
		
		paintText.setColor(Color.BLACK);
		paintText.setTextAlign(Align.CENTER);
		
		//BitmapFactory.Options options=new BitmapFactory.Options();
		//options.inSampleSize = 8;
		
		test = BitmapFactory.decodeResource(getResources(), R.drawable.mainfile);
		testScaled = Bitmap.createScaledBitmap(test, width, height, true);
		
		
//		ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);


		
		//gameLoopThread = new GameLoopThread(this);
		
	    holder = getHolder();
        holder.addCallback(this);
		//holder = getHolder();
		//holder.addCallback(new Callback() {});
		
		
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		

	}


		
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		// gameLoopThread.setRunning(true);
	}

		
	// reserves and locks the canvas
	public void surfaceCreated(SurfaceHolder holder) {

		gameLoopThread.setRunning(true);
		try { 
			gameLoopThread.start();
		} catch (IllegalThreadStateException e) {
			
		}
		

	}

			
			
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		/*
		 * boolean retry = true; gameLoopThread.setRunning(false); while (retry)
		 * { try { gameLoopThread.join(); retry = false; } catch
		 * (InterruptedException e) {} }
		 */
	}

	@Override
	protected void onDraw(Canvas canvas) {	
		
		//Conditions for shifting:
		//1. touch coordinates must be on the parameter specified
		//2. transitioning hasn't arrived at destination coordinates yet
		if ((touchX > width/2 && touchY > 7*height/8 && centerx > width/2 - width) || GameVariables.swipe == 1) { //Boundaries for shift left virtual space
			
			if (centerx <= width/2-width) GameVariables.swipe=0; //try to reset to "not swiping" state
			
			shiftLeft();
			drawBackground(canvas);
			canvas.drawBitmap(testScaled, centerx2-testScaled.getWidth()/2, 0, null);
			drawObject(canvas);
			
			Screen1.bOne.getHandler().post(new Runnable() { //make the button invisible because we're on screen2 now
			    public void run() {
			        Screen1.bOne.setVisibility(View.GONE);
			    }
			});
		}
		
		if ((touchX < width/2 && touchY > 7*height/8 && centerx2 < width/2 + width) || GameVariables.swipe == 2) { //Boundaries for shift right virtual space
			
			if (centerx2 >= width/2 + width) GameVariables.swipe = 0;
			
			shiftRight();
			drawBackground(canvas);
			canvas.drawBitmap(testScaled, centerx2-testScaled.getWidth()/2, 0, null);
			drawObject(canvas);
			
			Screen1.bOne.getHandler().post(new Runnable() {//make the button visible because we're on screen1 now
			    public void run() {
			        Screen1.bOne.setVisibility(View.VISIBLE);
			    }
			});
		}
		
		
		
		else { //draw the normal state, where no action is happening, just have the background stuff
			drawBackground(canvas);
			canvas.drawBitmap(testScaled, centerx2-testScaled.getWidth()/2, 0, null);// draw the hand
			drawObject(canvas);
			if (GameVariables.listener_bOne == 1) { //Demonstrating button click actions in surface view. Draw a black square on click.
				canvas.drawRect(centerx+0, centery - 300, centerx+100, centery - 200, paintText);
			}
		}
	}
	
	public void drawBackground (Canvas canvas){
		canvas.drawRect(0, 0, width, height, paintBG);
	}

	public void drawObject(Canvas canvas) {

		canvas.drawRect(centerx-100, centery-100,
				centerx + 100, centery + 100, middle);
		canvas.drawRect(centerx2-100, centery-100,
				centerx2 + 100, centery + 100, middle2);
		
		
		
		canvas.drawText("Screen " + Integer.toString(GameVariables.swipe), centerx, height/3, paintText);
		canvas.drawText("Screen " + Integer.toString(GameVariables.swipe), centerx2, height/3, paintText);
	
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void fakeTouch( View view) {
		view.setOnTouchListener(new OnTouchListener()
		{
		    public boolean onTouch(View v, MotionEvent event)
		    {
		        

		        return true;
		    }
		});


		// Obtain MotionEvent object
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis() + 100;
		float x = 0.0f;
		float y = 0.0f;
		// List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
		int metaState = 0;
		MotionEvent motionEvent = MotionEvent.obtain(
		    downTime, 
		    eventTime, 
		    MotionEvent.ACTION_UP, 
		    x, 
		    y, 
		    metaState
		);

		// Dispatch touch event to view
		view.dispatchTouchEvent(motionEvent);
	}

	
	public boolean onTouchEvent(MotionEvent event) {
		// get touched coordinates
		//GameVariables.touchY = (int) event.getY();
		
		//int eID = event.getAction();
		touchX = (int) event.getX();
		touchY = (int) event.getY();
		
		//if (eID == MotionEvent.ACTION_DOWN) shift();
		
		return true;
	}

	private void shiftLeft() {
		int speed = width/8;
		if (centerx > width/2 - width) {
			centerx-= speed;
			centerx2-= speed;
		}
		
		
	}
	
	private void shiftRight() {
		int speed = width/8;
		if (centerx2 < width/2 + width) {
			centerx+= speed;
			centerx2+= speed;
		}
		
	}

}

