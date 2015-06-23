package com.leepresswood.wizard.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.leepresswood.wizard.screens.game.gui.GUIButton;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;

public class InputLevelStore implements InputProcessor
{
	public ScreenLevelStore screen;
	
	public InputLevelStore(ScreenLevelStore screen)
	{
		this.screen = screen;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		//Corrent screenY before doing the touch.
		screenY = Gdx.graphics.getHeight() - screenY;
		
		for(GUIButton b : screen.button_array)
		{	
			if(b.is_active && b.sprite.getBoundingRectangle().contains(screenX, screenY))
			{
				b.doClick();
			}
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}

}
