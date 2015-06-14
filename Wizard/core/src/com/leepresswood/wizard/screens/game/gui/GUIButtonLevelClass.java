package com.leepresswood.wizard.screens.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class GUIButtonLevelClass extends GUIButton
{
	
	public GUIButtonLevelClass(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void update(float delta)
	{
		//This button will not be active during the wave.
		//if(screen.world.mid_wave)
	}
	
	@Override
	public void doClick()
	{
		
	}
}
