package com.leepresswood.wizard.world.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.enemies.Enemy;

/**
 * Bolt Spells are a type of spell characterized by a single cast per click.
 * @author Lee
 *
 */
public abstract class BoltSpell extends Spell
{
	public float damage;
	public float angle;
	public float speed_x, speed_y;
	public float speed_max;
	public float knockback;
	
	public BoltSpell(Texture t, float x, float y){super(t, x, y);}
	
	public BoltSpell(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, from, data, level);
		
		//Read the data from the XML file.
		damage = data.getFloat("damage");
		speed_max = data.getFloat("speed");
		knockback = data.getFloat("knockback");
		
		//Determine the initial speeds from the max speed and angle between the vectors.
		angle = new Vector2(to).sub(from).angle();
		speed_x = speed_max * MathUtils.cosDeg(angle);
		speed_y = speed_max * MathUtils.sinDeg(angle);
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//We want this to only hit enemies.
		if(entity instanceof Enemy)
		{
			((Enemy) entity).damage(damage);
			
			//Upon impact, we want to set knockback as well. To do this, get the angle between the two and add a knockback force.
			float angle_between = entity.parts[0].body.getPosition().angle(parts[0].body.getPosition());
			entity.parts[0].body.setLinearVelocity(knockback * MathUtils.cosDeg(angle_between), knockback * MathUtils.sinDeg(angle_between));
		}
   }
}
