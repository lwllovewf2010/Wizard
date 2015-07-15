package com.leepresswood.wizard.handlers;

import com.leepresswood.wizard.world.Universe;

/**
 * Level manager for the player.
 * @author Lee
 */
public class LevelHandler
{
	public Universe universe;
	
	public int level;							//Total points available.
	public int points_spent;				//Points that have been spent on leveling.
	public int points_available;			//Points available to be spent.
	
	public int spells_available;			//Number of available spells.
	public int[] spell_levels;				//Levels relating to each spell.
	
	public LevelHandler(Universe universe, int level)
	{
		this.universe = universe;
		this.level = level;
		
		spells_available = 1;
		spell_levels = new int[spells_available];
		for(int i : spell_levels)
			spell_levels[i] = 0;
	}

	/**
	 * Do all required actions upon leveling up.
	 */
	public void levelUp()
	{
		level++;
		points_available = level - points_spent;
	}
	
	/**
	 * After returning from the level store, we must gather the new
	 * level data.
	 */
	public void gatherLevelData()
	{
		//Collect the number of available spells.
		int new_spells_available = spells_available + 1;
		
		//From this number, create a new spell_levels array if necessary.
		if(spells_available != new_spells_available)
		{//Because they are not equal, we added more spells to the list of available ones.
			//Copy over the old list of spell levels.
			int[] new_spell_levels = new int[new_spells_available];
			for(int i = 0; i < spells_available - 1; i++)
				new_spell_levels[i] = spell_levels[i];
			
			//Everything remaining will be initialized to level 1.
			for(int i = spells_available; i < new_spells_available; i++)
				new_spell_levels[i] = 0;
			
			//Change the pointer to the new array.
			spell_levels = new_spell_levels;
		}
		
		//Using all this new data, we can create the spell list.
		spells_available = new_spells_available;
		universe.screen.gui.refreshSpellList(spells_available);
	}
}
