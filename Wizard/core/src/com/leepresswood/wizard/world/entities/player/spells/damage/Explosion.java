package com.leepresswood.wizard.world.entities.player.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.spells.BoltSpell;

/**
 * The aftermath of a fireball colliding with an enemy.
 *
 * @author Lee
 */
public class Explosion extends BoltSpell
{
	private float radius;	
	
	public Explosion(Texture t, float x, float y){super(t, x, y);}

	public Explosion(GameWorld world, Vector2 from, Vector2 to, Element data, int level)
	{
		super(world, from, to, data, 0);
		
		//Change the sprite's size to this radius.
		sprite.setOriginCenter();
		radius = data.getFloat("radius");
		sprite.setSize(radius, radius);
	}

	@Override
	protected void updatePosition(float delta)
	{
		//The spell doesn't move, but it does shrink over time.
		float new_size_percent = 1f - time_alive_current / time_alive_max;
		sprite.setSize(new_size_percent * radius, new_size_percent * radius);
		
		//Reset the bounds.
		bounds[0] = sprite.getBoundingRectangle();
	}

	@Override
	protected void updateCollision()
	{//We don't care about this spell's physics.
	}

	@Override
	public void doHit(Enemy enemy)
	{
		enemy.health -= damage;
	}
}