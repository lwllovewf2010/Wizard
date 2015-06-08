package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leepresswood.wizard.entities.enemies.creeps.ground.Skeleton;

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
				screen.world.player.moving_right = true;
				break;
			case Keys.A:
				screen.world.player.moving_left = true;
				break;
			case Keys.SPACE:
				screen.world.player.jumping = true;
				break;
			case Keys.Q:
				screen.world.enemies.add(screen.world.factory_enemy.getEnemy(Skeleton.class, true));
			//Pass Numerical keys into the GUI for spell switching.
			case Keys.NUM_0:
			case Keys.NUM_1:
			case Keys.NUM_2:
			case Keys.NUM_3:
			case Keys.NUM_4:
			case Keys.NUM_5:
			case Keys.NUM_6:
			case Keys.NUM_7:
			case Keys.NUM_8:
			case Keys.NUM_9:
			case Keys.NUMPAD_0:
			case Keys.NUMPAD_1:
			case Keys.NUMPAD_2:
			case Keys.NUMPAD_3:
			case Keys.NUMPAD_4:
			case Keys.NUMPAD_5:
			case Keys.NUMPAD_6:
			case Keys.NUMPAD_7:
			case Keys.NUMPAD_8:
			case Keys.NUMPAD_9:
				String key = Keys.toString(keycode);
				screen.gui.changeSpell(Integer.parseInt(key.substring(key.length() - 1)));				
				break;
			default:
				break;
		}
		
		//Display key information.
		//System.out.println("Key Down: " + Keys.toString(keycode));
		return true;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		switch(keycode)
		{
			//Use WASD and Spacebar for player movement.
			case Keys.D:
				screen.world.player.moving_right = false;
				break;
			case Keys.A:
				screen.world.player.moving_left = false;
				break;
			case Keys.SPACE:
				screen.world.player.jumping = false;
				break;
			default:
				break;
		}

		//Display key information.
		//System.out.println("Key Up: " + Keys.toString(keycode));
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
		//Must determine if it's on the GUI or the game world.
		
		
		Vector3 touch = screen.world.camera.unproject(new Vector3(screenX, screenY, 0));
		screen.world.player.attack(new Vector2(touch.x, touch.y));
		
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		
		
		return true;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		screen.gui.changeSpell(amount);
		return true;
	}
	
}
