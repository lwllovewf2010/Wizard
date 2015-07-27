package com.leepresswood.wizard.world.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.living.enemies.Enemy;
import com.leepresswood.wizard.world.entities.spells.BoltSpell;

/**
 * Bolt spell that is not affected by gravity. Goes through walls and floors.
 * @author Lee
 */
public class Aether extends BoltSpell
{
	public boolean follow;
	private final float RECALCULATE_MAX_DISTANCE = 225f;
	private final float RECALCULATE_TIME = 0.01f;
	private final float RECALCULATE_MAX_ANGLE_DIFFERENCE = 2f;
	private float recalculate_current;
	
	//For multiple bolts.
	private final float ROTATE_SPLIT_ANGLE = 180f;
	
	public Aether(Texture t, float x, float y, float width, float height){super(t, x, y, width, height);}
	
	public Aether(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, data, level);
		
		//Get XML data.
		follow = data.getChildrenByName("level").get(level).getBoolean("follow");
		
		//Aether will be split into multiple bolts. Each split should move and collide individually.
		int split_count = data.getChildrenByName("level").get(level).getInt("split");
		float rotate_by = ROTATE_SPLIT_ANGLE / (split_count + 1);
		for(int i = 1; i < split_count; i++)
		{
			//The main aether bolt will go straight forward. Every other one will be offset by an angle.
			Vector2 new_to = new Vector2(to).sub(from);
			
			/* Half the bolts go in one direction, and half go the other way. Because we will always have an odd number, there
			 * will always be one that is the center bolt. That's the one this instance of the class refers to. The others
			 * will be provided by this class free of charge to the player.
			*/
			if(i <= split_count / 2)
				new_to.rotate(-i * rotate_by);
			else
				new_to.rotate((i - split_count / 2) * rotate_by);
			
			universe.entity_handler.spells.add(new Aether(universe, from, new Vector2(from).add(new_to), data, level, i));
		}
	}
	
	/**
	 * This is a private version of the spell that can be used to create splits.
	 * Send this instance directly into the spell list.
	 */
	private Aether(Universe world, Vector2 from, Vector2 to, Element data, int level, int count)
	{
		super(world, from, to, data, level);
	}

	@Override
   protected void setBodies(float x, float y, float width, float height)
   {
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		//We will only have one body here, but we don't want gravity affecting the body.
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.SPELL_TRANSPARENT);
		parts[0].body.setGravityScale(0f);
   }

	@Override
   protected void calcMovement(float delta)
   {//Determine how aether will move.
		if(follow)
		{
			recalculate_current += delta;
			if(recalculate_current >= RECALCULATE_TIME)
			{
				recalculate_current -= RECALCULATE_TIME;
			
				//Find the nearest enemy.
				float enemy_distance_min = RECALCULATE_MAX_DISTANCE;
				Enemy enemy_index = null;
				
				for(Enemy e : universe.entity_handler.enemies)
				{	
					float length = new Vector2(parts[0].sprite.getX() + parts[0].sprite.getWidth() / 2f - (e.parts[0].sprite.getX() + e.parts[0].sprite.getWidth() / 2f), parts[0].sprite.getY() + parts[0].sprite.getHeight() / 2f - (e.parts[0].sprite.getY() + e.parts[0].sprite.getHeight() / 2f)).len2();
					if(length < enemy_distance_min)
					{
						enemy_distance_min = length;
						enemy_index = e;
					}
				}
				
				//Change direction toward that enemy if it exists.
				if(enemy_index != null)
				{
					//Get the angle between the spell's movement direction and the direction toward the closest enemy.
					float d_angle =  new Vector2(enemy_index.parts[0].sprite.getX() + enemy_index.parts[0].sprite.getWidth() / 2f, enemy_index.parts[0].sprite.getY() + enemy_index.parts[0].sprite.getHeight() / 2f).sub(new Vector2(parts[0].sprite.getX() + parts[0].sprite.getWidth() / 2f, parts[0].sprite.getY() + parts[0].sprite.getHeight() / 2f)).angle() - angle;
					if(d_angle > 180f)
						d_angle -= 360f;
					else if(d_angle < -180f)
						d_angle += 360f;
					if(Math.abs(d_angle) > RECALCULATE_MAX_ANGLE_DIFFERENCE)
						d_angle = Math.signum(d_angle) * RECALCULATE_MAX_ANGLE_DIFFERENCE;

					angle += d_angle;
					speed_x = speed_max * MathUtils.cosDeg(angle);
					speed_y = speed_max * MathUtils.sinDeg(angle);
				}
			}
		}
		
		parts[0].body.setLinearVelocity(speed_x, speed_y);
   }
}
