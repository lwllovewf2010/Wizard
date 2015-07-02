package com.leepresswood.wizard.world.entities.living.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.calculators.DefenseCalculator;
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
	
	public boolean do_jump;
	
	private final float DIE_TIME_MAX = 1f;
	private float die_time_current;
	
	public Enemy(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		knockback_force = data.getFloat("knockback_force");
		knockback_damage = data.getFloat("knockback_damage");
		health_max = data.getFloat("health");
		
		System.out.println(
				"\tKnockback Force: " + knockback_force
				+ "\n\tKnockback Damage: " + knockback_damage
				+ "\n\tHealth: " + health_max
		);
	}

	@Override
	protected void calcMovementX(float delta)
	{//General AI tells the enemies to move toward the center.
		/*if(sprite.getX() > universe.screen.world.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f)
			speed_current_x -= accel_x * delta;
		else if(sprite.getX() < universe.screen.world.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f - sprite.getWidth())
			speed_current_x += accel_x * delta;
		else
			speed_current_x = 0f;
		
		//Limit speed by max.
		if(Math.abs(speed_current_x) > speed_max_x)
			speed_current_x = speed_max_x * Math.signum(speed_current_x);*/
	}
	
	@Override
	protected void calcMovementY(float delta)
	{//If we asked the enemy to jump, do a jump calculation.
		if(do_jump)
		{
			
		}
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//Damage the player.
		if(entity instanceof Player)
			((Player) entity).damage(knockback_damage);
   }
	
	////@Override
	//public void enemyCollision()
	//{
		/*for(Spell s : universe.entity_handler.spells)
		{
			//To make this horrible O(n^3) function faster, we're only going to check the spells that are within a certain radius.S
			if(25f > Vector2.dst2(s.bounds[0].x + s.bounds[0].width / 2f, s.bounds[0].y + s.bounds[0].height / 2f, bodies[0].x + bodies[0].width / 2f, bodies[0].y + bodies[0].height / 2f))
			{
				for(Rectangle r : s.bounds)
				{
					for(Rectangle r2 : this.bodies)
					{
						if(r2.overlaps(r))
						{
							//Get the angle between the enemy and the attack. The angle of the knockback will be the flipped version of this angle.
							knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(r2.y + r.height / 2f - sprite.getY() - sprite.getHeight() / 2f, r.x + r.width / 2f - sprite.getX() - sprite.getWidth() / 2f);
							knockback_angle += 180f;
							
							//Get damage/effects.
							s.doHit(this);
							s.hitBy(this);
							
							//Set the knockback and invincibility.
							knockback_speed = ((BoltSpell) s).knockback;
							is_being_knocked_back = true;
							is_invincible = true;
							invincible_time_current = 0f;
						}
					}
				}
			}
		}*/
	//}
	
	/**
	 * Entity seeks to attack a targeted point in the world. Cast at or in the direction of that point.
	 * @param point The coordinate in the world that was clicked.
	 */
	public abstract void attack(Vector2 touch);
	
	@Override
	public void damage(float amount)
	{
		health_max -= DefenseCalculator.damageAfterDefense(amount, defense);
	}
	
	@Override
	public boolean getDeathStatus()
	{
		return health_max <= 0f;
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

		for(Box2DSprite p : parts)
			p.sprite.setAlpha(1f - die_time_current / DIE_TIME_MAX);
	}
}
