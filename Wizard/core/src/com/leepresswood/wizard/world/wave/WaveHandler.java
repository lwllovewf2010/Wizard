package com.leepresswood.wizard.world.wave;

import com.leepresswood.wizard.world.GameWorld;

/**
 * Handles the beginning and ending of waves.
 * 
 * @author Lee
 *
 */
public class WaveHandler
{
	public GameWorld world;
	
	public boolean mid_wave;				//Are we currently within a wave?
	
	public WaveHandler(GameWorld world)
	{
		this.world = world;
	}
	
	/**
	 * Wave has begun.
	 */
	public void waveBegin()
	{
		mid_wave = true;
	}
	
	/**
	 * Wave has ended.
	 */
	public void waveEnd()
	{
		mid_wave = false;
		world.level_handler.levelUp();
	}
}
