package com.leepresswood.wizard.player.classes;

import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.player.upperlevel.Player;
import com.leepresswood.wizard.player.upperlevel.Wizard;
import com.leepresswood.wizard.screen.ScreenGame;

public class AirWizard extends Player implements Wizard
{

	public AirWizard(ScreenGame screen)
	{
		super(screen);
	}

	@Override
	public void attack(Vector2 click_point)
	{
		super.attack(click_point);
		
	}
}
