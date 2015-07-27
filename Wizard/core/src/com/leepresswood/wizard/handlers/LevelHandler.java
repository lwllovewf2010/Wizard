package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.graphics.Color;
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
	
	//Experience stuff.
	private final float EXPERIENCE_MAX = 100f;
	private float experience_current;
	
	public LevelHandler(Universe universe, int level)
	{
		this.universe = universe;
		this.level = level;
		
		//Initialize each spell to level 0.
		spell_levels = new int[SPELLS_NUMBER_MAX];
		for(int i : spell_levels)
			spell_levels[i] = 0;
	}

	/**
	 * Enemy was killed, and experience was gained.
	 * @param experience Amount gained.
	 */
	public void addExperience(int experience)
	{
		universe.text_handler.createDecayText("" + experience +  "xp", 100f, 100f, Color.YELLOW);
		
		this.experience_current += experience;
		while(this.experience_current >= EXPERIENCE_MAX);
		{
			levelUp();
			this.experience_current -= EXPERIENCE_MAX;
		}
	}
	
	public float getExperienceAsPercentOfLevel()
	{
		return experience_current / EXPERIENCE_MAX;
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
