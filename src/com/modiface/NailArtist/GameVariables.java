package com.modiface.NailArtist;

public class GameVariables {

	public static int touchY = 0;
	public static int pause = 0;
	public static int isRunning = 0;
	
	public static double DSF = 0.55; //Device Scale Factor
	public static int SSF = 8;
	
	public static boolean showBar = false;
	
	//regular buttons
	protected static int listener_bOne = 0;
	
	//togglable buttons
	protected static int listener_bNailSticker = 0;
	protected static int listener_bNailPolish = 0;
	protected static int listener_bRings = 0;
	protected static int listener_bNailSet = 0;
	protected static int listener_bGems = 0;
	
	
	public static int swipe = 0; //0 if user is not swiping | 1 if swiping left was detected | 2 if swiping right was detected
	

	public static void resetTogglables() {
		listener_bNailSticker = 0;
		listener_bNailPolish = 0;
		listener_bRings = 0;
		listener_bNailSet = 0;
		listener_bGems = 0;
	}

}
