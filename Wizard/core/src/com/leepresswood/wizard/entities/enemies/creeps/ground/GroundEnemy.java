package com.leepresswood.wizard.entities.enemies.creeps.ground;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * An enemy type that is affected by gravity.
 * @author Lee
 *
 */
public abstract class GroundEnemy extends Enemy
{
	public HashMap<String, Boolean> spell_list;
	public boolean being_knocked_back;
	public float knockback_time_final = 0.5f;
	public float knockback_time_current;
	public float knockback_speed = 10f * Gdx.graphics.getDeltaTime();
	public float knockback_angle;
	
	public GroundEnemy(ScreenGame screen, float x, float y, Element element)
	{
		super(screen, x, y, element);
		
		spell_list = new HashMap<String, Boolean>();
	}

	@Override
	protected void calcMovementY(float delta)
	{//Ground enemies are affected by gravity.
		//The enemy is stunned during a knockback. All other motion is locked.
		if(being_knocked_back)
		{
			knockback_time_current += delta;
			if(knockback_time_current >= knockback_time_final)
			{
				being_knocked_back = false;
			}
			
			sprite.translate(knockback_speed * MathUtils.cosDeg(knockback_angle), knockback_speed * MathUtils.sinDeg(knockback_angle));
		}
		else
		{
			//If we asked the enemy to jump, do a jump calculation.
			if(do_jump)
			{
				do_jump = false;
				speed_current_y += jump_start_speed;
			}
			
			//Do a fall calculation by simulating gravity.
			if(sprite.getY() > screen.world.GROUND)
				speed_current_y -= delta * screen.world.GRAVITY;
			
			//Move in the Y direction.
			sprite.translateY(speed_current_y * delta);
		}
		
		//Set a hard limit for how low the entity can go. If they pass this limit, they're on a solid block. Reset the variables.
		if(sprite.getY() < screen.world.GROUND)
		{
			sprite.setY(screen.world.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
	}
	
	/**
	 * Entity was hit. Take damage and do knockback.
	 * @param damage The damage amount to subtract from the health. If this goes to or below zero, set the dead flag.
	 */
	public void hit(Spell s)
	{
		if(!spell_list.containsKey(s.toString()))
		{
			//Get the enemy's location in relation to the attack. This will allow us to calculate the knockback.
	
			
			//Check to see if the enemy has died.
			
			
			//Get the angle between the enemy and the attack.
			knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(s.sprite.getY() + s.sprite.getHeight() / 2f - sprite.getY() - sprite.getHeight() / 2f, s.sprite.getX() + s.sprite.getWidth() / 2f - sprite.getX() - sprite.getWidth() / 2f);
		
			//The angle of the knockback will be the flipped version of this angle. 
			knockback_angle += 180f;
			
			//Do the knockback and set invincibility to this particular spell.
			being_knocked_back = true;
			knockback_time_current = 0f;
			spell_list.put(s.toString(), true);
		}
	}
}
