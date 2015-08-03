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
		speed_max = getSpeed(data);
		knockback = getKnockback(data);
		
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
		if(main != null && main.get("damage",  null) != null)
			damage = main.getFloat("damage");
		if(sub != null && sub.get("damage",  null) != null)
			damage = sub.getFloat("damage");
		
		return damage;
	}
	
	/**
	 * @return The damage of the spell.
	 */
	private float getSpeed(SpellPackage data)
	{
		Element basic = SpellPackage.getBasic(data);
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		float speed = basic.getFloat("speed");
		if(main != null && main.get("speed",  null) != null)
			speed = main.getFloat("speed");
		if(sub != null && sub.get("speed",  null) != null)
			speed = sub.getFloat("speed");
		
		return speed;
	}
	
	/**
	 * @return The damage of the spell.
	 */
	private float getKnockback(SpellPackage data)
	{
		Element basic = SpellPackage.getBasic(data);
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		float knockback = basic.getFloat("knockback");
		if(main != null && main.get("knockback",  null) != null)
			knockback = main.getFloat("knockback");
		if(sub != null && sub.get("knockback",  null) != null)
			knockback = sub.getFloat("knockback");
		
		return knockback;
	}
}
