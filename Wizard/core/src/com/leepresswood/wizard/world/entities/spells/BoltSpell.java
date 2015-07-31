package com.leepresswood.wizard.world.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.datapackage.SpellPackage;
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
	
	public BoltSpell(Texture t, float x, float y, float width, float height){super(t, x, y,  width, height);}
	
	public BoltSpell(Universe universe, Vector2 from, Vector2 to, SpellPackage data)
	{
		super(universe, from, to, from, data);
		
		//Read the data from the XML file.
		damage = getDamage(data);
		speed_max = SpellPackage.getBasic(data).getFloat("speed");
		knockback = SpellPackage.getBasic(data).getFloat("knockback");
		
		//Determine the initial speeds from the max speed and angle between the vectors.
		angle = new Vector2(to).sub(from).angle();
		speed_x = speed_max * MathUtils.cosDeg(angle);
		speed_y = speed_max * MathUtils.sinDeg(angle);
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//We want this to only hit enemies.
		if(active)
		{
			if(entity instanceof Enemy)
			{
				((Enemy) entity).damage(damage, knockback, parts[0].body.getPosition());
			}
		}
   }
	
	/**
	 * @return The damage of the spell.
	 */
	private float getDamage(SpellPackage data)
	{
		Element basic = SpellPackage.getBasic(data);
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		float damage = basic.getFloat("damage");
		if(main.get("damage",  null) != null)
			damage = main.getFloat("damage");
		if(sub.get("damage",  null) != null)
			damage = sub.getFloat("damage");
		
		return damage;
	}
}
