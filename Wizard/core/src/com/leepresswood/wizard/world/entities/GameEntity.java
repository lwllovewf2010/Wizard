package com.leepresswood.wizard.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.world.entities.box2d.Box2DSprite;

/**
 * All game objects will be filtered through this class. 
 * @author Lee
 */
public abstract class GameEntity
{
	//Sprites and bounds.
	public Box2DSprite[] parts;
	
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
}
