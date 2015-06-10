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
	}

	@Override
	public void doHit(Player player)
	{
		
	}
}
