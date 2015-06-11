package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.screens.game.GameWorld;

public class Skeleton extends Enemy
{
	public Skeleton(GameWorld world, float x, float y, Element element)
	{
		super(world, x, y, element);
	}
	
	@Override
	public void attack(Vector2 touch)
	{
		//Skeletons are dumb. They don't have any attacks. Their only damage happens by bumping into things.
	}

	@Override
	public void doHit(Player player)
	{
		world.screen.gui.bar_health.change(-knockback_damage);
	}
}
