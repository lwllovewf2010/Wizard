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
	 * Calculate movement in the X direction.
	 * @param delta Change in time.
	 */
	protected abstract void calcMovementX(float delta);
	
	/**
	 * Calculate movement in the Y direction.
	 * @param delta Change in time.
	 */
	protected abstract void calcMovementY(float delta);
	
	/**
	 * This entity collided with the passed entity. Do damage/effects.
	 * @param entity The entity that was hit.
	 */
	public abstract void doHit(GameEntity entity);
	
	/**
	 * Is this entity dead?
	 * @return True if dead. False otherwise.
	 */
	public abstract boolean getDeathStatus();
	
	/**
	 * Send entity into death animation. Also handles what happens afterward.
	 */
	public abstract void die(float delta);
}
