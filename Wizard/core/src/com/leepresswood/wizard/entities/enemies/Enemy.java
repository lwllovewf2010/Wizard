package com.leepresswood.wizard.entities.enemies;

import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{

	public Enemy(ScreenGame screen, float x, float y)
	{
		super(screen, x, y);
	}
	
}
