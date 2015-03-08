package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.screens.game.ScreenGame;


public class Skeleton extends GroundEnemy
{

	public Skeleton(ScreenGame screen, float x, float y, Element element)
	{
		super(screen, x, y, element);
	}
	
	@Override
	public void attack(Vector2 touch)
	{
	}	
}
