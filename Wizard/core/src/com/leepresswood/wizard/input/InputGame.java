package com.leepresswood.wizard.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leepresswood.wizard.gui.elements.GUIButton;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.enemies.creeps.ground.Skeleton;

public class InputGame implements InputProcessor
{
	private ScreenGame screen;
	
	public Vector3 mouse_position;
	
	public InputGame(ScreenGame screen)
	{
		this.screen = screen;
		
		mouse_position = new Vector3();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		switch(keycode)
		{
			case Keys.D:
				screen.world.entity_handler.player.moving_right = true;
				break;
			case Keys.A:
				screen.world.entity_handler.player.moving_left = true;
				break;
			case Keys.SPACE:
				screen.world.entity_handler.player.jumping = true;
				break;
			case Keys.Q:
				Enemy e = screen.world.entity_handler.factory_enemy.getEnemy(Skeleton.class);
				if(e != null)
					screen.world.entity_handler.enemies.add(e);
				break;
			case Keys.SHIFT_LEFT:
				screen.world.wave_handler.mid_wave = !screen.world.wave_handler.mid_wave;
				break;
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
				screen.world.entity_handler.player.moving_right = false;
				break;
			case Keys.A:
				screen.world.entity_handler.player.moving_left = false;
				break;
			case Keys.SPACE:
				screen.world.entity_handler.player.jumping = false;
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
		//Must determine if it's on the GUI or the game world.
		if(!guiTouchCheck(screenX, screenY))
		{//Touch was not on GUI, so push it into the game world.
			Vector3 touch = screen.world.map_camera_handler.unproject(new Vector3(screenX, screenY, 0));
			screen.world.entity_handler.player.attack(new Vector2(touch.x, touch.y));
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
		/*mouse_position.x = screenX;
		mouse_position.y = Gdx.graphics.getHeight() - screenY;*/
		
		//mouse_position = screen.world.map_camera_handler.unproject(new Vector3(screenX, screenY, 0f));
		
		return true;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		screen.gui.changeSpell(amount);
		return true;
	}
	
	/**
	 * Determine if the touch was done on the GUI. Do the touch if it was.
	 * @param screenX X click.
	 * @param screenY Y click.
	 * @return True if touch was done on GUI. False otherwise.
	 */
	private boolean guiTouchCheck(int screenX, int screenY)
	{
		//ScreenY will be flipped for no reason. Flip it back.
		screenY = Gdx.graphics.getHeight() - screenY;
		
		//Check spell icons.
		for(int i = 0; i < screen.gui.spells.length; i++)
		{
			if(screen.gui.spells[i] != null && screen.gui.spells[i].sprite.getBoundingRectangle().contains(screenX, screenY))
			{
				screen.gui.shiftSpellTo(i);
				return true;
			}
		}
		
		//Check menu buttons. Note: The click can't happen if the button isn't active.
		for(GUIButton b : screen.gui.button_array)
			if(b.sprite.getBoundingRectangle().contains(screenX, screenY) && b.is_active)
			{
				b.doClick();
				return true;
			}
		
		//Otherwise, there was no GUI touch.
		return false;
	}
}
