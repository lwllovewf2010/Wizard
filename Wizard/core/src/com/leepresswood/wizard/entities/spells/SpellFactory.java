package com.leepresswood.wizard.entities.spells;

import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.entities.spells.damage.Aether;
import com.leepresswood.wizard.entities.spells.damage.Fireball;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Creates and manages spell spawning.
 * @author Lee
 *
 */
public class SpellFactory
{
	private ScreenGame screen;
	
	public Spell spell_last;
	public float time_recharge_current;
	public float TIME_RECHARGE;
	
	public SpellFactory(ScreenGame screen)
	{
		this.screen = screen;
	}
	
	/**
	 * Update the timing to see if it's possible to cast another spell.
	 * @param delta
	 */
	public void update(float delta)
	{//If time to recharge is above the max, we can cast the spell again. We want to limit this value to avoid a potential overflow (in a few billion seconds).
		if(time_recharge_current <  TIME_RECHARGE)
			time_recharge_current += delta;		
	}
	
	/**
	 * Create a new spell with the given data.
	 * @param type The type of spell to create.
	 * @param from The start location of the spell. Typically the player's center.
	 * @param to The end location of the spell. Typically where the player aimed.
	 * @return An instance of the desired spell. Will be null if spell can't be summoned at this time due to recharging.
	 */
	public Spell getSpell(Class<?> type, Vector2 from, Vector2 to)
	{
		//If we're allowed to cast another spell, cast.
		if(time_recharge_current >= TIME_RECHARGE)
		{
			time_recharge_current = 0f;
			
			if(type == Fireball.class)
				return new Fireball(screen, from, to);
			else if(type == Aether.class)
				return new Aether(screen, from, to);
		}
		
		//This will happen if the player is not ready to cast.
		return null;
	}
}
