package com.leepresswood.wizard.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{
	public String name;
	public Sprite sprite;
	
	public boolean do_jump;
	
	//Dying.
	public boolean is_dead;
	private boolean dying;
	private final float DIE_TIME_MAX = 1f;
	private float die_time_current;
	
	public Enemy(GameWorld world, float x, float y, Element data)
	{
		super(world, x, y);
			
		name = data.get("name");
		
		System.out.println("Enemy:\n\tName: " + name);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	@Override
	protected void move(float delta)
	{
		
		
		
		sprite.translate(speed_current_x * delta, speed_current_y * delta);
	}

	@Override
	protected void calcMovementX(float delta)
	{//General AI tells the enemies to move toward the center.
		if(sprite.getX() > world.screen.world.WORLD_TOTAL_HORIZONTAL / 2f)
			speed_current_x -= accel_x;
		else if(sprite.getX() < world.screen.world.WORLD_TOTAL_HORIZONTAL / 2f - sprite.getWidth())
			speed_current_x += accel_x;
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
		
		//Move in the Y direction.
		
	
		
	}
	
	@Override
	protected void enemyCollision()
	{
		if(!is_invincible)
		{
			for(Spell s : world.spells)
			{
				//Get the angle between the enemy and the attack. The angle of the knockback will be the flipped version of this angle.
				knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(s.sprite.getY() + s.sprite.getHeight() / 2f - sprite.getY() - sprite.getHeight() / 2f, s.sprite.getX() + s.sprite.getWidth() / 2f - sprite.getX() - sprite.getWidth() / 2f);
				knockback_angle += 180f;
				
				//Get damage.
				
				
				//Set the knockback and invincibility.
				is_being_knocked_back = true;
				is_invincible = true;
				invincible_time_current = 0f;
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
}
