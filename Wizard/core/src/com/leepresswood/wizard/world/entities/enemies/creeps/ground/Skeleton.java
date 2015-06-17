package com.leepresswood.wizard.world.entities.enemies.creeps.ground;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.Player;

public class Skeleton extends Enemy
{
	public Skeleton(GameWorld world, float x, float y, Element element)
	{
		super(world, x, y, element);
	}
	
	@Override
	public void attack(Vector2 touch)
	{
		//Skeletons don't have any attacks. Their only damage happens by bumping into things.
	}

	@Override
	public void doHit(Player player)
	{
		player.damage(knockback_damage);
	}

	@Override
	public void hitBy(Player player)
	{
	}
}
