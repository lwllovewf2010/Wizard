package com.leepresswood.wizard;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.leepresswood.wizard.screen.ScreenGame;

public class Input implements InputProcessor
{
	private ScreenGame screen;
	 
	public Input(ScreenGame screen)
	{
		this.screen = screen;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		switch(keycode)
		{
			case Keys.RIGHT:
				screen.moving_right = true;
				break;
			case Keys.LEFT:
				screen.moving_left = true;
				break;
			default:
				break;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		switch(keycode)
		{
			case Keys.RIGHT:
				screen.moving_right = false;
				break;
			case Keys.LEFT:
				screen.moving_left = false;
				break;
			default:
				break;
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
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
