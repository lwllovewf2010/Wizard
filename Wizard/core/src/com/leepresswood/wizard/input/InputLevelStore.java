package com.leepresswood.wizard.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
		//Correct screenY before doing the touch.
		screenY = Gdx.graphics.getHeight() - screenY;
		for(int i = 0; i < screen.NUMBER_LEVELS; i++)
		{//Buttons.
			if(screen.buttons_direct[i].is_active && screen.buttons_direct[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_direct[i].doClick();
			else if(screen.buttons_direct_effect[i].is_active && screen.buttons_direct_effect[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_direct_effect[i].doClick();
			else if(screen.buttons_indirect[i].is_active && screen.buttons_indirect[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_indirect[i].doClick();
			else if(screen.buttons_indirect_effect[i].is_active && screen.buttons_indirect_effect[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_indirect_effect[i].doClick();
			else if(screen.buttons_defense[i].is_active && screen.buttons_defense[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_defense[i].doClick();
			else if(screen.buttons_defense_effect[i].is_active && screen.buttons_defense_effect[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_defense_effect[i].doClick();
			else if(screen.buttons_ultimate[i].is_active && screen.buttons_ultimate[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_ultimate[i].doClick();
			else if(screen.buttons_ultimate_effect[i].is_active && screen.buttons_ultimate_effect[i].sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.buttons_ultimate_effect[i].doClick();
			
			if(screen.button_return.is_active && screen.button_return.sprite.getBoundingRectangle().contains(screenX, screenY))
				screen.button_return.doClick();
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
