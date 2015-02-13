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
	public boolean facing_left;
	public boolean moving_left;
	public boolean moving_right;
	public float speed_current_x;
	public float accel_x;
	public float decel_x;
	public float speed_max_x;
	
	//Up-down.
	public boolean jumping;
	public boolean jump_stop_hop;
	public float jump_start_speed;
	public float speed_current_y;
	public float jump_time_current;
	public float jump_time_max;
	
	//Sprites and bounds.
	public Sprite sprite;
	
	public PersonEntity(ScreenGame screen, float x, float y)
	{
		this.screen = screen;
		
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
	 * Send entity into death animation. Also handle what happens afterward within this.
	 */
	public abstract void die();
	
	/**
	 * Update timing and movement of sprites.
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
