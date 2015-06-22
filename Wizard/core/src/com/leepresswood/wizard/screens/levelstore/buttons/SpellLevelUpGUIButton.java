package com.leepresswood.wizard.screens.levelstore.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * The button that increases the number of spells available. 
 * @author Lee
 */
public class SpellLevelUpGUIButton extends LevelUpGUIButton
{
	private final int MAX = 5;
	public int current = 0;
	
	public SpellLevelUpGUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	protected void increment()
	{
		if(++current >= MAX)
			is_active = false;
	}
}
