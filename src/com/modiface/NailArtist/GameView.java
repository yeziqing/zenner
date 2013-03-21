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
import java.util.Random;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private Context mContext;
	private SensorManager mSensorManager;
	private Display mDisplay;
	private WindowManager mWindowManager;

	public int width, height;
	public int x, y; 

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
		
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = 8;
		
		
		
//		ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipfront);


		
		//gameLoopThread = new GameLoopThread(this);
		
		SurfaceHolder holder = getHolder();
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
		gameLoopThread.start();

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
		
		if (touchX > width/2 && touchY > 6*height/8) {
			shiftLeft();
			drawBackground(canvas);
			drawObject(canvas);
		}
		
		if (touchX < width/2 && touchY > 6*height/8) {
			shiftRight();
			drawBackground(canvas);
			drawObject(canvas);
		}
		
		else {
			drawBackground(canvas);
			drawObject(canvas);
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
		
		
		
		canvas.drawText("Screen1", centerx, height/3, paintText);
		canvas.drawText("Screen2", centerx2, height/3, paintText);
	
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
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
		int speed = width/4;
		if (centerx > width/2 - width) {
			centerx-= speed;
			centerx2-= speed;
		}
		
		
	}
	
	private void shiftRight() {
		int speed = width/4;
		if (centerx2 < width/2 + width) {
			centerx+= speed;
			centerx2+= speed;
		}
		
	}

}

