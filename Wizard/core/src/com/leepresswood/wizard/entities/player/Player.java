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
		
	public boolean jumping = false;
	public float jump_start_speed = 6f;
	public float speed_current_y = 0f;
	public float jump_time_current = 0f;
	public float jump_time_max = 0.25f;
	
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
		//Movement.
		calcMovementX(delta);
		calcMovementY(delta);
	}
	
	/**
	 * Calculate movement X direction.
	 * @param delta Change in time.
	 */
	private void calcMovementX(float delta)
	{
		//Deceleration check. Decelerate if not moving, if both left and right are pressed at the same time, or if moving in one direction but pressing another.
		//Note: Simplifying the boolean math. ((A and B) or (!A and !B)) is (A == B)
		//if(!moving_right && !moving_left || moving_right && moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))
		if(moving_right == moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))	
		{
			//Move command is no longer pressed. Move to 0 speed over time.
			if(speed_current_x < 0f)
				speed_current_x += decel_x * delta;
			else if(speed_current_x > 0f)
				speed_current_x -= decel_x * delta;
			
			//Dampen the speed for lower values.
			if(Math.abs(speed_current_x) < 0.1f)
				speed_current_x = 0f;
		}
		
		//Acceleration check.
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
	
	/**
	 * Calculate movement in Y direction. Used in jumping or falling. 
	 * @param delta Change in time.
	 */
	private void calcMovementY(float delta)
	{
		//If the jumping variable is true, jump button is being held.  This allows the jump button to be held longer to jump higher. There must be a max jump button time, though.
		if(jumping && jump_time_current < jump_time_max)
		{
			speed_current_y = jump_start_speed;
			
			//If we've gone over the max time allowed to hold the jump button, make it so that we cannot jump until we land.
			jump_time_current += delta;
		}
		
		//Block the player from jumping multiple times.
		if(!jumping && jump_time_current > 0f)
			jump_time_current = jump_time_max;
		
		//Do a fall calculation by simulating gravity.
		if(sprite.getY() > screen.GROUND)
			speed_current_y -= delta * screen.GRAVITY;

		//Move in the Y direction.
		sprite.translateY(speed_current_y * delta);
		
		//Set a hard limit for how low the player can go. If they pass this limit, they're on . Reset the variables.
		if(sprite.getY() < screen.GROUND)
		{
			sprite.setY(screen.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
