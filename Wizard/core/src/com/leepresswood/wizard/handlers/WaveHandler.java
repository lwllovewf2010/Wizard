package com.leepresswood.wizard.handlers;

import com.leepresswood.wizard.world.GameWorld;

/**
 * Handles the beginning and ending of waves.
 * @author Lee
 */
public class WaveHandler
{
	public GameWorld world;
	
	public int wave_number;
	public boolean mid_wave;				//Are we currently within a wave?
	
	public WaveHandler(GameWorld world)
	{
		this.world = world;
		
		wave_number = 1;
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
		wave_number++;
		mid_wave = false;
		world.level_handler.levelUp();
	}
}
