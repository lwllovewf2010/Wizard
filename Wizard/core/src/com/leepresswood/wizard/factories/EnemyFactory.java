package com.leepresswood.wizard.factories;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.enums.Enemies;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.enemies.creeps.Skeleton;

/**
 * Creates and manages enemy spawning.
 * @author Lee
 *
 */
public class EnemyFactory
{
	private Universe world;
	private Element data_root;
	
	public float time_recharge_next = 0.5f;
	public float time_recharge_current;	
	
	public EnemyFactory(Universe world)
	{
		this.world = world;
		time_recharge_current = time_recharge_next;

		//Read information about the enemies from the XML data file. Stores this information into a data root node that can be used to gather data about each enemy.
		try
		{
			data_root = new XmlReader().parse(Gdx.files.internal("data/enemies.xml"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Update the timing to see if it's possible to spawn another enemy.
	 * @param delta
	 */
	public void update(float delta)
	{//If time to recharge is above the max, we can spawn again. We want to limit this value to avoid a potential overflow (in a few billion seconds).
		if(!isReady())
			time_recharge_current += delta;
	}
	
	/**
	 * Create a new enemy. Spawns at a random side.
	 * @param type The type of enemy to create.
	 * @return An instance of the desired enemy. Will be null if enemy can't be summoned at this time due to recharging.
	 */
	public Enemy getEnemy(String type)
	{
		return getEnemy(type, MathUtils.randomBoolean());
	}
	
	/**
	 * Create a new enemy with the given data.
	 * @param type The type of enemy to create.
	 * @param left Spawn location. True if left. False if right.
	 * @return An instance of the desired enemy. Will be null if enemy can't be summoned at this time due to recharging.
	 */
	public Enemy getEnemy(String type, boolean left)
	{
		time_recharge_current = 0f;
		
		//Left or right side?
		float x = left ? -3f : world.map_camera_handler.WORLD_TOTAL_HORIZONTAL;
		float y = world.map_camera_handler.GROUND;
		
		Enemy e = null;			
		switch(Enemies.valueOf(type))
		{
			case SKELETON:
				e = new Skeleton(world, x, y, data_root.getChildByName("skeleton"));
				break;
		}
			
		return e;
	}
	
	public boolean isReady()
	{
		return time_recharge_current >= time_recharge_next;
	}
}
