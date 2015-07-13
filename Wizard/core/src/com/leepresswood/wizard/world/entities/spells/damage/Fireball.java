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
 * Bolt spell that is affected by gravity. Explodes on impact.
 * @author Lee
 */
public class Fireball extends BoltSpell
{ 
	private Element explosion_data;
	
	public Fireball(Texture t, float x, float y){super(t, x, y);}
	
	public Fireball(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, data, level);
		
		//Read the data from the XML file.
		float impulse = data.getFloat("impulse");
		explosion_data = data.getChildrenByName("level").get(level).getChildByName("explosion");
		
		//Set impulse and the initial linear velocity. Gravity will do the rest.
		parts[0].body.getFixtureList().get(0).setRestitution(impulse);
		parts[0].body.setLinearVelocity(speed_x, speed_y);
	}
	
	@Override
   protected void setBodies(float x, float y, float width, float height)
   {
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		//We will only have one body here.
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.SPELL_SOLID);
   }

	@Override
   protected void calcMovement(float delta)
   {//Physics takes over from here. No need to change what Box2D calculates.
   }

	@Override
   public void doHit(GameEntity entity)
   {//We want this to only explode against enemies.
		if(entity instanceof Enemy)
		{
			//Remove life.
			((Enemy) entity).damage(damage);
			
			//Destroy this bolt.
			active = false;
			
			//The collision death of this bolt will create an explosion.
			//Note: Instantiating this spell will create a new body in the world while we're already rendering. This isn't allowed, so we need to add it to a body queue instead.
			Vector2 location = new Vector2(parts[0].sprite.getX() + parts[0].sprite.getWidth() / 2f, parts[0].sprite.getY() + parts[0].sprite.getHeight() / 2f);
			universe.entity_handler.spell_queue.add(new Explosion(universe, location, location, explosion_data, level));
		}
   }
}
