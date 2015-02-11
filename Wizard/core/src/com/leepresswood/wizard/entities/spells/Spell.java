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
	}
	
	protected abstract void makeSprite();
	protected abstract void update(float delta);
	protected abstract void draw(SpriteBatch batch);
}
