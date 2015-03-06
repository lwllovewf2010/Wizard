package com.leepresswood.wizard.entities.enemies;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{
	public boolean do_jump;
	
	public Enemy(ScreenGame screen, float x, float y, Element element)
	{
		super(screen, x, y);
	}
	
}
