package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * An enemy type that is affected by gravity.
 * @author Lee
 *
 */
public abstract class GroundEnemy extends Enemy
{
	public GroundEnemy(GameWorld world, float x, float y, Element element)
	{
		super(world, x, y, element);
	}
}
