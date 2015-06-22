package com.leepresswood.wizard.screens.levelstore.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.game.gui.GUIButton;

/**
 * The button that increases the number of spells available. 
 * @author Lee
 */
public class SpellLevelUpGUIButton extends GUIButton
{
	private final int MAX = 5;
	public int current = 0;
	
	public SpellLevelUpGUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void update(float delta)
	{System.out.println(current);
	}

	@Override
	public void doClick()
	{
		if(++current >= MAX)
			is_active = false;
	}
}
