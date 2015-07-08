package com.leepresswood.wizard.world.entities.spells;

/**
 * DynamicallyCreatedSpells are spells that may be created mid-rendering of the world. This interface is necessary to avoid creating a body
 * mid-render -- Box2D doesn't like that. During the spell queue, we will call the instantiate() method. That will effectively work in the
 * same was as a normal spell's setBodies() method.
 * @author Lee
 */
public interface DynamicallyCreatedSpell
{
	/**
	 * Effectively this type of spell's setBodies() method.
	 */
	public void instantiate();
}
