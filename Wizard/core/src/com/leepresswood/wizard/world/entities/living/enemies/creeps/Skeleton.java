package com.leepresswood.wizard.world.entities.living.enemies.creeps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.living.enemies.Enemy;

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
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.ENEMY);
	}
	
	@Override
	public void attack(Vector2 touch)
	{
		//Skeletons don't have any attacks. Their only damage happens by bumping into things.
	}
}
