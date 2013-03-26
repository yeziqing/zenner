package com.modiface.NailArtist;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PixelFormat;
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

	private static int centerx; //everything on screen1 is drawn relative to centerx's value
	private static int centery;
	
	private static int centerx2;//everything on screen2 is drawn relative to centerx's value
	
	public static int screenID = 0;
	//public static int GameVariables.touchX;
	//public static int GameVariables.touchY;
	
	private Paint middle = new Paint();
	private Paint middle2 = new Paint();
	private Paint paintBG = new Paint();
	private Paint paintText = new Paint();
	
	public static Bitmap bg_pink_screen1, bg_pink_screen2; //layer1
	public static Bitmap bg_polish; //layer1 alternative with gradients
	public static Bitmap bg_hand, bg_hand_scaled; //layer2
	public static Bitmap bg_table, bg_table_scaled; //layer3
	public static Bitmap bg_bar,bg_bar_scaled; //layer4
	
	public static BitmapFactory.Options options, options3, oPolishGradient;
	
	//menu pics
	public static Bitmap bg_bar_polish_1, bg_bar_polish_2, bg_bar_polish_3, bg_bar_polish_4, bg_bar_polish_5, bg_bar_polish_6;
	public static Bitmap bg_bar_polish_1_scaled, bg_bar_polish_2_scaled, bg_bar_polish_3_scaled, bg_bar_polish_4_scaled, bg_bar_polish_5_scaled, bg_bar_polish_6_scaled;

	
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
		
		//use this Bitmap.option for drawing hand_nothing 1&2
		options = new BitmapFactory.Options();
		options.inScaled = false;
		options.inDither = false;
		options.inSampleSize = 1;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		
		//use this Bitmap.option for drawing nail polish menu bars
		options3 = new BitmapFactory.Options();
		options3.inScaled = false;
		options3.inDither = false;
		options3.inSampleSize = 2;
		options3.inPreferQualityOverSpeed = false;
		
		//use this option to draw the gradient background layer for nail polish colours
		oPolishGradient = new BitmapFactory.Options();
		oPolishGradient.inScaled = false;
		oPolishGradient.inDither = false;
		oPolishGradient.inSampleSize = 1;
		oPolishGradient.inPreferQualityOverSpeed = false;
		//oPolishGradient.inPreferredConfig = Bitmap.Config.ARGB_8888;
		
		//bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pink_screen1, options);
		//bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pink_screen2, options);
		bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1, options);
		bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2, options);
		
		
		bg_hand = BitmapFactory.decodeResource(getResources(), R.drawable.bg_hand, options);
		bg_hand_scaled = Bitmap.createScaledBitmap(bg_hand, (int) (bg_hand.getWidth()*GameVariables.DSF), (int) (bg_hand.getHeight()*GameVariables.DSF), true);
		
		bg_table = BitmapFactory.decodeResource(getResources(), R.drawable.bg_table, options);
		bg_table_scaled = Bitmap.createScaledBitmap(bg_table, width, height/4, true);

		bg_bar = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar, options);
		bg_bar_scaled = Bitmap.createScaledBitmap(bg_bar, width, height/6, true);

		bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.color1, options); //this is the gradient layer
		
		//bar menus
			//nail polish
			bg_bar_polish_1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_1, options3);
			bg_bar_polish_1_scaled = Bitmap.createScaledBitmap(bg_bar_polish_1, width, height/6, true);
			
			bg_bar_polish_2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_2, options3);
			bg_bar_polish_2_scaled = Bitmap.createScaledBitmap(bg_bar_polish_2, width, height/6, true);
			
			bg_bar_polish_3 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_3, options3);
			bg_bar_polish_3_scaled = Bitmap.createScaledBitmap(bg_bar_polish_3, width, height/6, true);
			
			bg_bar_polish_4 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_4, options3);
			bg_bar_polish_4_scaled = Bitmap.createScaledBitmap(bg_bar_polish_4, width, height/6, true);
			
			bg_bar_polish_5 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_5, options3);
			bg_bar_polish_5_scaled = Bitmap.createScaledBitmap(bg_bar_polish_5, width, height/6, true);
			
			bg_bar_polish_6 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_bar_polish_6, options3);
			bg_bar_polish_6_scaled = Bitmap.createScaledBitmap(bg_bar_polish_6, width, height/6, true);
			
		
		//initate canvas holder
	    holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);

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
		if (GameVariables.showBar == false && GameVariables.swipe == 1) { //Boundaries for shift left virtual space
			
			if (centerx <= width/2-width) GameVariables.swipe=0; //try to reset to "not swiping" state
			
			toggleButtonVisibility(2); //make all buttons for screen 2 visible and all buttons for screen 1 INVISIBLE
			shiftLeft();
			drawBase(canvas);
			canvas.drawBitmap(bg_polish, centerx2-width/2, 0, null);
			canvas.drawBitmap(bg_pink_screen1, centerx-width/2, 0, null);
			canvas.drawBitmap(bg_pink_screen2, centerx2-width/2, 0, null);
			canvas.drawBitmap(bg_table_scaled, centerx-width/2, 3*(height/4), null);
			canvas.drawBitmap(bg_table_scaled, centerx2-width/2, 3*(height/4), null);
			drawBar(canvas);
			drawObject(canvas);

		}
		
		else if (GameVariables.showBar == false && GameVariables.swipe == 2) { //Boundaries for shift right virtual space
			
			if (centerx2 >= width/2 + width) GameVariables.swipe = 0;
			
			toggleButtonVisibility(1); //make all buttons for screen 1 visible and all buttons for screen 2 INVISIBLE
			shiftRight();
			drawBase(canvas);
			canvas.drawBitmap(bg_polish, centerx2-width/2, 0, null);
			canvas.drawBitmap(bg_pink_screen1, centerx-width/2, 0, null);
			canvas.drawBitmap(bg_pink_screen2, centerx2-width/2, 0, null);
			canvas.drawBitmap(bg_table_scaled, centerx-width/2, 3*(height/4), null);
			canvas.drawBitmap(bg_table_scaled, centerx2-width/2, 3*(height/4), null);
			drawBar(canvas);
			drawObject(canvas);
				
		}

		else { //draw the normal state, where no action is happening, just have the background stuff
			GameVariables.swipe = 0;
			
			drawBase(canvas); //base yellow layer to avoid tearing during transitions
			canvas.drawBitmap(bg_polish, centerx2-width/2, 0, null); //draw the gradient layer
			canvas.drawBitmap(bg_pink_screen1, centerx-width/2, 0, null); //screen 1 hand
			canvas.drawBitmap(bg_pink_screen2, centerx2-width/2, 0, null); //screen 2 hand
			canvas.drawBitmap(bg_table_scaled, centerx-width/2, 3*(height/4), null); //screen 1 table
			canvas.drawBitmap(bg_table_scaled, centerx2-width/2, 3*(height/4), null); // screen 2 table
			drawBar(canvas); //draw the menu bar translucent rectangle (conditional)
			drawObject(canvas); //some squares for debugging
			
			if (GameVariables.listener_bOne == 1) { //Demonstrating button click actions in surface view. Draw a black square on click.
				canvas.drawRect(centerx+0, centery - 300, centerx+100, centery - 200, paintText);
			}
		}
	}
	
	private void drawBar(Canvas canvas) {
		// TODO Auto-generated method stub	
		if (GameVariables.showBar == true) {
			canvas.drawBitmap(bg_bar_scaled, centerx2-width/2, 4*height/7, null);
			
			if (GameVariables.listener_bNailPolish == 1) { //nail polish button is activated
				
				drawBar_Polish(canvas);
				
			}
		}
	}

	private void drawBar_Polish(Canvas canvas) {
		// TODO Auto-generated method stub
		if (GameVariables.swipe == 1) {
			GameVariables.swipe = 0;
			if (GameVariables.menu_id_nail_polish <6) GameVariables.menu_id_nail_polish++;
		}
		else if (GameVariables.swipe == 2) {
			GameVariables.swipe = 0;
			if (GameVariables.menu_id_nail_polish >1) GameVariables.menu_id_nail_polish--;
		}
	
		switch (GameVariables.menu_id_nail_polish) {
		
			
			case 1:
				canvas.drawBitmap(bg_bar_polish_1_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish1, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish2, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*2 && GameVariables.touchX < (width/8)*3 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish3, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*3 && GameVariables.touchX < (width/8)*4 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish4, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*4 && GameVariables.touchX < (width/8)*5 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish5, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*5 && GameVariables.touchX < (width/8)*6 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish6, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*6 && GameVariables.touchX < (width/8)*7 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish7, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*7 && GameVariables.touchX < (width/8)*8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish8, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
			case 2:
				canvas.drawBitmap(bg_bar_polish_2_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish9, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish10, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*2 && GameVariables.touchX < (width/8)*3 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish11, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*3 && GameVariables.touchX < (width/8)*4 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish12, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*4 && GameVariables.touchX < (width/8)*5 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish13, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*5 && GameVariables.touchX < (width/8)*6 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish14, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*6 && GameVariables.touchX < (width/8)*7 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish15, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*7 && GameVariables.touchX < (width/8)*8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish16, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
			case 3:
				canvas.drawBitmap(bg_bar_polish_3_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish17, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish18, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*2 && GameVariables.touchX < (width/8)*3 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish19, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*3 && GameVariables.touchX < (width/8)*4 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish20, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*4 && GameVariables.touchX < (width/8)*5 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish21, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*5 && GameVariables.touchX < (width/8)*6 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish22, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*6 && GameVariables.touchX < (width/8)*7 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish23, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*7 && GameVariables.touchX < (width/8)*8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish24, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
			case 4:
				canvas.drawBitmap(bg_bar_polish_4_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish25, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish26, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*2 && GameVariables.touchX < (width/8)*3 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish27, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*3 && GameVariables.touchX < (width/8)*4 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish28, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*4 && GameVariables.touchX < (width/8)*5 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish29, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*5 && GameVariables.touchX < (width/8)*6 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish30, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*6 && GameVariables.touchX < (width/8)*7 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish31, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*7 && GameVariables.touchX < (width/8)*8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish32, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
			case 5:
				canvas.drawBitmap(bg_bar_polish_5_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish33, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish34, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*2 && GameVariables.touchX < (width/8)*3 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish35, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*3 && GameVariables.touchX < (width/8)*4 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish36, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*4 && GameVariables.touchX < (width/8)*5 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish37, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*5 && GameVariables.touchX < (width/8)*6 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish38, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*6 && GameVariables.touchX < (width/8)*7 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish39, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > (width/8)*7 && GameVariables.touchX < (width/8)*8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish40, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
			case 6:
				canvas.drawBitmap(bg_bar_polish_6_scaled, centerx2-width/2, 4*height/7, null);
				if (GameVariables.touchX > 0 && GameVariables.touchX < width/8 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish41, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
				if (GameVariables.touchX > width/8 && GameVariables.touchX < (width/8)*2 && GameVariables.touchY > 4*height/7 && GameVariables.touchY < 4*height/7+ bg_bar_polish_1_scaled.getHeight()) {
					bg_polish = BitmapFactory.decodeResource(getResources(), R.drawable.polish42, oPolishGradient); //set the gradient
					bg_pink_screen1 = BitmapFactory.decodeResource(getResources(), R.drawable.hand1_nothing, options);
					bg_pink_screen2 = BitmapFactory.decodeResource(getResources(), R.drawable.hand2_nothing, options);
				}
			break;
		}
	}



	public void drawBase (Canvas canvas){
		canvas.drawRect(0, 0, width, height, paintBG);
	}

	public void drawObject(Canvas canvas) {

		canvas.drawRect(centerx-width/2, centery-height/2,
				centerx-width/2 + 20, centery-height/2 + 20, middle);
		canvas.drawRect(centerx2-width/2, centery-height/2,
				centerx2-width/2 + 20, centery-height/2 + 20, middle2);
			
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

	
	/*public boolean onTouchEvent(MotionEvent e) {
		// get touched coordinates
		//GameVariables.GameVariables.touchY = (int) event.getY();
		
		//int eID = event.getAction();
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			GameVariables.touchX = (int) e.getX();
			GameVariables.touchY = (int) e.getY();
		}
		
		//if (eID == MotionEvent.ACTION_DOWN) shift();
		
		return true;
	}*/

	private void shiftLeft() {
		int speed = width/GameVariables.SSF;
		if (centerx > width/2 - width) {
			centerx-= speed;
			centerx2-= speed;
		}
		
	}
	
	private void shiftRight() {
		int speed = width/GameVariables.SSF;
		if (centerx2 < width/2 + width) {
			centerx+= speed;
			centerx2+= speed;
		}
		
	}
	
	public void toggleButtonVisibility(int screen_id) {
		
		switch(screen_id) {
		
			case 1: //if we're on screen 1, set all screen 2 buttons invisible and set all screen1 buttons visible
				
				//The following are screen 2 buttons. Make them invisible
				Screen1.bOne.getHandler().post(new Runnable() { //make the button invisible because we're on screen1 now
				    public void run() {
				        Screen1.bOne.setVisibility(View.GONE);
				    }
				});
				Screen1.bUndo.getHandler().post(new Runnable() { //make the button invisible because we're on screen1 now
				    public void run() {
				        Screen1.bUndo.setVisibility(View.GONE);
				    }
				});
				Screen1.bApplyAll.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bApplyAll.setVisibility(View.GONE);
				    }
				});
				Screen1.bHand.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bHand.setVisibility(View.GONE);
				    }
				});
				Screen1.bNailSticker.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bNailSticker.setVisibility(View.GONE);
				    }
				});
				Screen1.bNailSet.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bNailSet.setVisibility(View.GONE);
				    }
				});
				Screen1.bNailPolish.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bNailPolish.setVisibility(View.GONE);
				    }
				});
				Screen1.bRings.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bRings.setVisibility(View.GONE);
				    }
				});
				Screen1.bGems.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bGems.setVisibility(View.GONE);
				    }
				});
				
				//The following are screen 1 buttons, make them visible
				Screen1.bFiler.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bFiler.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bScissors.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bScissors.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bSkin.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bSkin.setVisibility(View.VISIBLE);
				    }
				});
				
				
				break;
				
				
			case 2:
				
				//The following are screen 1 buttons. Make them invisible
				Screen1.bFiler.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bFiler.setVisibility(View.GONE);
				    }
				});
				Screen1.bScissors.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bScissors.setVisibility(View.GONE);
				    }
				});
				Screen1.bSkin.getHandler().post(new Runnable() {
				    public void run() {
				        Screen1.bSkin.setVisibility(View.GONE);
				    }
				});
				
				//The following are screen 2 buttons. Make them visible
				Screen1.bOne.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bOne.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bUndo.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bUndo.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bApplyAll.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bApplyAll.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bHand.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bHand.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bNailSticker.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bNailSticker.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bNailSet.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bNailSet.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bNailPolish.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bNailPolish.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bRings.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bRings.setVisibility(View.VISIBLE);
				    }
				});
				Screen1.bGems.getHandler().post(new Runnable() {//make the button visible because we're on screen2 now
				    public void run() {
				        Screen1.bGems.setVisibility(View.VISIBLE);
				    }
				});
				
				
				
				
				break;
			
		}
	}

}

