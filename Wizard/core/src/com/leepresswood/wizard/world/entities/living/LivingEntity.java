package com.leepresswood.wizard.world.entities.living;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.calculators.DefenseCalculator;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;

/**
 * Parent class to both the players and the enemies. 
 */
public abstract class LivingEntity extends GameEntity
{	
	//XML Data
	public float health_max;
	public float defense;
	public float speed_max_x;
	public float accel_x;
	public float decel_x;
	public float jump_start_speed;
	
	//Movement and direction.
	public boolean moving_left;
	public boolean moving_right;
	public float speed_current_x;
	public float knockback_speed;
	
	//Knockback.
	public boolean is_invincible;
	public float invincible_time_max = 0.5f;
	public float invincible_time_current;
	public float knockback_angle;
	
	//Dying.
	public float health_current;
	public boolean is_dead;
	public boolean dying;
	
	//Jumping.
	public boolean jumping;
	public boolean on_ground;
	
	public LivingEntity(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		health_max = data.getFloat("health");
		defense = data.getFloat("defense");
		speed_max_x = data.getFloat("speed");
		accel_x = data.getFloat("acceleration");
		decel_x = data.getFloat("deceleration");
		jump_start_speed = data.getFloat("jump_speed");
		
		health_current = health_max;
	}
	
	@Override
	public void update(float delta)
	{
		//Nothing else needs to be done if we're dead.
		if(!dying)
		{
			//Movement calculation.
			calcMovement(delta);
			
			//Invincibility calculation.
			if(is_invincible)
			{
				invincible_time_current += delta;
				if(invincible_time_current >= invincible_time_max)
					is_invincible = false;
			}
			//else
			//	enemyCollision();
		}
		
		//Die in accordance with the type of enemy this is.
		if(dying || getDeathStatus())
		{
			dying = true;
			die(delta);
		}
		
		//Update parts to the new position.
		for(Box2DSprite p : parts)
			p.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{//We will assume the parts are ordered correctly while drawing.
		for(Box2DSprite p : parts)
			p.draw(batch);
	}
	
	@Override
	public void doHit(GameEntity entity)
	{
		
	}
	
	/**
	 * Overloading the doHit function for entity-ground collisions.
	 * @param body
	 */
	public void doHit(Body body)
	{
		/**
		 * Logic:
		 * This is being called because there was a collision. There's no debate there.
		 * We're trying to determine the side of the collision. Hitting from this entity's
		 * bottom will turn on jumping once more.
		 * Bottom-touching will be determined by the Y-positioning of the bodies. If this
		 * entity's Y-position is higher than the Y-position of the collided body, we can jump again.
		 */
		if(parts[0].body.getPosition().y >= body.getPosition().y)
			on_ground = true;
	}
	
	/**
	 * Damage was taken.
	 * @param amount Amount of damage taken.
	 */
	public void damage(float amount)
	{//Damage was done, but defense must be taken into consideration.
		health_current -= DefenseCalculator.damageAfterDefense(amount, defense);
	}
	
	@Override
	public boolean getDeathStatus()
	{
		return health_current <= 0f;
	}
}
