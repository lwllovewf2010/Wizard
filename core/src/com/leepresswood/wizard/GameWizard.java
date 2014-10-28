package com.leepresswood.wizard;

import com.badlogic.gdx.Game;

public class GameWizard extends Game 
{
	@Override
	public void create()
	{
		setScreen(new ScreenGame(this));
	}
}
