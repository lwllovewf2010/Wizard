package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.gui.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;

/**
 * The button that increases the number of spells available. 
 * @author Lee
 */
public class SpellLevelUpGUIButton extends GUIButton
{
	private final int MAX = 5;
	public int current = 0;
	
	public SpellLevelUpGUIButton(ScreenParent screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void doClick()
	{
		if(++current >= MAX)
		{
			current = MAX;
			is_active = false;
		}
	}
}
