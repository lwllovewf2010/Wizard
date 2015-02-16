package com.leepresswood.wizard.entities.spells;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * The Spell class is a parent to the various spells in the game. All spells will have a cast-to position, a cast-from position, a mana cost, a delay, and an animation. 
 * @author Lee
 */
public abstract class Spell
{
	protected ScreenGame screen;
	
	//Spell location, movement, visibility, etc.
	public Vector2 from, to;
	public boolean active;
	
	//Sprite stuff.
	public Sprite sprite;
	
	public Spell(ScreenGame screen, Vector2 from, Vector2 to)
	{//Create an active version of this spell.
		this.screen = screen;
		
		makeSprite(from);
	}
	
	/**
	 * Create the sprite at the passed location.
	 * @param start The location of the spell. This is the spell's center for most spells.
	 */
	protected abstract void makeSprite(Vector2 start);
	
	/**
	 * Call the position and collision updates.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		updatePosition(delta);
		updateCollision();
	}
	
	/**
	 * Update the spell's position over time. Also calls the collision collision detection method.
	 * @param delta Change in time.
	 */
	protected abstract void updatePosition(float delta);
	
	/**
	 * Checks the spell's collision with game objects. Deals with damage and visibility as well.
	 */
	protected abstract void updateCollision();
	
	/**
	 * Draw the spell.
	 * @param batch SpriteBatch for the sprite.
	 */
	protected abstract void draw(SpriteBatch batch);
}
