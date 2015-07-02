package com.leepresswood.wizard.world.entities.living.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.calculators.DefenseCalculator;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.LivingEntity;

/**
 * Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 * @author Lee
 *
 */
public abstract class Player extends LivingEntity
{	
	public Player(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		//Grab the health and mana.
		health_max = data.getFloat("health");
		mana = data.getFloat("mana");
		
		on_ground = true;
	}
	
	@Override
	protected void setBodies(float x, float y, float width, float height)
	{
		//For now, we'll just have one body part.
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this);
	}
	
	@Override
	protected void blockCollision()
	{
		super.blockCollision();
		
		/*//On top of the normal block collision, we want our player to be stuck within the bounds of the stage.
		if(sprite.getX() < 0f)
		{
			sprite.setX(0f);
			speed_current_x = 0f;
		}
		else if(sprite.getX() + sprite.getWidth() > universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL)
		{
			sprite.setX(universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL - sprite.getWidth());
			speed_current_x = 0f;
		}*/
	}

	protected void calcMovementX(float delta)
	{
		//Deceleration check. Decelerate if not moving, if both left and right are pressed at the same time, or if moving in one direction but pressing another.
		/* if(!moving_right && !moving_left || moving_right && moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))
		 * Note: Simplifying the boolean math. ((A and B) or (!A and !B)) is (A == B)
		 */		
		if(moving_right == moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))	
		{
			//Move command is no longer pressed. Decay to 0 speed over time.
			if(speed_current_x < 0f)
				speed_current_x += decel_x * delta;
			else if(speed_current_x > 0f)
				speed_current_x -= decel_x * delta;
			
			//Dampen the speed for lower values.
			if(Math.abs(speed_current_x) < 0.1f)
				speed_current_x = 0f;
		}
		
		//Acceleration check.
		if(moving_left && !moving_right)
		{
			speed_current_x -= accel_x * delta;
			if(speed_current_x < -speed_max_x)
				speed_current_x = -speed_max_x;
		}
		else if(moving_right && !moving_left)
		{
			speed_current_x += accel_x * delta;
			if(speed_current_x > speed_max_x)
				speed_current_x = speed_max_x;
		}
		
		for(Box2DSprite p : parts)
			p.body.setLinearVelocity(speed_current_x, p.body.getLinearVelocity().y);
	}
	
	protected void calcMovementY(float delta)
	{
		//If the jumping variable is true, jump button is being held.
		if(jumping && on_ground)
		{
			//Stop further jumping until we're on the ground.
			on_ground = false;
			for(Box2DSprite p : parts)
				p.body.applyForceToCenter(0f, jump_start_speed * universe.map_camera_handler.pixel_size * universe.map_camera_handler.GRAVITY, true);			//Translating jump speed into newtons for box2d.
		}
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//Player colliding with an entity will not do anything.
   }
	
	@Override
	public void enemyCollision()
	{
		/*for(Enemy e : universe.entity_handler.enemies)
		{
			//To make this horrible O(n^3) function faster, we're only going to check the enemies that are alive and within a certain radius.
			if(e.dying == false && 25f > Vector2.dst2(e.bodies[0].x + e.bodies[0].width / 2f, e.bodies[0].y + e.bodies[0].height / 2f, bodies[0].x + bodies[0].width / 2f, bodies[0].y + bodies[0].height / 2f))
			{
				for(Rectangle r : e.bodies)
				{
					for(Rectangle r2 : this.bodies)
					{
						if(r2.overlaps(r))
						{
							//Get the angle between the enemy and the attack. The angle of the knockback will be the flipped version of this angle.
							knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(r2.y + r.height / 2f - sprite.getY() - sprite.getHeight() / 2f, r.x + r.width / 2f - sprite.getX() - sprite.getWidth() / 2f);
							knockback_angle += 180f;
							
							//Get damage/effects.
							e.doHit(this);
							e.hitBy(this);
							
							//Set the knockback and invincibility.
							knockback_speed = e.knockback_force;
							is_being_knocked_back = true;
							is_invincible = true;
							invincible_time_current = 0f;
						}
					}
				}
			}
		}*/
	}

	@Override
	public boolean getDeathStatus()
	{
		return universe.screen.gui.bar_health.current_bar_value <= 0f;
	}
	
	@Override
	public void die(float delta)
	{
	}
}
