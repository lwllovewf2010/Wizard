package com.leepresswood.wizard.helpers.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.datapackage.SpellPackage;
import com.leepresswood.wizard.helpers.enums.Spells;
import com.leepresswood.wizard.helpers.handlers.LevelHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.spells.Spell;
import com.leepresswood.wizard.world.entities.spells.damage.Aether;
import com.leepresswood.wizard.world.entities.spells.damage.Fireball;
import com.leepresswood.wizard.world.entities.spells.utility.Dig;

/**
 * Creates and manages spell spawning.
 * @author Lee
 */
public class SpellFactory
{
	public Universe universe;
	public float[] time_recharge;
	
	public SpellFactory(Universe universe)
	{
		this.universe = universe;
		time_recharge = new float[LevelHandler.NUMBER_OF_SPELLS];
	}
	
	/**
	 * Update the timing to see if it's possible to cast another spell.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		for(int i = 0; i < LevelHandler.NUMBER_OF_SPELLS; i++)
			time_recharge[i] += delta;
	}
	
	/**
	 * Create a new spell with the given data.
	 * @param category_index Category of the spell that was requested. (Direct, Indirect, etc.)
	 * @param from The start location of the spell. Typically the player's center.
	 * @param to The end location of the spell. Typically where the player aimed.
	 * @return An instance of the desired spell. Will be null if spell can't be summoned at this time due to recharging or mana cost..
	 */
	public Spell getSpell(int category_index, Vector2 from, Vector2 to)
	{
		//Get data package.
		SpellPackage data = universe.level_handler.castable_spells[category_index];
		Element basic = SpellPackage.getBasic(data);
		Element main = SpellPackage.getMain(data);
		Element sub = SpellPackage.getSub(data);
		
		//Recharge time and mana cost may each be affected by sub levels.
		float recharge = getRechargeTime(basic, main, sub);
		float mana_cost = getManaCost(basic, main, sub);
				
		//Parse spell from this package.
		if(time_recharge[category_index] >= recharge && universe.entity_handler.player.mana_current >= mana_cost)
		{
			time_recharge[category_index] = 0f;
			universe.entity_handler.player.mana_current -= mana_cost;
			
			//Spell was successfully cast. Let's find out which one.
			Spells spell_type = Spells.valueOf(basic.get("name").toUpperCase()); 
			switch(spell_type)
			{
				case FIREBALL:
					return new Fireball(universe, from, to, data);
				case AETHER:
					return new Aether(universe, from, to, data);
				case DIG:
					return new Dig(universe, from, to, data);
			}
		}
		
		//If we make it here, we weren't ready to cast the spell.
		return null;
	}
	
	/**
	 * @return The recharge time of the spell.
	 */
	private float getRechargeTime(Element basic, Element main, Element sub)
	{
		float recharge = basic.getFloat("recharge");
		if(main.get("recharge",  null) != null)
			recharge = main.getFloat("recharge");
		if(sub.get("recharge",  null) != null)
			recharge = sub.getFloat("recharge");
		
		return recharge;
	}
	
	/**
	 * @return The mana cost of the spell.
	 */
	private float getManaCost(Element basic, Element main, Element sub)
	{
		float mana_cost = basic.getFloat("mana_cost");
		if(main.get("mana_cost",  null) != null)
			mana_cost = main.getFloat("mana_cost");
		if(sub.get("mana_cost",  null) != null)
			mana_cost = sub.getFloat("mana_cost");
		
		return mana_cost;
	}
}
