package com.leepresswood.wizard.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.world.Universe;

/**
 * All game objects will be filtered through this class. 
 * @author Lee
 */
public abstract class GameEntity
{
	public Universe universe;
	
	//Sprites and bounds.
	public Box2DSprite[] parts;
	
	public GameEntity(Universe universe)
   {
		this.universe = universe;
   }

	/**
	 * Update timing and movement of sprites.
	 * @param delta Change in time.
	 */
	public abstract void update(float delta);
	
	/**
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public abstract void draw(SpriteBatch batch);
	
	/**
	 * Is this entity dead?
	 * @return True if dead. False otherwise.
	 */
	public abstract boolean getDeathStatus();
	
	/**
	 * Send entity into death animation. Also handle what happens afterward within this.
	 */
	public abstract void die(float delta);
}
