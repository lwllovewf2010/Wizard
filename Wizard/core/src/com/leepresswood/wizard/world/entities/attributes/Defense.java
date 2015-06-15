package com.leepresswood.wizard.world.entities.attributes;

/**
 * Defense will directly affect the amount of damage the player takes.
 *
 * @author Lee
 */
public class Defense
{
	/**
	 * Damage was dealt to an entity with a certain defense. Do the correct damage amount.
	 * @param damage Damage done.
	 * @param defense Defense of the entity.
	 * @return Actual damage done.
	 */
	public static float damageAfterDefense(float damage, float defense)
	{
		if(damage > 25)
			damage = 25;
		else if(damage < -25)
			damage = -25;
		
		//There will be a range of defense from -25 to 25. The percetage boost/decay of the damage is the percentage of that range.
		float percent = defense / 25f;
		
		return damage - (damage * percent);
	}
}
