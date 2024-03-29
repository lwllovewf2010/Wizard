package com.leepresswood.wizard.world.entities.living.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.Box2DSprite;
import com.leepresswood.wizard.helpers.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.LivingEntity;

/**
 * Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 * @author Lee
 */
public abstract class Player extends LivingEntity
{
	public float mana_max;
	public float mana_current;
	
	public Player(Universe universe, float x, float y, Element data)
	{
		super(universe, x, y, data);
		
		//Grab XMl items..		
		mana_max = data.getFloat("mana");;
		
		mana_current = mana_max;		
		on_ground = true;
	}
	
	@Override
	protected void setBodies(float x, float y, float width, float height)
	{
		//For now, we'll just have one body part.
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.PLAYER);
	}

	@Override
	public void update(float delta)
	{
		super.update(delta);
   
		//On top of the normal block collision, we want our player to be stuck within the bounds of the universe..
		if(parts[0].body.getTransform().getPosition().x - parts[0].body.getFixtureList().get(0).getShape().getRadius() < 0f)
		{
			force = 0f;
			parts[0].body.setTransform(parts[0].body.getFixtureList().get(0).getShape().getRadius(), parts[0].body.getTransform().getPosition().y, 0f);
		}
		else if(parts[0].body.getTransform().getPosition().x + parts[0].body.getFixtureList().get(0).getShape().getRadius() > universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL)
		{
			force = 0f;
			parts[0].body.setTransform(universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL - (parts[0].body.getFixtureList().get(0).getShape().getRadius()), parts[0].body.getTransform().getPosition().y, 0f);
		}
	}
	
	protected void calcMovement(float delta)
	{
		//X
		if(moving_left && !moving_right)
			force = -accel_x;
		else if(moving_right && !moving_left)
			force = accel_x;
		else
			force = 0f;
		
		//Y
		if(jumping)
		{//If the jumping variable is true, jump button is being held.
			//If we're on the ground, we can start a jump.
			if(on_ground)
			{
				on_ground = false;
				mid_jump = true;
				for(Box2DSprite p : parts)
					p.body.applyForceToCenter(0f, jump_start_speed, true);
			}
			//If not on the ground but mid-jump, apply another force and count.
			else if(mid_jump)
			{
				//Increase the timer before we apply another force.
				jump_time_current += delta;
				if(jump_time_current < JUMP_TIME_MAX)
				{
					for(Box2DSprite p : parts)
						p.body.applyForceToCenter(0f, jump_start_speed, true);
				}
			}
		}
		else
		{//We aren't holding the jump button.			
			if(mid_jump)
			{//If the length of time we held the jump button is less than the min, keep holding it virtually.
				jump_time_current += delta;
				if(jump_time_current < JUMP_TIME_MIN)
				{
					for(Box2DSprite p : parts)
						p.body.applyForceToCenter(0f, jump_start_speed, true);
				}
				else
				{
					mid_jump = false;
					jump_time_current = JUMP_TIME_MAX;
				}
			}
		}
		
		for(Box2DSprite p : parts)
			//Only apply more force if the current horizontal speed is less than the max speed or if the force is in the opposite direction of the movement.
			if(Math.pow(p.body.getLinearVelocity().x, 2f) < Math.pow(speed_max_x, 2f) || Math.signum(p.body.getLinearVelocity().x) != Math.signum(force))
				p.body.applyForceToCenter(force, 0f, true);
	}
	
	@Override
   public void doHit(GameEntity entity)
   {//Player colliding with an entity will not do anything.
   }
	
	@Override
	protected String getTextureBaseString()
	{
		return "players/";
	}
	
	@Override
	public void die(float delta)
	{
		active = false;
	}
}
