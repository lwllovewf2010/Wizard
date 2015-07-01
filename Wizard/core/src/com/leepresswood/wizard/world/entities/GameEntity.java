package com.leepresswood.wizard.world.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.bodyparts.BodyPart;

/**
 * Parent class to both the players and the enemies. 
 */
public abstract class GameEntity
{
	protected Universe universe;
	
	//Sprite and texture data.
	private final String TEXTURE_BASE = "textures/";
	private final String TEXTURE_EXTENSION = ".png";
	
	//XML Data
	public String name;
	public Texture texture;
	public float health;
	public float mana;
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
	public boolean is_being_knocked_back;
	public float invincible_time_max = 0.5f;
	public float invincible_time_current;
	public float knockback_angle;
	
	//Dying.
	public boolean is_dead;
	public boolean dying;
	
	//Jumping.
	public float jump_time_current;
	public boolean jumping;
	public float speed_current_y;
	public boolean jump_stop_hop;
	
	//Sprites and bounds.
	public BodyPart[] body_parts;
	
	public GameEntity(Universe universe, float x, float y, Element data)
	{
		this.universe = universe;
		
		name = data.get("name");
		texture = universe.screen.game.assets.get(TEXTURE_BASE + data.get("texture") + TEXTURE_EXTENSION);
		defense = data.getFloat("defense");
		speed_max_x = data.getFloat("speed");
		accel_x = data.getFloat("acceleration");
		decel_x = data.getFloat("deceleration");
		jump_start_speed = data.getFloat("jump_speed");
		
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
	
	/**
	 * Update timing and movement of sprites.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		//Nothing else needs to be done if we're dead.
		if(!dying)
		{
			move(delta);
			if(!is_invincible)
				enemyCollision();			
			//blockCollision();
		}
		
		//Die in accordance with the type of enemy this is.
		if(dying || getDeathStatus())
		{
			dying = true;
			die(delta);
		}
		
		for(BodyPart p : body_parts)
			p.update(delta);
	}
	
	/**
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public void draw(SpriteBatch batch)
	{//We will assume the body parts are ordered correctly while drawing.
		for(BodyPart p : body_parts)
			p.draw(batch);
	}
	
	/**
	 * Determine left-right movement.
	 */
	private void move(float delta)
	{
		//Invincibility calculation.
		if(is_invincible)
		{
			invincible_time_current += delta;
			if(invincible_time_current >= invincible_time_max)
			{
				is_invincible = false;
			}
		}
		
		//Movement calculation.
		calcMovementX(delta);
		calcMovementY(delta);
		
		//sprite.translate(speed_current_x * delta, speed_current_y * delta);
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
	 * Damage was taken. Do the correct action.
	 * @param amount Amount of damage taken.
	 */
	public abstract void damage(float amount);
	
	/**
	 * Collision with enemies to this entity.
	 */
	protected abstract void enemyCollision();
	
	/**
	 * Calculate movement in the X direction.
	 * @param delta Change in time.
	 */
	protected abstract void calcMovementX(float delta);
	
	/**
	 * Calculate movement in the Y direction.
	 * @param delta Change in time.
	 */
	protected abstract void calcMovementY(float delta);
	
	/**
	 * Is this entity dead?
	 * @return True if dead. False otherwise.
	 */
	protected abstract boolean getDeathStatus();
	
	/**
	 * Send entity into death animation. Also handle what happens afterward within this.
	 */
	protected abstract void die(float delta);
}
