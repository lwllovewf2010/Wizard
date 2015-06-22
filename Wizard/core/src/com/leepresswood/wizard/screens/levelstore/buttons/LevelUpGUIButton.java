package com.leepresswood.wizard.screens.levelstore.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.game.gui.GUIButton;

/**
 * There will be multiple level-up buttons on the level store screen. Each will
 * affect the level of a different attribute.
 * @author Lee
 */
public abstract class LevelUpGUIButton extends GUIButton
{
	public LevelUpGUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void update(float delta)
	{//Update isn't required here.
	}

	@Override
	public void doClick()
	{//We will increment the value affected by the button and check to see if the value has reached the max.
		increment();
	}
	
	/**
	 * The button was pressed, so an increment of a particular value is required.
	 */
	protected abstract void increment();
}
