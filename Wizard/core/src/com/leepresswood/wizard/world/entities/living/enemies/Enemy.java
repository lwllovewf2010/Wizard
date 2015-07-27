package com.leepresswood.wizard.world.entities.living.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.LivingEntity;
import com.leepresswood.wizard.world.entities.living.player.Player;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends LivingEntity
{
	//XML Data
	public float knockback_damage;
	public float knockback_force;
	public int experience;
	
	public boolean do_jump;
	
	private final float DIE_TIME_MAX = 1f;
	private float die_time_current;
	
	private boolean get_experience;
	
	public Enemy(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		knockback_force = data.getFloat("knockback_force");
		knockback_damage = data.getFloat("knockback_damage");
		experience = data.getInt("experience");
		health_max = data.getFloat("health");
	}

	@Override
	protected void calcMovement(float delta)
	{//General AI tells the enemies to move toward the center.
		//X
		if(!is_invincible)
		{
			if(parts[0].sprite.getX() > universe.screen.universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f)
				force = -accel_x;
			else if(parts[0].sprite.getX() < universe.screen.universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f - parts[0].sprite.getWidth())
				force = accel_x;
			else
				force = 0f;
			
			for(Box2DSprite p : parts)
				//Only apply more force if the current horizontal speed is less than the max speed or if the force is in the opposite direction of the movement.
				if(Math.pow(p.body.getLinearVelocity().x, 2f) < Math.pow(speed_max_x, 2f) || Math.signum(p.body.getLinearVelocity().x) != Math.signum(force))
					p.body.applyForceToCenter(force, 0f, true);
		}
		
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//Damage the player.
		if(entity instanceof Player)
		{
			((Player) entity).damage(knockback_damage, knockback_force, parts[0].body.getPosition());
		}
   }
	
	/**
	 * Entity seeks to attack a targeted point in the world. Cast at or in the direction of that point.
	 * @param point The coordinate in the world that was clicked.
	 */
	public abstract void attack(Vector2 touch);
	
	@Override
	protected String getTextureBaseString()
	{
		return "enemies/";
	}
	
	@Override
	public void die(float delta)
	{//New note: This is only called after it has been determined that the entity is dying. No need to check for dying.
		//Update the timing and change the alpha.
		die_time_current += delta;
		if(die_time_current >= DIE_TIME_MAX)
		{
			is_dead = true;
			die_time_current = DIE_TIME_MAX;
		}
		
		if(!get_experience)
		{
			universe.level_handler.addExperience(experience);
			get_experience = true;
		}

		for(Box2DSprite p : parts)
			p.sprite.setAlpha(1f - die_time_current / DIE_TIME_MAX);
	}
}
