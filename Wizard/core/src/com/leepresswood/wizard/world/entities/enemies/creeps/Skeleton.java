package com.leepresswood.wizard.world.entities.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.bodyparts.BodyPart;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.Player;

public class Skeleton extends Enemy
{
	public Skeleton(Universe universe, float x, float y, Element element)
	{
		super(universe, x, y, element);
	}
	
	@Override
	protected void setBodies(float x, float y, float width, float height)
	{
		//For now, we'll just have one body part.
		body_parts = new BodyPart[1];
		
		body_parts[0] = new BodyPart(new Sprite(texture), universe.world_handler.createDynamicEntity(x, y, width, height));
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
