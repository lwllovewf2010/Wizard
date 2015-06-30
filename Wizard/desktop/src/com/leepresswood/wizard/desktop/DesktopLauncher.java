package com.leepresswood.wizard.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leepresswood.wizard.GameWizard;

public class DesktopLauncher 
{	
	/**
	 * Game width will be a constant. Game height will be directly
	 * related to width by a factor of the golden ratio (~1.618). We
	 * are using this ratio because most computer screens use an
	 * approximation of this ration in their width x height proportions
	 * and because it looks better to the human eye.
	 */
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = (int) (GAME_WIDTH / 1.618f);
	
	public static void main(String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = GameWizard.GAME_NAME + " : " + GameWizard.GAME_VERSION;		
		config.width = GAME_WIDTH;
		config.height = GAME_HEIGHT;
		config.resizable = false;
		
		//config.addIcon(null, null);
		
		new LwjglApplication(new GameWizard(), config);
	}
}
