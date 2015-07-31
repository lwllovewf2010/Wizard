package com.leepresswood.wizard.helpers.factories;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.datapackage.SpellPackage;
import com.leepresswood.wizard.helpers.enums.Spells;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.spells.Spell;
import com.leepresswood.wizard.world.entities.spells.damage.Aether;
import com.leepresswood.wizard.world.entities.spells.damage.Fireball;
import com.leepresswood.wizard.world.entities.spells.utility.Dig;

/**
 * Creates and manages spell spawning.
 * @author Lee
 *
 */
public class SpellFactory
{
	private Universe world;
	
	private Element data_root;									//Location of spell data.
	
	public HashMap<Spells, Float> time_recharge;			//Recharge zeroed out after casting a spell.
	public HashMap<Spells, Float> time_max;				//Max time set by spell data.
	
	public SpellFactory(Universe world)
	{
		this.world = world;

		//Read information about the spells from the XML data file. Stores this information into a data root node that can be used to gather data about each spell.
		try
		{
			data_root = new XmlReader().parse(Gdx.files.internal("data/spells.xml"));
			time_recharge = new HashMap<Spells, Float>();
			time_max = new HashMap<Spells, Float>();
			
			//Set the times for all spells.
			for(Spells s : Spells.values())
			{
				time_recharge.put(s, 0f);
				time_max.put(s, data_root.getChildByName(s.toString().toLowerCase()).getFloat("recharge"));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Update the timing to see if it's possible to cast another spell.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{//If time to recharge is above the max, we can cast the spell again. We want to limit this value to avoid a potential overflow (in a few billion seconds).
		for(Spells s : time_recharge.keySet())
			if(time_recharge.get(s) < time_max.get(s))
				time_recharge.put(s, time_recharge.get(s) + delta);
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
		SpellPackage data = world.level_handler.castable_spells[category_index];
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		//Parse spell from this package.
		
		if(time_recharge.get(spell_type) >= time_max.get(spell_type) && world.entity_handler.player.mana_current >= getManaCost(category_index, data))
		{
			time_recharge.put(spell_type, 0f);
			switch(spell_type)
			{
				case FIREBALL:
					s = new Fireball(world, from, to, data_root.getChildByName("fireball"), level);
					break;
				case AETHER:
					s = new Aether(world, from, to, data_root.getChildByName("aether"), level);
					break;
				case DIG:
					s = new Dig(world, from, to, data_root.getChildByName("dig"), level);
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
