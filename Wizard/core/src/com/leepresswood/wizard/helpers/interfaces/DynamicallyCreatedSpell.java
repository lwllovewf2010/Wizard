package com.leepresswood.wizard.helpers.interfaces;

/**
 * DynamicallyCreatedSpells are spells that may be created mid-rendering of the world. This interface is necessary to avoid creating a body
 * mid-render -- Box2D doesn't like that. During the spell queue, we will call the instantiate() method. That will effectively work in the
 * same was as a normal spell's setBodies() method.
 * @author Lee
 */
public interface DynamicallyCreatedSpell
{
	/**
	 * Effectively this type of spell's setBodies() method. This must be called rather than setBodies() because we want
	 * this spell to avoid attempting to add a body to the world mid-render. This spell method is called upon every spell
	 * in the spell queue in the EntityHandler at a time where it is safe to add new spells.
	 */
	public void instantiate();
}
