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
	public float jump_start_speed;
	
	//Movement and direction.
	public boolean moving_left;
	public boolean moving_right;
	public float force;
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
	public final float JUMP_TIME_MAX = 0.15f;
	public final float JUMP_TIME_MIN = 0.07f;
	public float jump_time_current;
	public boolean mid_jump;
	
	public LivingEntity(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		health_max = data.getFloat("health");
		defense = data.getFloat("defense");
		speed_max_x = data.getFloat("speed");
		accel_x = data.getFloat("acceleration");
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
	
	/**
	 * Overloading the doHit function for entity-ground collisions.
	 * @param body
	 */
	public void doHit(Body body)
	{
		/**
		 * Logic:
		 * This is being called because there was a collision. There's no debating that.
		 * We're trying to determine the side of the collision. Hitting from this entity's
		 * bottom will turn on jumping once more.
		 * Bottom-touching will be determined by the Y-positioning of the bodies. If this
		 * entity's Y-position is higher than the Y-position of the collided body, we can jump again.
		 */
		if(parts[0].body.getPosition().y - parts[0].body.getFixtureList().get(0).getShape().getRadius() >= body.getPosition().y)
		{
			on_ground = true;
			mid_jump = false;
			jump_time_current = 0f;
			parts[0].body.setLinearVelocity(parts[0].body.getLinearVelocity().x, 0f);
		}
		
		//On top of jump-based collision, we also need to turn off the player's momentum upon hitting a wall.
		//Right
		/*if(parts[0].body.getLinearVelocity().x > 0f && parts[0].body.getPosition().x <= body.getPosition().x)
			parts[0].body.setLinearVelocity(0f, parts[0].body.getLinearVelocity().y);
		
		//Left
		if(parts[0].body.getLinearVelocity().x < 0f && parts[0].body.getPosition().x >= body.getPosition().x)
			parts[0].body.setLinearVelocity(0f, parts[0].body.getLinearVelocity().y);*/
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
