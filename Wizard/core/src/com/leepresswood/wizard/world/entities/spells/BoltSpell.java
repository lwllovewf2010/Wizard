package com.leepresswood.wizard.world.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;

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
		super(universe, from, to, data, level);
		
		//Read the data from the XML file.
		damage = data.getFloat("damage");
		speed_max = data.getFloat("speed");
		knockback = data.getFloat("knockback");
		
		//Determine the initial speeds from the max speed and angle between the vectors.
		angle = new Vector2(to).sub(from).angle();
		speed_x = speed_max * MathUtils.cosDeg(angle);
		speed_y = speed_max * MathUtils.sinDeg(angle);
	}
}
