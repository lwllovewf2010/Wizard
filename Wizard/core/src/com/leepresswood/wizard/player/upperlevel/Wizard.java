package com.leepresswood.wizard.player.upperlevel;

import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Wizard extends Player
{
	public float mana_max;
	public float mana_current;
	
	public Wizard(ScreenGame screen)
	{
		super(screen);
	}
}
