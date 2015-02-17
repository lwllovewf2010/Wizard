/* Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 */
package com.leepresswood.wizard.entities.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.damage.Aether;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class Player extends PersonEntity
{	
	public Player(ScreenGame screen, float x, float y)
	{
		super(screen, x, y);
		
		//Display player information.
		System.out.println("Player:\n\tPosition: " + sprite.getX() + ", " + sprite.getY() + "\n\tWidth: " + sprite.getWidth() + "\n\tHeight: " + sprite.getHeight());
	}
	
	/**
	 * Entity was hit. Take damage, do knockback, and set invincibility frames.
	 * @param damage The damage amount to subtract from the health. If this goes to or below zero, set the dead flag.
	 */
	public void hit(Enemy enemy)
	{
		//Get the enemy's location in relation to the player. This will allow us to calculate the knockback.
		
		
		//Get the enemy's damage amount. This allows us to update the player's life.
		
		
		//Check to see if the player has died.
		if(screen.gui.bar_health.current_bar_value <= 0)
		{//Dead.
			
		}
		else
		{//Not dead. Set invincibility frames.
			
		}
	}
	
	public void attack(Vector2 touch)
	{
		//Get the selected spell's mana cost and compare it to the player's current mana.
		
		
		//Cast the selected spell if possible.
		screen.spells.add(new Aether(screen, new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f), new Vector2(touch.x, touch.y)));
	}

	protected void calcMovementX(float delta)
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
		
		//Keep the player within the bounds of the screen in the X direction.
		if(sprite.getX() < 0f)
			sprite.setX(0f);
		else if(sprite.getX() + sprite.getWidth() > screen.WORLD_TOTAL_HORIZONTAL)
			sprite.setX(screen.WORLD_TOTAL_HORIZONTAL - sprite.getWidth());
	}
	
	protected void calcMovementY(float delta)
	{
		//Don't allow multiple hops with the same spacebar press.
		if(!jump_stop_hop)
		{
			//If the jumping variable is true, jump button is being held. This allows the jump button to be held longer to jump higher. There must be a max jump button time, though.
			if(jumping)
			{
				if(jump_time_current < jump_time_max)
				{
					speed_current_y = jump_start_speed;
					jump_time_current += delta;
				}
			}
			else
			{
				//Block the player from jumping multiple times within the same jump.
				if(jump_time_current > 0f)
				{
					jump_time_current = jump_time_max;
				}
			}
		}
		else
		{//Allow the player to jump again if they have released the jump button at least once since the flag was set.
			if(!jumping)
			{
				jump_stop_hop = false;
			}
		}
		
		//Do a fall calculation by simulating gravity.
		if(sprite.getY() > screen.GROUND)
			speed_current_y -= delta * screen.GRAVITY;

		//Move in the Y direction.
		sprite.translateY(speed_current_y * delta);
		
		//Set a hard limit for how low the player can go. If they pass this limit, they're on a solid block. Reset the variables.
		if(sprite.getY() < screen.GROUND)
		{
			sprite.setY(screen.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	@Override
	protected void setSprites(ScreenGame screen, float x, float y)
	{
		sprite = new Sprite(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
		sprite.setBounds(x, y, 1, 2);
	}

	@Override
	protected void setMovementVariables()
	{
		//X
		facing_left = false;
		moving_left = false;
		moving_right = false;
		speed_current_x = 0f;
		accel_x = 3f;
		decel_x = 2f * accel_x;
		speed_max_x = 3f;
		
		//Y
		jumping = false;
		jump_stop_hop = false;
		jump_start_speed = 6f;
		speed_current_y = 0f;
		jump_time_current = 0f;
		jump_time_max = 0.25f;
	}

	@Override
	public void die()
	{
	}

	@Override
	protected void updateTiming(float delta)
	{
	}
}
