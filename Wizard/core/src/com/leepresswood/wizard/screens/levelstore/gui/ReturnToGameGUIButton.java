package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

/**
 * From the level store, go back to the game screen.
 *
 * @author Lee
 */
public class ReturnToGameGUIButton extends GUIButton
{
	public ReturnToGameGUIButton(ScreenParent screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void doClick()
	{
		screen.game.setScreen(((ScreenLevelStore) screen).game_screen);
	}
}
