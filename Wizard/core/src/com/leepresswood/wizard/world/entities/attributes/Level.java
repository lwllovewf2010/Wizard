package com.leepresswood.wizard.world.entities.attributes;

import com.leepresswood.wizard.world.GameWorld;

/**
 * Level manager for the player.
 *
 * @author Lee
 */
public class Level
{
	public GameWorld world;
	
	public int level;
	
	public Level(GameWorld world)
	{
		this.world = world;
		
		//Player will start at level 1.
		level = 1;
	}
}
