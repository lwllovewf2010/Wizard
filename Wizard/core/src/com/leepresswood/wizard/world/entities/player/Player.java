package com.leepresswood.wizard.world.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.calculators.DefenseCalculator;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.enemies.Enemy;

/**
 * Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 * @author Lee
 *
 */
public abstract class Player extends GameEntity
{
	public final float JUMP_TIME_MAX = 0.25f;
	public boolean jump_stop_hop;
	
	public Player(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		//Grab the health and mana.
		health = data.getFloat("health");
		mana = data.getFloat("mana");
	}
	
	@Override
	public void damage(float amount)
	{
		//Damage was done, but defense must be taken into consideration.
		universe.screen.gui.bar_health.change(-DefenseCalculator.damageAfterDefense(amount, defense));
	}
	
	@Override
	protected void blockCollision()
	{
		super.blockCollision();
		
		//On top of the normal block collision, we want our player to be stuck within the bounds of the stage.
		if(sprite.getX() < 0f)
		{
			sprite.setX(0f);
			speed_current_x = 0f;
		}
		else if(sprite.getX() + sprite.getWidth() > universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL)
		{
			sprite.setX(universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL - sprite.getWidth());
			speed_current_x = 0f;
		}
	}

	protected void calcMovementX(float delta)
	{
		//Deceleration check. Decelerate if not moving, if both left and right are pressed at the same time, or if moving in one direction but pressing another.
		/* if(!moving_right && !moving_left || moving_right && moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))
		 * Note: Simplifying the boolean math. ((A and B) or (!A and !B)) is (A == B)
		 */		
		if(moving_right == moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))	
		{
			//Move command is no longer pressed. Decay to 0 speed over time.
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
	}
	
	protected void calcMovementY(float delta)
	{
		//Don't allow multiple hops with the same spacebar press.
		if(!jump_stop_hop)
		{
			//If the jumping variable is true, jump button is being held. This allows the jump button to be held longer to jump higher. There must be a max jump button time, though.
			if(jumping)
			{
				if(jump_time_current < JUMP_TIME_MAX)
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
					jump_time_current = JUMP_TIME_MAX;
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
		speed_current_y -= delta * universe.map_camera_handler.GRAVITY;
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	@Override
	protected void enemyCollision()
	{
		for(Enemy e : universe.entity_handler.enemies)
		{
			//To make this horrible O(n^3) function faster, we're only going to check the enemies that are alive and within a certain radius.
			if(e.dying == false && 25f > Vector2.dst2(e.bounds[0].x + e.bounds[0].width / 2f, e.bounds[0].y + e.bounds[0].height / 2f, bounds[0].x + bounds[0].width / 2f, bounds[0].y + bounds[0].height / 2f))
			{
				for(Rectangle r : e.bounds)
				{
					for(Rectangle r2 : this.bounds)
					{
						if(r2.overlaps(r))
						{
							//Get the angle between the enemy and the attack. The angle of the knockback will be the flipped version of this angle.
							knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(r2.y + r.height / 2f - sprite.getY() - sprite.getHeight() / 2f, r.x + r.width / 2f - sprite.getX() - sprite.getWidth() / 2f);
							knockback_angle += 180f;
							
							//Get damage/effects.
							e.doHit(this);
							e.hitBy(this);
							
							//Set the knockback and invincibility.
							knockback_speed = e.knockback_force;
							is_being_knocked_back = true;
							is_invincible = true;
							invincible_time_current = 0f;
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean getDeathStatus()
	{
		return universe.screen.gui.bar_health.current_bar_value <= 0f;
	}
	
	@Override
	public void die(float delta)
	{
	}
}
