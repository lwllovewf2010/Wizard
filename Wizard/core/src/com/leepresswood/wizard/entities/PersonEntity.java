package com.leepresswood.wizard.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Parent class to both the players and the enemies. 
 */
public abstract class PersonEntity
{
	protected GameWorld world;	
	
	//Movement and direction.
	public boolean facing_left;
	public boolean moving_left;
	public boolean moving_right;
	public float speed_current_x;
	public float accel_x;
	public float decel_x;
	public float speed_max_x;
	
	//Knockback.
	public boolean is_invincible;
	public boolean is_being_knocked_back;
	public float invincible_time_max = 0.25f;
	public float invincible_time_current;
	public float knockback_speed;
	public float knockback_angle;
	
	//Up-down.
	public boolean jumping;
	public boolean jump_stop_hop;
	public float jump_start_speed;
	public float speed_current_y;
	public float jump_time_current;
	public float jump_time_max;
	
	//Sprites and bounds.
	public Sprite sprite;
	
	public PersonEntity(GameWorld world, float x, float y)
	{
		this.world = world;
		
		setSprites(x, y);
		setMovementVariables();
	}
	
	/**
	 * Determine left-right movement.
	 */
	protected void move(float delta)
	{
		//The enemy is stunned during a knockback. All AI motion is locked.
		if(is_being_knocked_back)
		{
			is_being_knocked_back = false;			
			speed_current_x = knockback_speed * MathUtils.cosDeg(knockback_angle);
			speed_current_y = knockback_speed * MathUtils.sinDeg(knockback_angle);
		}
		else
		{
			calcMovementX(delta);
			calcMovementY(delta);
		}
	}
	
	protected abstract void calcMovementX(float delta);
	protected abstract void calcMovementY(float delta);
	
	/**
	 * Set sprites to their initial values.
	 * @param screen Screen for gathering any necessary data.
	 * @param x Left side of the sprite.
	 * @param y Bottom side of the sprite.
	 */
	protected abstract void setSprites(float x, float y);
	
	/**
	 * Set movement variables to their initial values.
	 */
	protected abstract void setMovementVariables();
	
	/**
	 * Entity seeks to attack a targeted point in the world. Cast at or in the direction of that point.
	 * @param point The coordinate in the world that was clicked.
	 */
	public abstract void attack(Vector2 touch);
	
	/**
	 * Collision with enemies to this entity.
	 */
	protected abstract void enemyCollision();
	
	/**
	 * Collision with the game world.
	 */
	protected void blockCollision()
	{
		//Set a hard limit for how low the entity can go. If they pass this limit, they're on a solid block. Reset the variables.
		if(sprite.getY() < world.screen.world.GROUND)
		{
			sprite.setY(world.screen.world.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
	}
	
	/**
	 * Send entity into death animation. Also handle what happens afterward within this.
	 */
	public abstract void die(float delta);
	
	/**
	 * Update timing and movement of sprites.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		if(is_invincible)
		{
			invincible_time_current += delta;
			if(invincible_time_current >= invincible_time_max)
			{
				is_invincible = false;
			}
		}//The enemy is stunned during a knockback. All AI motion is locked.
		if(is_being_knocked_back)
		{
			is_being_knocked_back = false;			
			speed_current_x = knockback_speed * MathUtils.cosDeg(knockback_angle);
			speed_current_y = knockback_speed * MathUtils.sinDeg(knockback_angle);
		}
		else
		{
			calcMovementX(delta);
			calcMovementY(delta);
			
		}
		
		move(delta);
		enemyCollision();
		blockCollision();
		//health();
		die(delta);
	}
	
	/**
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public abstract void draw(SpriteBatch batch);
}
