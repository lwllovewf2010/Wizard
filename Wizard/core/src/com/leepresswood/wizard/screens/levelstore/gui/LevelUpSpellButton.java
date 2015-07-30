package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

public class LevelUpSpellButton extends GUIButton
{
	private int spell_number;
	
	public LevelUpSpellButton(ScreenParent screen, Texture t, float x, float y, float width, float height, int spell_number)
   {
	   super(screen, t, x, y, width, height);
	   
	   this.spell_number = spell_number;
	   this.is_active = false;
   }
	
	@Override
	public void doClick()
	{
		((ScreenLevelStore) screen).levelUpSpell(spell_number);
	}
}