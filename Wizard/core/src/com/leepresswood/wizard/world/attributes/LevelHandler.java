package com.leepresswood.wizard.world.attributes;

import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.wave.WaveHandler;

/**
 * Level manager for the player.
 *
 * @author Lee
 */
public class LevelHandler
{
	public GameWorld world;
	
	public int level;
	public int points_spent;
	public int points_available;
	
	public LevelHandler(GameWorld world, int level)
	{
		this.world = world;
		this.level = level;
	}
	
	/**
	 * Wave began. Do the correct steps.
	 */
	public void waveBegin()
	{
		mid_wave = true;
	}
	
	/**
	 * Wave ended. Do the correct steps.
	 */
	public void waveEnd()
	{
		mid_wave = false;
		level++;
		points_available = level - points_spent;
	}
}
