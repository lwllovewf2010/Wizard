package com.leepresswood.wizard.handlers;

import com.leepresswood.wizard.world.Universe;

/**
 * Level manager for the player.
 * @author Lee
 */
public class LevelHandler
{
	public Universe universe;
	
	public int level;										//Total points available.
	public int points_spent;							//Points that have been spent on leveling.
	
	public final int SPELLS_NUMBER_MAX = 5;		//Maximum spells the player can have.
	public int spells_available;						//Number of available spells.
	
	public final int SPELL_LEVEL_MAX = 5;			//Every spell can be leveled 5 times.
	public int[] spell_levels;							//Levels relating to each spell.
	
	public LevelHandler(Universe universe, int level)
	{
		this.universe = universe;
		this.level = level;
		
		//Initialize each spell to level 0.
		spell_levels = new int[SPELLS_NUMBER_MAX];
		for(int i : spell_levels)
		{
			spell_levels[i] = 0;
		}
	}

	public int getPointsAvailable()
	{
		return level - points_spent;
	}
	
	/**
	 * Do all required actions upon leveling up.
	 */
	public void levelUp()
	{
		level++;
	}
	
	/**
	 * Are there points available to be spent?
	 * @return True if available. False otherwise.
	 */
	public boolean canSpend()
	{
		return 0 < getPointsAvailable();
	}
	
	/**
	 * Player requested to spend a point.
	 */
	public void spend()
	{
		points_spent++;
	}
	
	/**
	 * After returning from the level store, we must gather the new level data.
	 */
	public void gatherLevelData()
	{
		universe.screen.gui.makeSpellList();
	}
}
