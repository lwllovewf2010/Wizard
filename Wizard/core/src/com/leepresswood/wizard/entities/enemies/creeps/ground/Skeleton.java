package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.screens.game.ScreenGame;


public class Skeleton extends Enemy
{

	public Skeleton(ScreenGame screen, float x, float y, Element element)
	{
		super(screen, x, y, element);
	}

	@Override
	protected void calcMovementX(float delta)
	{
	}

	@Override
	protected void calcMovementY(float delta)
	{
	}

	@Override
	protected void setSprites(ScreenGame screen, float x, float y)
	{
	}

	@Override
	protected void setMovementVariables()
	{
	}

	@Override
	public void attack(Vector2 touch)
	{
	}

	@Override
	public void die()
	{
	}

	@Override
	protected void updateTiming(float delta)
	{
	}

	@Override
	public void draw(SpriteBatch batch)
	{
	}
	
}
