package com.leepresswood.wizard.world.entities.player.spells;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.enums.Spells;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.player.spells.damage.Aether;
import com.leepresswood.wizard.world.entities.player.spells.damage.Fireball;
import com.leepresswood.wizard.world.entities.player.spells.utility.Dig;

/**
 * Creates and manages spell spawning.
 * @author Lee
 *
 */
public class SpellFactory
{
	private GameWorld world;
	private Element data_root;
	
	//Recharge set by casting a spell.
	public float time_recharge_next;
	public float time_recharge_current;	
	
	public SpellFactory(GameWorld world)
	{
		this.world = world;

		//Read information about the spells from the XML data file. Stores this information into a data root node that can be used to gather data about each spell.
		try
		{
			data_root = new XmlReader().parse(Gdx.files.internal("data/spells.xml"));
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
		if(time_recharge_current < time_recharge_next)
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
		//If we're allowed to cast another spell, cast. This depends upon the recharge time of the last spell.
		if(time_recharge_current >= time_recharge_next)
		{
			time_recharge_current = 0f;
			
			Spell s = null;
			if(type == Fireball.class)
				s = new Fireball(world, from, to, data_root.getChildByName("fireball"));
			else if(type == Aether.class)
				s = new Aether(world, from, to, data_root.getChildByName("aether"));
			else if(type == Dig.class)
				s = new Dig(world, from, to, data_root.getChildByName("dig"));

			//Set the new recharge time before we go.
			if(s != null)
				time_recharge_next = s.recharge;
			return s;
		}
		
		//This will happen if the player is not ready to cast.
		return null;
	}
	
	/**
	 * Create a new spell with the given data.
	 * @param type The type of spell to create.
	 * @param from The start location of the spell. Typically the player's center.
	 * @param to The end location of the spell. Typically where the player aimed.
	 * @return An instance of the desired spell. Will be null if spell can't be summoned at this time due to recharging.
	 */
	public Spell getSpell(String type, Vector2 from, Vector2 to)
	{
		time_recharge_current = 0f;
			
		Spell s = null;
		switch(Spells.valueOf(type))
		{
			case FIREBALL:
				s = new Fireball(world, from, to, data_root.getChildByName("fireball"));
				break;
			case AETHER:
				s = new Aether(world, from, to, data_root.getChildByName("aether"));
				break;
			case DIG:
				s = new Dig(world, from, to, data_root.getChildByName("dig"));
				break;
		}

		//Set the new recharge time before we go.
		if(s != null)
			time_recharge_next = s.recharge;
		return s;
	}
	
	public boolean isReady()
	{
		return time_recharge_current >= time_recharge_next;
	}
}
