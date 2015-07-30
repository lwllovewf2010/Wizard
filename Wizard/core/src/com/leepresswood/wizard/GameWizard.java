package com.leepresswood.wizard;

import com.badlogic.gdx.Game;
import com.leepresswood.wizard.helpers.Assets;
import com.leepresswood.wizard.screens.splash.ScreenSplash;

public class GameWizard extends Game 
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
	 * Version 0.12 Alpha Goals:
	 * =========================
	 * Dynamic spell selection from level store.
	 * Level up costs multiple points for high-level items.
	 * 
	 * Near Future Goals:
	 * ==================
	 * Enemy graphics
	 * Player graphics
	 * Spell graphics
	 * More spells
	 * More wizard types
	 * More enemies
	 */
	public static final String GAME_VERSION = "0.12 Alpha";
	
	public Assets assets;

	@Override
	public void create()
	{
		//Assets loaded here. Also loads the main menu.
		this.setScreen(new ScreenSplash(this));		
	}
}