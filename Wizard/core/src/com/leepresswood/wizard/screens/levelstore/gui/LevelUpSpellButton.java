package com.leepresswood.wizard.screens.levelstore.gui;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.helpers.enums.AttackLevel;
import com.leepresswood.wizard.helpers.enums.AttackType;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

public class LevelUpSpellButton extends GUIButton
{
	private AttackType type;
	private AttackLevel level;
	private int button_number;
	
	public LevelUpSpellButton(ScreenParent screen, Texture t, float x, float y, float width, float height, AttackType type, AttackLevel level, int button_number)
   {
	   super(screen, t, x, y, width, height);
	   
	   this.type = type;
	   this.level = level;
	   this.button_number = button_number;
	   
	   //First buttons are active. Others are not.
	   this.is_active = button_number == 0;
   }
	
	@Override
	public void doClick()
	{
		//Do the level up.
		((ScreenLevelStore) screen).levelUpSpell(type, level, button_number);
	}
}