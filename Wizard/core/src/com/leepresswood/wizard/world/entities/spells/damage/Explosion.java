package com.leepresswood.wizard.world.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.spells.BoltSpell;
import com.leepresswood.wizard.world.entities.spells.DynamicallyCreatedSpell;

/**
 * The aftermath of a fireball colliding with an enemy.
 *
 * @author Lee
 */
public class Explosion extends BoltSpell implements DynamicallyCreatedSpell
{	
	private float x, y, width, height;
	
	public Explosion(Texture t, float x, float y){super(t, x, y);}

	public Explosion(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, data, 0);
	}

	@Override
	protected void setBodies(float x, float y, float width, float height)
	{//Because this will be created dynamically, we don't want to instantiate a body at this instant. Call the instantiate function during the spell queue step.
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void instantiate()
	{
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setSize(width, height);
		s.setPosition(x - width / 2f, y -  height / 2f);
		
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.SPELL_TRANSPARENT);
		parts[0].body.setGravityScale(0f);
	}	

	@Override
	protected void calcMovement(float delta)
	{//The spell doesn't move, but it does shrink over time.
		float new_size_percent = 1f - time_alive_current / time_alive_max;
		parts[0].sprite.setSize(new_size_percent * parts[0].sprite.getWidth(), new_size_percent * parts[0].sprite.getHeight());
		
		//Reset the center.
		parts[0].sprite.setPosition(from.x - parts[0].sprite.getWidth() / 2f, from.y -  parts[0].sprite.getHeight() / 2f);
		parts[0].body.getFixtureList().get(0).getShape().setRadius(parts[0].sprite.getWidth());
	}

	@Override
	public void doHit(GameEntity entity)
	{
		//enemy.health_max -= damage;
	}
}
