package com.leepresswood.wizard.entities.spells;

import com.badlogic.gdx.math.Vector2;

/**
 * The Spell class is a parent to the various spells in the game. All spells will have a cast-to position, a cast-from position, a mana cost, a delay, and an animation. 
 * @author Lee
 */
public abstract class Spell
{
	public Vector2 from, to;
}
