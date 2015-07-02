package com.leepresswood.wizard.world.entities.living;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	protected Universe universe;
	
	//Sprite and texture data.
	private final String TEXTURE_BASE = "textures/";
	private final String TEXTURE_EXTENSION = ".png";
	
	//XML Data
	public String name;
	public Texture texture;
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
		super(universe);
		
		name = data.get("name");
		texture = universe.screen.game.assets.get(TEXTURE_BASE + data.get("texture") + TEXTURE_EXTENSION);
		health_max = data.getFloat("health");
		defense = data.getFloat("defense");
		speed_max_x = data.getFloat("speed");
		accel_x = data.getFloat("acceleration");
		decel_x = data.getFloat("deceleration");
		jump_start_speed = data.getFloat("jump_speed");
		
		health_current = health_max;
		
		//Create a body for all this entity's parts.
		setBodies(x, y, data.getFloat("width"), data.getFloat("height"));
	}
	
	/**
	 * Set sprites to their initial values.
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected abstract void setBodies(float x, float y, float width, float height);
	
	@Override
	public void update(float delta)
	{
		//Nothing else needs to be done if we're dead.
		if(!dying)
		{
			//Movement calculation.
			calcMovementX(delta);
			calcMovementY(delta);
			
			//Invincibility calculation.
			if(is_invincible)
			{
				invincible_time_current += delta;
				if(invincible_time_current >= invincible_time_max)
					is_invincible = false;
			}
			else
				enemyCollision();			
			//blockCollision();
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
	 * Collision with the game world.
	 */
	protected void blockCollision()
	{
		/*//Bottom
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null)
		{
			sprite.setY((int) (sprite.getY() + 1));
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
		
		//Top
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) (sprite.getY() + sprite.getHeight())) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setY((int) (sprite.getY()));
			speed_current_y = 0f;
		}
		
		//Left
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setX((int) (sprite.getX() + 1));
			speed_current_x = 0f;
		}
		
		//Right
		if(universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setX((int) (sprite.getX()));
			speed_current_x = 0f;
		}*/
	}
	
	/**
	 * Damage was taken.
	 * @param amount Amount of damage taken.
	 */
	public void damage(float amount)
	{//Damage was done, but defense must be taken into consideration.
		universe.screen.gui.bar_health.change(-DefenseCalculator.damageAfterDefense(amount, defense));
		health_current -= DefenseCalculator.damageAfterDefense(amount, defense);
	}
	
	/**
	 * Collision with enemies to this entity.
	 */
	public abstract void enemyCollision();
}
