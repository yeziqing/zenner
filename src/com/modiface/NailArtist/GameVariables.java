package com.modiface.NailArtist;

public class GameVariables {

	public static int touchY = 0;
	public static int touchX = 0;
	
	public static int pause = 0;
	public static int isRunning = 0;
	
	final public static int FPS = 120;
	public static double DSF = 0.55; //Device Scale Factor
	public static int SSF = 8;
	
	public static boolean showBar = false;
	
	//regular buttons
	protected static int listener_bOne = 0;
	
	//screen 1 togglable buttons
	protected static int listener_bSkin = 0;
	protected static int listener_bFiler = 0;
	protected static int listener_bScissors = 0;
	
	//screen 2 togglable buttons
	protected static int listener_bNailSticker = 0;
	protected static int listener_bNailPolish = 0;
	protected static int listener_bRings = 0;
	protected static int listener_bNailSet = 0;
	protected static int listener_bGems = 0;
	
	
	//togglable's screens: tells you which page it is
		protected static int menu_id_nail_polish = 1; //nail polish
		protected static int menu_id_nail_set = 1; // nail hubcaps/sets
		protected static int menu_id_nail_stickers = 1; //nail stickers
		
	public static int swipe = 0; //0 if user is not swiping | 1 if swiping left was detected | 2 if swiping right was detected
	

	public static void resetTogglables() {
		listener_bSkin = 0;
		listener_bFiler = 0;
		listener_bScissors = 0;
		
		listener_bNailSticker = 0;
		listener_bNailPolish = 0;
		listener_bRings = 0;
		listener_bNailSet = 0;
		listener_bGems = 0;
	}

}
