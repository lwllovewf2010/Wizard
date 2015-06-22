package com.leepresswood.wizard.handlers;

import com.leepresswood.wizard.world.GameWorld;

/**
 * Level manager for the player.
 * @author Lee
 */
public class LevelHandler
{
	public GameWorld world;
	
	public int level;							//Total points available.
	public int points_spent;				//Points that have been spent on leveling.
	public int points_available;			//Points available to be spent.
	
	public LevelHandler(GameWorld world, int level)
	{
		this.world = world;
		this.level = level;
	}

	/**
	 * Do all required actions upon leveling up.
	 */
	public void levelUp()
	{
		level++;
		points_available = level - points_spent;
	}
}
