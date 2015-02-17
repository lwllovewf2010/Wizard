package com.leepresswood.wizard.entities.spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Bolt Spells are a type of spell characterized by a single cast per click. One sprite to this type of spell.
 * @author Lee
 *
 */
public abstract class BoltSpell extends Spell
{
	protected float speed_x, speed_y;
	protected float SPEED_MAX;
	
	public BoltSpell(ScreenGame screen, Vector2 from, Vector2 to)
	{
		super(screen, from, to);
		
		//Determine the initial speeds from the max speed.
		setSpeedMax();
		float angle = to.sub(from).angle();
		speed_x = SPEED_MAX * MathUtils.cosDeg(angle);
		speed_y = SPEED_MAX * MathUtils.sinDeg(angle);
	}
	
	/**
	 * Set the speed to use in position calculations.
	 */
	protected abstract void setSpeedMax();
	
	@Override
	protected void makeSpriteBounds()
	{
		float width = 1;
		float height = width;
		
		sprite.setBounds(from.x - width / 2f, from.y - height / 2f, width, height);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
