package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;


public class InputGame implements InputProcessor
{
	private ScreenGame screen;
	
	public InputGame(ScreenGame screen)
	{
		this.screen = screen;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		switch(keycode)
		{
			case Keys.D:
				screen.player.moving_right = true;
				break;
			case Keys.A:
				screen.player.moving_left = true;
				break;
			case Keys.SPACE:
				screen.player.jumping = true;
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
			case Keys.D:
				screen.player.moving_right = false;
				break;
			case Keys.A:
				screen.player.moving_left = false;
				break;
			case Keys.SPACE:
				screen.player.jumping = false;
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
