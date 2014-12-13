package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Wizard extends Humanoid
{	
	public Wizard(ScreenGame screen)
	{
		super(screen);		
	}
	
	@Override
	public void attack(Vector2 click_point)
	{
		//Check for mana first
		
	}
}
