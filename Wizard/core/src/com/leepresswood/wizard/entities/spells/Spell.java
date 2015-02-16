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
	
	public Vector2 from, to;
	public boolean active;
	
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
	 * Update the spell's position over time. Also calls the collision collision detection method.
	 * @param delta The change in time.
	 */
	protected abstract void update(float delta);
	
	/**
	 * Checks the spell's collision with game objects.
	 */
	protected abstract void collision();
	
	/**
	 * 
	 * @param batch
	 */
	protected abstract void draw(SpriteBatch batch);
}
