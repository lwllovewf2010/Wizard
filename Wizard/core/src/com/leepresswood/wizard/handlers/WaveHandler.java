package com.leepresswood.wizard.handlers;

import com.leepresswood.wizard.world.GameWorld;

/**
 * Handles the beginning and ending of waves.
 * @author Lee
 */
public class WaveHandler
{
	public GameWorld world;
	
	public boolean start_wave;				//Wave was requested to be started.
	public int wave_number;					//Wave number. 1-based. 
	public boolean mid_wave;				//Are we currently within a wave?
	
	public WaveHandler(GameWorld world)
	{
		this.world = world;
		
		wave_number = 1;
	}
	
	/**
	 * Check to see if the wave is over.
	 */
	public void update()
	{
		if(start_wave && !mid_wave)
			waveBegin();
		if(mid_wave && world.entity_handler.enemies != null && world.entity_handler.enemies.isEmpty())
			waveEnd();
	}
	
	/**
	 * Wave has begun.
	 */
	private void waveBegin()
	{
		start_wave = false;
		mid_wave = true;
		world.entity_handler.spawnWave();
	}
	
	/**
	 * Wave has ended.
	 */
	private void waveEnd()
	{
		wave_number++;
		start_wave = false;
		mid_wave = false;
		world.level_handler.levelUp();
	}
}
