package com.leepresswood.wizard.screens.levelstore.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.leepresswood.wizard.gui.elements.GUIButton;
import com.leepresswood.wizard.screens.game.ScreenGame;

public abstract class LevelUpGUIButton extends GUIButton
{

	public LevelUpGUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
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
	}
}
