package com.leepresswood.wizard.entities.enemies;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.creeps.ground.Skeleton;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.entities.spells.damage.Aether;
import com.leepresswood.wizard.entities.spells.damage.Fireball;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Creates and manages enemy spawning.
 * @author Lee
 *
 */
public class EnemyFactory
{
	private ScreenGame screen;
	private Element data_root;
	
	public float time_recharge_next;
	public float time_recharge_current;	
	
	public EnemyFactory(ScreenGame screen)
	{
		this.screen = screen;

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
		if(time_recharge_current < time_recharge_next)
			time_recharge_current += delta;
	}
	
	/**
	 * Create a new spell with the given data.
	 * @param type The type of enemy to create.
	 * @param left Spawn location. True if left. False if right.
	 * @return An instance of the desired enemy. Will be null if spell can't be summoned at this time due to recharging.
	 */
	public Enemy getEnemy(Class<?> type, boolean left)
	{
		//If we're allowed to spawn another enemy. This depends upon the recharge time of the last spawn.
		if(time_recharge_current >= time_recharge_next)
		{
			time_recharge_current = 0f;
			
			//Left or right side?
			float x = left ? 0 : screen.world.WORLD_TOTAL_HORIZONTAL;
			float y = screen.world.GROUND;
			
			Enemy e = null;			
			if(type == Skeleton.class)
				e = new Skeleton(screen, x, y, data_root.getChildByName("skeleton"));			
			return e;
		}
		
		//This will happen if the player is not ready to cast.
		return null;
	}
	
}
