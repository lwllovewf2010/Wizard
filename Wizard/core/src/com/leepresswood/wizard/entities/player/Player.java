/* Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 */
package com.leepresswood.wizard.entities.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class Player
{	
	protected ScreenGame screen;	
	
	//Direction and movement.
	public boolean facing_left = false;
	public boolean moving_left = false;
	public boolean moving_right = false;
	public float speed_current_x = 0f;
	
	public float accel_x = 3f;
	public float decel_x = 3f * accel_x;
	public float speed_max_x = 3f;
		
	public boolean jumping = true;
	public boolean	falling;
	
	
	//Sprites and bounds.
	public Sprite sprite;

	
	
	public Player(ScreenGame screen, float x, float y)
	{
		this.screen = screen;
		sprite = new Sprite(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
		sprite.setBounds(x, y, 1, 2);
	}
	
	public void die()
	{
		
	}
	
	public void attack(Vector2 click_point)
	{
		
	}
	
	public void update(float delta)
	{
		calcMovementX(delta);
		
		//Jumping or falling.
		if(falling)
			;
	}
	
	/**
	 * Calculate movement if moving left or right.
	 * @param delta Change in time.
	 */
	private void calcMovementX(float delta)
	{
		//Deceleration check. Decelerate if not moving, if both left and right are pressed at the same time, or if moving in one direction but pressing another.
		if(!moving_right && !moving_left || moving_right && moving_left || moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0)
			decelerate(delta);
		
		if(moving_left && !moving_right)
		{
			speed_current_x -= accel_x * delta;
			if(speed_current_x < -speed_max_x)
				speed_current_x = -speed_max_x;
		}
		else if(moving_right && !moving_left)
		{
			speed_current_x += accel_x * delta;
			if(speed_current_x > speed_max_x)
				speed_current_x = speed_max_x;
		}
		
		//Move in the X direction.
		sprite.translateX(delta * speed_current_x);		
	}
	
	private void decelerate(float delta)
	{
		//Move command is no longer pressed. Move to 0 speed over time.
		if(speed_current_x < 0f)
			speed_current_x += decel_x * delta;
		else if(speed_current_x > 0f)
			speed_current_x -= decel_x * delta;
		
		
		//Set some lack sensitivity.
		if(Math.abs(speed_current_x) < 0.1f)
			speed_current_x = 0f;
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
