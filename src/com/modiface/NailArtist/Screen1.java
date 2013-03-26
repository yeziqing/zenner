package com.modiface.NailArtist;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class Screen1 extends Activity implements OnClickListener{
	
	
	private static final int SWIPE_MIN_DISTANCE = 70;// 123
    private static final int SWIPE_MAX_OFF_PATH = 300; //250
    private static final int SWIPE_THRESHOLD_VELOCITY = 30; //200
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    //screen 1 buttons
    public static ImageButton bSkin, bFiler, bScissors;
    
	//screen 2 buttons
	public static ImageButton bOne, bUndo, bApplyAll, bHand;
	public static ImageButton bNailSticker, bNailSet, bNailPolish, bRings, bGems;
	
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
        
      
        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        GameVariables.isRunning = 1;
        //GameView gameView;
        gameView = new GameView (this, w, h);
        game = new FrameLayout(this);  
        GameButtons = new RelativeLayout(this); 
        
        
        
        
        //SCREEN 1
        bFiler = new ImageButton(this);
        Bitmap bmbFiler = BitmapFactory.decodeResource(getResources(), R.drawable.icon_filer_squished_55);
        bFiler.setImageBitmap(bmbFiler);
        bFiler.setBackgroundColor(Color.TRANSPARENT);
        bFiler.setId(7845126);
        
        bScissors = new ImageButton(this);
        Bitmap bmbScissors = BitmapFactory.decodeResource(getResources(), R.drawable.icon_scissors_55);
        bScissors.setImageBitmap(bmbScissors);
        bScissors.setBackgroundColor(Color.TRANSPARENT);
        bScissors.setId(3214871);
        
        bSkin = new ImageButton(this);
        Bitmap bmbSkin = BitmapFactory.decodeResource(getResources(), R.drawable.icon_skin_55);
        bSkin.setImageBitmap(bmbSkin);
        bSkin.setBackgroundColor(Color.TRANSPARENT);
        bSkin.setId(99887711);
        
        //SCREEN 2
        //instantiate buttons and allocate their image resources and ID's
        bOne = new ImageButton(this);      
        Bitmap bmbOne = BitmapFactory.decodeResource(getResources(), R.drawable.icon_clear_all_55);
        bOne.setImageBitmap(bmbOne);
        bOne.setBackgroundColor(Color.TRANSPARENT);
        bOne.setId(996915094);
        
        bUndo = new ImageButton(this);      
        Bitmap bmbUndo = BitmapFactory.decodeResource(getResources(), R.drawable.icon_undo_55);
        bUndo.setImageBitmap(bmbUndo);
        bUndo.setBackgroundColor(Color.TRANSPARENT);
        bUndo.setId(5464645);
        
        bApplyAll = new ImageButton(this);      
        Bitmap bmbApplyAll = BitmapFactory.decodeResource(getResources(), R.drawable.icon_apply_all_55);
        bApplyAll.setImageBitmap(bmbApplyAll);
        bApplyAll.setBackgroundColor(Color.TRANSPARENT);
        bApplyAll.setId(5464852);
        
        bHand = new ImageButton(this);      
        Bitmap bmbHand = BitmapFactory.decodeResource(getResources(), R.drawable.icon_hand_55);
        bHand.setImageBitmap(bmbHand);
        bHand.setBackgroundColor(Color.TRANSPARENT);
        bHand.setId(21344764);
        
        bNailSticker = new ImageButton(this);
        final Bitmap bmbNailSticker = BitmapFactory.decodeResource(getResources(), R.drawable.icon_nail_stickers_55);
        bNailSticker.setImageBitmap(bmbNailSticker);
        bNailSticker.setBackgroundColor(Color.TRANSPARENT);
        bNailSticker.setId(1158744);
        
        bNailSet = new ImageButton(this);
        Bitmap bmbNailSet = BitmapFactory.decodeResource(getResources(), R.drawable.icon_nail_set_55);
        bNailSet.setImageBitmap(bmbNailSet);
        bNailSet.setBackgroundColor(Color.TRANSPARENT);
        bNailSet.setId(9877443);
        
        bNailPolish = new ImageButton(this);
        Bitmap bmbNailPolish = BitmapFactory.decodeResource(getResources(), R.drawable.icon_nail_polish_55);
        bNailPolish.setImageBitmap(bmbNailPolish);
        bNailPolish.setBackgroundColor(Color.TRANSPARENT);
        bNailPolish.setId(8879875);
        
        bRings = new ImageButton(this);
        Bitmap bmbRings = BitmapFactory.decodeResource(getResources(), R.drawable.icon_rings_55);
        bRings.setImageBitmap(bmbRings);
        bRings.setBackgroundColor(Color.TRANSPARENT);
        bRings.setId(5512878);
        
        bGems = new ImageButton(this);
        final Bitmap bmbGems = BitmapFactory.decodeResource(getResources(), R.drawable.icon_gems_55);
        bGems.setImageBitmap(bmbGems);
        bGems.setBackgroundColor(Color.TRANSPARENT);
        bGems.setId(6657332);

        //intialize relative layout parameters
        RelativeLayout.LayoutParams params = new LayoutParams(  
        	RelativeLayout.LayoutParams.FILL_PARENT,  
        	RelativeLayout.LayoutParams.FILL_PARENT);  
        GameButtons.setLayoutParams(params); 
               
        
        //layout rules for bFiler
        RelativeLayout.LayoutParams lrbFiler = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbFiler.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);  
        lrbFiler.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        bFiler.setLayoutParams(lrbFiler);
        
        RelativeLayout.LayoutParams lrbScissors = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbScissors.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lrbScissors.addRule(RelativeLayout.LEFT_OF, bFiler.getId());
        bScissors.setLayoutParams(lrbScissors);
        
        RelativeLayout.LayoutParams lrbSkin = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbSkin.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lrbSkin.addRule(RelativeLayout.RIGHT_OF, bFiler.getId());
        bSkin.setLayoutParams(lrbSkin);
        
        //layout rules for CLEAR ALL (bOne)
        RelativeLayout.LayoutParams lrbOne = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbOne.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
        lrbOne.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        bOne.setLayoutParams(lrbOne);
        
        //layout rules for bUndo
        RelativeLayout.LayoutParams lrbUndo = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbUndo.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
        lrbUndo.addRule(RelativeLayout.BELOW, bOne.getId());
        bUndo.setLayoutParams(lrbUndo);
        
        //layout rules for bApplyAll
        RelativeLayout.LayoutParams lrbApplyAll = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbApplyAll.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
        lrbApplyAll.addRule(RelativeLayout.BELOW, bUndo.getId());
        bApplyAll.setLayoutParams(lrbApplyAll);
        
        //layout rules for bHand
        RelativeLayout.LayoutParams lrbHand = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbHand.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);  
        lrbHand.addRule(RelativeLayout.BELOW, bApplyAll.getId());
        bHand.setLayoutParams(lrbHand);
        
        //layout rules for bNailSticker
        RelativeLayout.LayoutParams lrbNailSticker = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbNailSticker.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);  
        lrbNailSticker.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        bNailSticker.setLayoutParams(lrbNailSticker);
        
        //layout rules for bNailSet
        RelativeLayout.LayoutParams lrbNailSet = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbNailSet.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        lrbNailSet.addRule(RelativeLayout.ABOVE, bNailSticker.getId());
        bNailSet.setLayoutParams(lrbNailSet);
        
        //layout rules for bNailPolish
        RelativeLayout.LayoutParams lrbNailPolish = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbNailPolish.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lrbNailPolish.addRule(RelativeLayout.LEFT_OF, bNailSticker.getId());
        bNailPolish.setLayoutParams(lrbNailPolish);
        
        //layout rules for bRings
        RelativeLayout.LayoutParams lrbRings = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        lrbRings.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lrbRings.addRule(RelativeLayout.RIGHT_OF, bNailSticker.getId());
        bRings.setLayoutParams(lrbRings);
        
        //layout rules for bGems
        RelativeLayout.LayoutParams lrbGems = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT); 
        //lrbGems.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lrbGems.addRule(RelativeLayout.RIGHT_OF, bNailSticker.getId());
        lrbGems.addRule(RelativeLayout.ABOVE, bRings.getId());
        bGems.setLayoutParams(lrbGems);
        
        
        //add SCREEN 1 buttons to the view
        GameButtons.addView(bFiler);
        GameButtons.addView(bScissors);
        GameButtons.addView(bSkin);
        
        //add SCREEN 2 buttons to the view
        GameButtons.addView(bOne);
        GameButtons.addView(bUndo);
        GameButtons.addView(bApplyAll);
        GameButtons.addView(bHand);
        GameButtons.addView(bNailSticker);
        GameButtons.addView(bNailSet);
        GameButtons.addView(bNailPolish);
        GameButtons.addView(bRings);
        GameButtons.addView(bGems);
        
        game.addView(gameView);  
        game.addView(GameButtons); 
        
        //bind the gameView to gesture detector for swiping
        gameView.setOnClickListener(Screen1.this); 
        gameView.setOnTouchListener(gestureListener);
        
        //getWindow().setFormat(PixelFormat.RGBA_8888); 
        setContentView(game);
        
        // NSIN: not sure if need these
        gameView.setFocusable(true);
        gameView.setFocusableInTouchMode(true);
        game.setFocusable(true);
        game.setFocusableInTouchMode(true);
        
        
        initializeButtons();
        
	   	bOne.setOnClickListener(new View.OnClickListener() { //toggle CLEAR ALL button
	         public void onClick(View v) {
	        	 if (GameVariables.listener_bOne == 0) GameVariables.listener_bOne = 1;
	        	 else {GameVariables.listener_bOne = 0; };

	         }
	    });
	   	
	   	bHand.setOnClickListener(new View.OnClickListener() { //Shift Right to goto screen 1
	         public void onClick(View v) {
	        	 GameVariables.swipe = 2;
	         }
	    });
	   	
	   	bNailSticker.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	if (GameVariables.listener_bNailSticker == 0) {
	        		GameVariables.resetTogglables();
	        		GameVariables.listener_bNailSticker = 1;
	        		//bNailSticker.setImageResource(R.drawable.icon_nail_stickers_55_pressed);
	        		resetButtonAlpha();
	        		bNailSticker.setAlpha(50);
	        		GameVariables.showBar = true;
	        	}
	        	else if (GameVariables.listener_bNailSticker == 1){
	        		GameVariables.listener_bNailSticker = 0; 
	        		resetButtonAlpha();
	        		GameVariables.showBar = false;
	        	}

	         }
	    });
	   	
	   	bNailPolish.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	resetTouch(); //reset touch coordinates so clicks in the bar when bar is not showing does not propagate to action afterwards when bar is shown
	        	if (GameVariables.listener_bNailPolish == 0 && GameVariables.swipe == 0) {
	        		GameVariables.resetTogglables();
	        		GameVariables.listener_bNailPolish = 1;
	        		resetButtonAlpha();
	        		bNailPolish.setAlpha(50);
	        		GameVariables.showBar = true;
	        	}
	        	else if (GameVariables.listener_bNailPolish == 1 && GameVariables.swipe == 0){
	        		GameVariables.listener_bNailPolish = 0; 
	        		resetButtonAlpha();
	        		GameVariables.showBar = false;
	        	}

	         }
	    });
	   	
	   	bNailSet.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 resetTouch();
	        	if (GameVariables.listener_bNailSet == 0 && GameVariables.swipe == 0) {
	        		GameVariables.resetTogglables();
	        		GameVariables.listener_bNailSet = 1;
	        		resetButtonAlpha();
	        		bNailSet.setAlpha(50);
	        		GameVariables.showBar = true;
	        	}
	        	else if (GameVariables.listener_bNailSet == 1 && GameVariables.swipe == 0){
	        		GameVariables.listener_bNailSet = 0; 
	        		resetButtonAlpha();
	        		GameVariables.showBar = false;
	        	}

	         }
	    });
	   	
	   	bGems.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 resetTouch();
	        	if (GameVariables.listener_bGems == 0 && GameVariables.swipe == 0) {
	        		GameVariables.resetTogglables();
	        		GameVariables.listener_bGems = 1;
	        		resetButtonAlpha();
	        		bGems.setAlpha(50);
	        		GameVariables.showBar = true;
	        	}
	        	else if (GameVariables.listener_bGems == 1 && GameVariables.swipe == 0){
	        		GameVariables.listener_bGems = 0; 
	        		resetButtonAlpha();
	        		GameVariables.showBar = false;
	        	}

	         }
	    });
	   	
	   	bRings.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 resetTouch();
	        	if (GameVariables.listener_bRings == 0 && GameVariables.swipe == 0) {
	        		GameVariables.resetTogglables();
	        		GameVariables.listener_bRings = 1;
	        		resetButtonAlpha();
	        		bRings.setAlpha(50);
	        		GameVariables.showBar = true;
	        	}
	        	else if (GameVariables.listener_bRings == 1 && GameVariables.swipe == 0){
	        		GameVariables.listener_bRings = 0; 
	        		resetButtonAlpha();
	        		GameVariables.showBar = false;
	        	}

	         }
	    });
        
        
	}
	
	private void initializeButtons() {
		
		//TODO Make screen1 buttons visible

		// Make screen2 buttons INVISIBLE
		bOne.setVisibility(View.GONE);
		bUndo.setVisibility(View.GONE);
		bApplyAll.setVisibility(View.GONE);
		bHand.setVisibility(View.GONE);
		bNailSticker.setVisibility(View.GONE);
		bNailSet.setVisibility(View.GONE);
		bNailPolish.setVisibility(View.GONE);
		bRings.setVisibility(View.GONE);
		bGems.setVisibility(View.GONE);
	}
	
	private void resetButtonAlpha() {
		bOne.setAlpha(255);
		bUndo.setAlpha(255);
		bApplyAll.setAlpha(255);
		bHand.setAlpha(255);
		bNailSticker.setAlpha(255);
		bNailSet.setAlpha(255);
		bNailPolish.setAlpha(255);
		bRings.setAlpha(255);
		bGems.setAlpha(255);
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
      //  finish();
       // System.gc();
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
	
	//Subclass for swiping detection
	class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {                    
                	GameVariables.swipe = 1;resetTouch();
                }  
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	GameVariables.swipe = 2;resetTouch();
                }
                resetTouch(); //reset touch so it won't get mistaken as a click selection
            } catch (Exception e) {
                // nothing
            }
            return true;
        }
        
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
        	
        	//gameView.onTouchEvent(e);
        	if (e.getAction() == MotionEvent.ACTION_DOWN) {
    			GameVariables.touchX = (int) e.getX();
    			GameVariables.touchY = (int) e.getY();
        	}
            return super.onSingleTapConfirmed(e);
        }

    }
	
	public void resetTouch() {
		GameVariables.touchX=0;
	 	GameVariables.touchY=0;
	}

	//Unimplemented method from Gesture thingy
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Filter f = (Filter) v.getTag();
        //FilterFullscreenActivity.show(this, input, f);
		
	}
	
	//Elipses Inflater
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.clear();
		menu.add(0, 0, 0, "ITEM 0");
		menu.add(0, 1, 1, "ITEM 1");
		menu.add(0, 2, 2, "ITEM 2");
		menu.add(0, 3, 3, "ITEM 3");
		return super.onPrepareOptionsMenu(menu);
	}
	
	//Elipses handler
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
		return true;
	}



}
