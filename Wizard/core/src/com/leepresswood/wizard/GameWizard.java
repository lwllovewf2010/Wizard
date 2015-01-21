package com.leepresswood.wizard;

import com.badlogic.gdx.Game;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.screen.ScreenSplash;

public class GameWizard extends Game 
{
	public Assets assets;

	@Override
	public void create()
	{
		//Assets loaded here. Also loads the main menu.
		this.setScreen(new ScreenSplash(this));		
	}
}