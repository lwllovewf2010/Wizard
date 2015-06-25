package com.leepresswood.wizard.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leepresswood.wizard.GameWizard;

public class DesktopLauncher 
{
	/**
	 * Use this to write the name of the game in the UI box.
	 */
	public static final String GAME_NAME = "Wizard";	
	
	/**
	 * Use this to determine the version of the game in V.U format. 
	 * Example: Version 1 - Update 4 would be version "1.4"
	 * Increment the Update number frequently (weekly?) and the Version
	 * number only when a major feature addition has been made.
	 * Version number should be 0 until first working version
	 * of the game has been produced. Another name for this is "Alpha".
	 * Also note that "1.1" and "1.10" are different versions.
	 * 
	 * Until the game is out of testing stages, "Alpha" or "Beta" will 
	 * be appended to the version number.
	 * 
	 * Version 0.8 Alpha Goals:
	 * Upgradable spells
	 * Upgradable attributes
	 * 
	 * Near Future Goals:
	 * Enemy graphics
	 * Player graphics
	 * Spell graphics
	 * More spells
	 * More wizard types
	 * More enemies
	 * Better GUI
	 */
	public static final String GAME_VERSION = "0.7 Alpha";
	
	/**
	 * Game width will be a constant. Game height will be directly
	 * related to width by a factor of the golden ratio (~1.618). We
	 * are using this ratio because most computer screens use an
	 * approximation of this ration in their width x height proportions
	 * and because it looks better to the human eye.
	 */
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = (int) (((float) GAME_WIDTH) / 1.618f);
	
	public static void main(String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = GAME_NAME + " : " + GAME_VERSION;		
		config.width = GAME_WIDTH;
		config.height = GAME_HEIGHT;
		config.resizable = false;
		
		//config.addIcon(null, null);
		
		new LwjglApplication(new GameWizard(), config);
	}
}
