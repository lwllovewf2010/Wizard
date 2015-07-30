package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

/**
 * The button that increases the number of spells available. 
 * @author Lee
 */
public class SpellLevelUpGUIButton extends GUIButton
{	
	public SpellLevelUpGUIButton(ScreenParent screen, Texture t, float x, float y, float width, float height)
	{
		super(screen, t, x, y, width, height);
	}

	@Override
	public void doClick()
	{//Bump current's value.
		((ScreenLevelStore) screen).levelUpSpellNumber();
	}
}
