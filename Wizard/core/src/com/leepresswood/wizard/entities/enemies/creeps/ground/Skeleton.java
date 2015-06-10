package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.screens.game.GameWorld;

public class Skeleton extends GroundEnemy
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
	public void hit(Player player)
	{
		
	}
}
