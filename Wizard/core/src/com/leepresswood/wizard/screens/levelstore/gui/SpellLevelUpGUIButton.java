package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

/**
 * The button that increases the number of spells available. 
 * @author Lee
 */
public class SpellLevelUpGUIButton extends GUIButton
{
	private final int MAX_SPELLS_AVAILABLE = 5;
	
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
	{//Bump current's value.
		if(++((ScreenLevelStore) screen).current_spells_available >= MAX_SPELLS_AVAILABLE)
		{
			((ScreenLevelStore) screen).current_spells_available = MAX_SPELLS_AVAILABLE;
			is_active = false;
		}
	}
}
