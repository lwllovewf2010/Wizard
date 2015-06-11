package com.leepresswood.wizard.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.entities.attributes.Defense;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{	
	//XML Data
	public float knockback_damage;
	public float knockback_force;
	public float health;
	
	public boolean do_jump;
	
	private final float DIE_TIME_MAX = 1f;
	private float die_time_current;
	
	public Enemy(GameWorld world, float x, float y, Element data)
	{
		super(world, x, y, data);
		
		knockback_force = data.getFloat("knockback_force");
		knockback_damage = data.getFloat("knockback_damage");
		health = data.getFloat("health");
		
		System.out.println(
				"\tKnockback Force: " + knockback_force
				+ "\n\tKnockback Damage: " + knockback_damage
				+ "\n\tHealth: " + health
		);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	@Override
	protected void calcMovementX(float delta)
	{//General AI tells the enemies to move toward the center.
		if(sprite.getX() > world.screen.world.WORLD_TOTAL_HORIZONTAL / 2f)
			speed_current_x -= accel_x * delta;
		else if(sprite.getX() < world.screen.world.WORLD_TOTAL_HORIZONTAL / 2f - sprite.getWidth())
			speed_current_x += accel_x * delta;
		else
			speed_current_x = 0f;
		
		//Limit speed by max.
		if(Math.abs(speed_current_x) > speed_max_x)
			speed_current_x = speed_max_x * Math.signum(speed_current_x);
	}
	
	@Override
	protected void calcMovementY(float delta)
	{//Ground enemies are affected by gravity.
		//If we asked the enemy to jump, do a jump calculation.
		if(do_jump)
		{
			do_jump = false;
			speed_current_y += jump_start_speed;
		}
		
		//Do a fall calculation by simulating gravity.
		if(sprite.getY() > world.screen.world.GROUND)
			speed_current_y -= delta * world.screen.world.GRAVITY;
	}
	
	@Override
	protected void enemyCollision()
	{
		for(Spell s : world.entity_handler.spells)
		{
			//To make this horrible O(n^3) function faster, we're only going to check the spells that are within a certain radius.S
			if(25f > Vector2.dst2(s.bounds[0].x + s.bounds[0].width / 2f, s.bounds[0].y + s.bounds[0].height / 2f, bounds[0].x + bounds[0].width / 2f, bounds[0].y + bounds[0].height / 2f))
			{
				for(Rectangle r : s.bounds)
				{
					for(Rectangle r2 : this.bounds)
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
		}
	}
	
	@Override
	public void damage(float amount)
	{
		health -= Defense.damageAfterDefense(amount, defense);
	}
	
	@Override
	protected boolean getDeathStatus()
	{
		return health <= 0f;
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

		sprite.setAlpha(1f - die_time_current / DIE_TIME_MAX);
	}

	/**
	 * Enemy hit the passed player. Do any damage/effects.
	 * @param player The player that was hit.
	 */
	public abstract void doHit(Player player);

	/**
	 * The passed player bumped into this enemy. If there is an effect on the player's body (like a fire cloak), hit the enemy with it.
	 * @param player The player that was hit.
	 */
	public abstract void hitBy(Player player);
}
