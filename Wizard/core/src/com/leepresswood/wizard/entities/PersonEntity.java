package com.leepresswood.wizard.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Parent class to both the players and the enemies. 
 */
public abstract class PersonEntity
{
	protected GameWorld world;
	
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
	public boolean facing_left;
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
	public Sprite sprite;
	public Rectangle[] bounds;
	
	public PersonEntity(GameWorld world, float x, float y, Element data)
	{
		this.world = world;
		
		name = data.get("name");
		texture = world.screen.game.assets.get(TEXTURE_BASE + data.get("texture") + TEXTURE_EXTENSION);
		defense = data.getFloat("defense");
		speed_max_x = data.getFloat("speed");
		accel_x = data.getFloat("acceleration");
		decel_x = data.getFloat("deceleration");
		jump_start_speed = data.getFloat("jump_speed");
		
		bounds = setSprites(texture, x, y, data.getFloat("width"), data.getFloat("height"));
		
		System.out.println(
				"PersonEntity:"
				+ "\n\tName: " + name
				+ "\n\tPosition: " + sprite.getX() + ", " + sprite.getY() 
				+ "\n\tWidth: " + sprite.getWidth() 
				+ "\n\tHeight: " + sprite.getHeight()
				+ "\n\tDefense: " + defense
				+ "\n\tMax Speed: " + speed_max_x
				+ "\n\tJump Speed: " + jump_start_speed
				+ "\n\tHorizontal Acceleration: " + accel_x
				+ "\n\tHorizontal Deceleration: " + decel_x
		);
	}
	
	/**
	 * Set sprites to their initial values.
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return Bounds of all the sprites.
	 */
	protected Rectangle[] setSprites(Texture texture, float x, float y, float width, float height)
	{
		sprite = new Sprite(texture);
		sprite.setBounds(x, y, width, height);
		
		return new Rectangle[]{sprite.getBoundingRectangle()};
	}
	
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
			blockCollision();
		}
		
		//Die in accordance with the type of enemy this is.
		if(dying || getDeathStatus())
		{
			dying = true;
			die(delta);
		}
	}
	
	/**
	 * Determine left-right movement.
	 */
	private void move(float delta)
	{
		if(is_invincible)
		{
			invincible_time_current += delta;
			if(invincible_time_current >= invincible_time_max)
			{
				is_invincible = false;
			}
		}
		if(is_being_knocked_back)
		{
			is_being_knocked_back = false;			
			speed_current_x += knockback_speed * MathUtils.cosDeg(knockback_angle);
			speed_current_y = knockback_speed * MathUtils.sinDeg(knockback_angle);
		}
		else
		{
			calcMovementX(delta);
			calcMovementY(delta);
		}
		
		sprite.translate(speed_current_x * delta, speed_current_y * delta);
		
		//Reset the bounds.
		bounds[0] = sprite.getBoundingRectangle();
	}
	
	/**
	 * Collision with the game world.
	 */
	protected void blockCollision()
	{
		//Bottom
		if(world.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null)
		{
			sprite.setY((int) (sprite.getY() + 1));
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
		
		//Top
		if(world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) (sprite.getY() + sprite.getHeight())) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setY((int) (sprite.getY()));
			speed_current_y = 0f;
		}
		
		//Left
		if(world.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setX((int) (sprite.getX() + 1));
			speed_current_x = 0f;
		}
		
		//Right
		if(world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			sprite.setX((int) (sprite.getX()));
			speed_current_x = 0f;
		}
	}
		
	/**
	 * Entity seeks to attack a targeted point in the world. Cast at or in the direction of that point.
	 * @param point The coordinate in the world that was clicked.
	 */
	public abstract void attack(Vector2 touch);
	
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
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public abstract void draw(SpriteBatch batch);
	
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
