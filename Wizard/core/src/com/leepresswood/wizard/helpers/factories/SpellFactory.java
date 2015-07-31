package com.leepresswood.wizard.helpers.factories;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
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
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		//Parse spell from this package.
		if(time_recharge[category_index] >= time_max[category_index] && universe.entity_handler.player.mana_current >= SpellPackage.getMainLevel(data).getFloat("mana_cost"))
		{
			time_recharge[category_index] = 0f;
			switch(spell_type)
			{
				case FIREBALL:
					s = new Fireball(universe, from, to, data_root.getChildByName("fireball"), level);
					break;
				case AETHER:
					s = new Aether(universe, from, to, data_root.getChildByName("aether"), level);
					break;
				case DIG:
					s = new Dig(universe, from, to, data_root.getChildByName("dig"), level);
					break;
			}
		}
		
		return s;
	}
	
	/**
	 * We're trying to add a spell to the world and we need the mana
	 * cost of that spell. Get it.
	 * @param level The level.
	 * @param data The datapackage we're using.
	 * @return the mana cost of that spell.
	 */
	private float getManaCost(int level, SpellPackage data)
	{
		return SpellPackage.getMain(data).getChild(level).getFloat("mana_cost");
	}
}
