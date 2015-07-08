package com.leepresswood.wizard.world.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.enemies.Enemy;
import com.leepresswood.wizard.world.entities.spells.BoltSpell;

/**
 * The aftermath of a fireball colliding with an enemy.
 *
 * @author Lee
 */
public class Explosion extends BoltSpell
{
	public Explosion(Texture t, float x, float y){super(t, x, y);}

	public Explosion(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, data, 0);		
	}

	@Override
	protected void setBodies(float x, float y, float width, float height)
	{
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		sprite.setSize(width, height);
		sprite.setPosition(from.x - sprite.getWidth() / 2f, from.y -  sprite.getHeight() / 2f);
		
		//We will only have one body here.
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.SPELL_TRANSPARENT);
		parts[0].body.setGravityScale(0f);
	}

	@Override
	protected void calcMovement(float delta)
	{//The spell doesn't move, but it does shrink over time.
		float new_size_percent = 1f - time_alive_current / time_alive_max;
		sprite.setSize(new_size_percent * sprite.getWidth(), new_size_percent * sprite.getHeight());
		
		//Reset the center.
		parts[0].sprite.setPosition(from.x - sprite.getWidth() / 2f, from.y -  sprite.getHeight() / 2f);
		parts[0].body.getFixtureList().get(0).getShape().setRadius(sprite.getWidth());
	}

	@Override
	public void doHit(GameEntity entity)
	{
		//enemy.health_max -= damage;
	}
}
