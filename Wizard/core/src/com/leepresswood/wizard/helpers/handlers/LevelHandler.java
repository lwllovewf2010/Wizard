package com.leepresswood.wizard.helpers.handlers;

import com.badlogic.gdx.graphics.Color;
import com.leepresswood.wizard.helpers.datapackage.SpellPackage;
import com.leepresswood.wizard.helpers.enums.SpellCategory;
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
	
	/**
	 * Level up logic:
	 * There will be three paths of level ups for each wizard. Each path will have
	 * around six skills. Each skill requires that path's previous skill in order
	 * to be chosen. The skills are broken into six "levels" where level one is
	 * the base level for that skill. The number of levels into that path will
	 * directly relate to the cost of that skill. That is to say that the cost
	 * goes up as you move through the levels of that path. This is for balance.
	 * 
	 * The paths will represent three different versions of fighting strategy.
	 * They will probably be named something clever as the game-making process
	 * goes on, but the general idea is that there will be a skillset based upon
	 * direct damage (fireball, aether orb, arrows, fire wall), another based upon 
	 * indirect damage (traps, mines, tricks, summons), and one based upon defense
	 * (lower damage taken, damage nearby enemies with a burn)
	 * 
	 * There will be a (few?) subpath(s) for each of the three main paths. They
	 * will upgrade the path's effects without raising the base path skill.
	 * As an example, the Void Wizard's main attack spell is a floating ball.
	 * Leveling up the main path will increase the number of balls that are released
	 * from the Wizard. Leveling the subpath will enhance the properties of each
	 * orb, such as increasing the speed, making the orbs heat-seeking, or
	 * adding more knockback.
	 * 
	 * Not every every skill obtained will increase the number of available castable
	 * spells on the game screen, but some of them will.
	 * 
	 * As far as implementation is concerned, new spells should be added to an
	 * array on the screen. New effects should be added as a Buff that is
	 * transferred from one entity to another on contact.
	 */
	public final int TOTAL_LEVELS = 6;
	public final int COST_PER_LEVEL = 2;
	public final int ULTIMATE_COST_PER_LEVEL = 5;
	public static final int NUMBER_OF_SPELLS = 4;
	
	public int direct_levels;
	public int direct_sublevels;
	public int indirect_levels;
	public int indirect_sublevels;
	public int defense_levels;
	public int defense_sublevels;
	public int ultimate_levels;
	public int ultimate_sublevels;
	
	//This is going to be the list of available castable spells.
	public SpellPackage[] castable_spells;
	
	//Experience stuff.
	private final float EXPERIENCE_MAX = 100f;
	private float experience_current;
	
	/**
	 * @param universe Reference to Universe.
	 * @param level Will be used as a starting level when the Universe loads.
	 */
	public LevelHandler(Universe universe, int level)
	{
		this.universe = universe;
		this.level = level;
		
		//Create spell packages.
		castable_spells = new SpellPackage[NUMBER_OF_SPELLS];
		recalculate();
	}

	/**
	 * Enemy was killed, and experience was gained.
	 * @param experience Amount gained.
	 */
	public void addExperience(int experience)
	{
		//Display text for experience earned.
		universe.text_handler.createDecayText("+" + experience +  "xp", universe.screen.gui.bar_experience.x, universe.screen.gui.bar_experience.y + universe.screen.gui.bar_experience.MAX_BAR_HEIGHT, Color.YELLOW);
		
		//Increase experience (and experience bar indirectly) by this amount.
		this.experience_current += experience;
		while(this.experience_current >= EXPERIENCE_MAX)
		{
			levelUp();
			this.experience_current -= EXPERIENCE_MAX;
		}
	}
	
	/**
	 * @return Percentage to next level.
	 */
	public float getExperienceAsPercentOfLevel()
	{
		return experience_current / EXPERIENCE_MAX;
	}
	
	/**
	 * @return How many points are available.
	 */
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
	 * Are there enough points available to be spent?
	 * @param spend How much the player wants to spend.
	 * @return True if available. False otherwise.
	 */
	public boolean canSpend(int spend)
	{
		return spend < getPointsAvailable();
	}
	
	/**
	 * Player requested to spend a point.
	 * @param spend How much the player wants to spend.
	 */
	public void spend(int spend)
	{
		points_spent += spend;
	}

	/**
	 * After returning from the level store, we need to remake the spell data packages.
	 */
	public void recalculate()
   {
		//In order: Direct, Indirect, Defense, Ultimate.
		castable_spells[0] = new SpellPackage(universe.entity_handler.type, SpellCategory.DIRECT, direct_levels, direct_sublevels);
		castable_spells[1] = new SpellPackage(universe.entity_handler.type, SpellCategory.INDIRECT, indirect_levels, indirect_sublevels);
		castable_spells[2] = new SpellPackage(universe.entity_handler.type, SpellCategory.DEFENSE, defense_levels, defense_sublevels);
		castable_spells[3] = new SpellPackage(universe.entity_handler.type, SpellCategory.ULTIMATE, ultimate_levels, ultimate_sublevels);
   }
}
