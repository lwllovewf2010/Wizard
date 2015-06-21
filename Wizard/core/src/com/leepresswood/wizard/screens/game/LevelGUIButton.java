package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.gui.elements.GUIButton;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

/**
 * The button that opens the level shop. 
 * @author Lee
 *
 */
public class LevelGUIButton extends GUIButton
{
	public LevelGUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void update(float delta)
	{
		//This button will not be active during the wave.
		if(screen.world.mid_wave)
			is_active = false;
		else
			is_active = true;
	}
	
	@Override
	public void doClick()
	{
		//Replace the current screen with the shop screen.
		if(is_active)
		{
			screen.game.setScreen(new ScreenLevelStore(screen.game, screen));
		}
	}
}