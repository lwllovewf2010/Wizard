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
	public float time;
	public float MAX_TIME;
	
	//Sprite stuff.
	public Sprite sprite;
	
	public Spell(ScreenGame screen, Vector2 from, Vector2 to)
	{//Create an active version of this spell.
		this.screen = screen;
		this.from = from;
		this.to = to;
		
		active = true;
		
		sprite = new Sprite();
		makeSpriteTexture();
		makeSpriteBounds();
	}
	
	/**
	 * Set sprite's texture.
	 */
	protected abstract void makeSpriteTexture();
	
	/**
	 * Create the sprite using the "from" and "to" vectors.
	 */
	protected abstract void makeSpriteBounds();
	
	/**
	 * Call the position and collision updates. Also examine the time.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		updatePosition(delta);
		updateCollision();
		
		//If time is set, compare it. Above TIME_MAX -> make inactive.
		if(MAX_TIME > 0f && time >= MAX_TIME)
			active = false;
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
	public abstract void draw(SpriteBatch batch);
}
