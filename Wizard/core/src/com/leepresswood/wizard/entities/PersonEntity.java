package com.leepresswood.wizard.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Parent class to both the players and the enemies. 
 */
public abstract class PersonEntity
{
	protected ScreenGame screen;	
	
	//Movement and direction.
	public boolean facing_left = false;
	public boolean moving_left = false;
	public boolean moving_right = false;
	public float speed_current_x = 0f;
	public float accel_x = 3f;
	public float decel_x = 2f * accel_x;
	public float speed_max_x = 3f;
	
	//Up-down.
	public boolean jumping = false;
	public boolean jump_stop_hop = false;
	public float jump_start_speed = 6f;
	public float speed_current_y = 0f;
	public float jump_time_current = 0f;
	public float jump_time_max = 0.25f;
	
	//Sprites and bounds.
	public Sprite sprite;
	
	public PersonEntity(ScreenGame screen, float x, float y)
	{
		this.screen = screen;
		sprite = new Sprite(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
		sprite.setBounds(x, y, 1, 2);
		
		setSprites(screen, x, y);
		setMovementVariables();
	}
	
	/**
	 * Determine left-right movement.
	 */
	protected abstract void calcMovementX(float delta);
	
	/**
	 * Determine up-down movement.
	 */
	protected abstract void calcMovementY(float delta);
	
	/**
	 * Set sprites to their initial values.
	 * @param screen Screen for gathering any necessary data.
	 * @param x Left side of the sprite.
	 * @param y Bottom side of the sprite.
	 */
	protected abstract void setSprites(ScreenGame screen, float x, float y);
	
	/**
	 * Set movement variables to their initial values.
	 */
	protected abstract void setMovementVariables();
	
	/**
	 * Entity seeks to attack a targeted point in the world. Cast at or in the direction of that point.
	 * @param point The coordinate in the world that was clicked.
	 */
	public abstract void attack(Vector2 point);
	
	/**
	 * Entity was hit. Take damage, do knockback, and set invincibility frames.
	 * @param damage The damage amount to subtract from the health. If this goes to or below zero, set the dead flag.
	 */
	public abstract void hit(float damage);
	
	/**
	 * Send entity into death animation. Also handle what happens afterward within this.
	 */
	public abstract void die();
	
	/**
	 * Update timing and movement of sprite.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		//Timing.
		updateTiming(delta);
		
		//Movement.
		calcMovementX(delta);
		calcMovementY(delta);
	}
	
	/**
	 * Update the timed events and set the corresponding flags.
	 * @param delta
	 */
	protected abstract void updateTiming(float delta);
	
	/**
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public abstract void draw(SpriteBatch batch);
}
