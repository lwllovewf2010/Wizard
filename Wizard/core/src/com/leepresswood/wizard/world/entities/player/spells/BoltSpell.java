package com.leepresswood.wizard.world.entities.player.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;

/**
 * Bolt Spells are a type of spell characterized by a single cast per click. One sprite to this type of spell.
 * @author Lee
 *
 */
public abstract class BoltSpell extends Spell
{
	public float damage;
	public float speed_x, speed_y;
	public float speed_max;
	public float knockback;
	
	public BoltSpell(Texture t, float x, float y){super(t, x, y);}
	
	public BoltSpell(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		super(world, from, to, data);
		
		//Read the data from the XML file.
		damage = data.getFloat("damage");
		speed_max = data.getFloat("speed");
		knockback = data.getFloat("knockback");
		
		//Determine the initial speeds from the max speed and angle between the vectors.
		float angle = to.sub(from).angle();
		speed_x = speed_max * MathUtils.cosDeg(angle);
		speed_y = speed_max * MathUtils.sinDeg(angle);
		
		System.out.println(
			"\tAngle: " + angle 
			+ "\n\tSpeed X: " + speed_x 
			+ "\n\tSpeed Y: " + speed_y
			+ "\n\tDamage: " + damage
			+ "\n\tKnockback: " + knockback
		);
	}
	
	@Override
	protected Rectangle[] makeSprites()
	{
		float width = 2f;
		float height = width;
		
		sprite.setBounds(from.x - width / 2f, from.y - height / 2f, width, height);
		
		return new Rectangle[]{sprite.getBoundingRectangle()};
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
