package com.leepresswood.wizard.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{
	public String name;
	
	public boolean do_jump;
	
	private final float DIE_TIME_MAX = 1f;
	private float die_time_current;
	
	public float knockback_force;
	public float knockback_damage;
	
	public Enemy(GameWorld world, float x, float y, Element data)
	{
		super(world, x, y);
			
		name = data.get("name");
		knockback_force = data.getFloat("knockback_force");
		knockback_damage = data.getFloat("knockback_damage");
		accel_x = data.getFloat("knockback_force");
		decel_x = data.getFloat("knockback_force");
		speed_max_x = data.getFloat("knockback_force");
		jump_start_speed = data.getFloat("knockback_force");
		
		System.out.println(
				"Enemy:"
				+ "\n\tName: " + name
				+ "\n\tPosition: " + sprite.getX() + ", " + sprite.getY() 
				+ "\n\tWidth: " + sprite.getWidth() 
				+ "\n\tHeight: " + sprite.getHeight()
				+ "\n\tMax Speed: " + speed_max_x
				+ "\n\tJump Speed: " + jump_start_speed
				+ "\n\tHorizontal Acceleration" + accel_x
				+ "\n\tHorizontal Deceleration" + decel_x
				+ "\n\tKnockback Force: " + knockback_force
				+ "\n\tKnockback Damage: " + knockback_damage
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
		for(Spell s : world.spells)
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
							s.hit(this);							
							
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
	public void die(float delta)
	{
		if(dying)
		{
			//Update the timing and change the alpha.
			die_time_current += delta;
			if(die_time_current >= DIE_TIME_MAX)
			{
				dying = false;
				is_dead = true;
				die_time_current = DIE_TIME_MAX;
			}

			sprite.setAlpha(die_time_current / DIE_TIME_MAX);
		}
	}

	/**
	 * Enemy hit the passed player. Do any damage/effects.
	 * @param player The player that was hit.
	 */
	public abstract void hit(Player player);
}
