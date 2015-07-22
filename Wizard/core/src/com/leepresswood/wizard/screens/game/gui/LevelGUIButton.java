package com.leepresswood.wizard.screens.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * The button that opens the level shop. 
 * @author Lee
 *
 */
public class LevelGUIButton extends GUIButton
{
	public LevelGUIButton(ScreenParent screen, float x, float y, float width, float height)
	{
		super(screen, screen.game.assets.get("textures/gui/spellbook.png", Texture.class), x, y, width, height);
	}

	@Override
	public void update(float delta)
	{
		//This button will not be active during the wave.
		if(((ScreenGame) screen).universe.wave_handler.mid_wave)
			is_active = false;
		else
			is_active = true;
	}
	
	@Override
	public void doClick()
	{
		//Replace the current screen with the shop screen. Pauses the game in the process.
		//screen.game.setScreen(new ScreenLevelStore(screen.game, screen, ScreenUtils.getFrameBufferTexture()));
		((ScreenGame) screen).go_to_level_store = true;
	}
}
