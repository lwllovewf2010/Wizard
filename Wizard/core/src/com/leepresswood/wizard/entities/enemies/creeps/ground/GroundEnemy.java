package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * An enemy type that is affected by gravity.
 * @author Lee
 *
 */
public abstract class GroundEnemy extends Enemy
{
	public GroundEnemy(ScreenGame screen, float x, float y)
	{
		super(screen, x, y);
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
	public void die()
	{
	}

	@Override
	protected void updateTiming(float delta)
	{
	}
}
