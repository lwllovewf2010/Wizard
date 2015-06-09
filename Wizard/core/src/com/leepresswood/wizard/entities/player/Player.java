package com.leepresswood.wizard.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 * @author Lee
 *
 */
public class Player extends PersonEntity
{	
	private final float WIDTH = 3f;
	private final float HEIGHT = WIDTH * 1.618f;
	
	public Player(GameWorld world, float x, float y)
	{
		super(world, x, y);
		
		//Display player information.
		System.out.println(
			"Player:\n\tPosition: " + sprite.getX() + ", " + sprite.getY() 
			+ "\n\tWidth: " + sprite.getWidth() 
			+ "\n\tHeight: " + sprite.getHeight()
		);
	}
	
	public void attack(Vector2 touch)
	{
		//Get the selected spell type from the GUI.
		Spell spell_type = world.screen.gui.getActiveSpell();
		
		//Get the selected spell's mana cost and compare it to the player's current mana. See if it's possible to cast.
		if(spell_type != null && world.screen.gui.canCast(spell_type))
		{
			//Get the spell from the factory. Two vectors represent the player's center and the click location, respectively.
			Spell spell = world.screen.world.factory_spell.getSpell(spell_type.getClass(), new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f), new Vector2(touch.x, touch.y));
			
			//If this spell is null, we weren't able to instantiate it due to recharge timing not being correct or an active spell not being chosen in the GUI.
			if(spell != null)
			{
				//Create the selected spell.
				world.screen.gui.cast(spell);
				world.screen.world.spells.add(spell);
			}
		}
	}

	protected void calcMovementX(float delta)
	{
		//Deceleration check. Decelerate if not moving, if both left and right are pressed at the same time, or if moving in one direction but pressing another.
		/* if(!moving_right && !moving_left || moving_right && moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))
		 * Note: Simplifying the boolean math. ((A and B) or (!A and !B)) is (A == B)
		 */		
		if(moving_right == moving_left || (moving_left && speed_current_x > 0 || moving_right && speed_current_x < 0))	
		{
			//Move command is no longer pressed. Move to 0 speed over time.
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
		
		//Move in the X direction.
		sprite.translateX(delta * speed_current_x);
		
		//Keep the player within the bounds of the screen in the X direction.
		if(sprite.getX() < 0f)
			sprite.setX(0f);
		else if(sprite.getX() + sprite.getWidth() > world.screen.world.WORLD_TOTAL_HORIZONTAL)
			sprite.setX(world.screen.world.WORLD_TOTAL_HORIZONTAL - sprite.getWidth());
	}
	
	protected void calcMovementY(float delta)
	{
		//Don't allow multiple hops with the same spacebar press.
		if(!jump_stop_hop)
		{
			//If the jumping variable is true, jump button is being held. This allows the jump button to be held longer to jump higher. There must be a max jump button time, though.
			if(jumping)
			{
				if(jump_time_current < jump_time_max)
				{
					speed_current_y = jump_start_speed;
					jump_time_current += delta;
				}
			}
			else
			{
				//Block the player from jumping multiple times within the same jump.
				if(jump_time_current > 0f)
				{
					jump_time_current = jump_time_max;
				}
			}
		}
		else
		{//Allow the player to jump again if they have released the jump button at least once since the flag was set.
			if(!jumping)
			{
				jump_stop_hop = false;
			}
		}
		
		//Do a fall calculation by simulating gravity.
		if(sprite.getY() > world.screen.world.GROUND)
			speed_current_y -= delta * world.screen.world.GRAVITY;

		//Move in the Y direction.
		sprite.translateY(speed_current_y * delta);
		
		//Set a hard limit for how low the player can go. If they pass this limit, they're on a solid block. Reset the variables.
		if(sprite.getY() < world.screen.world.GROUND)
		{
			sprite.setY(world.screen.world.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	@Override
	protected void setMovementVariables()
	{
		//X
		accel_x = 5f;
		decel_x = 2f * accel_x;
		speed_max_x = 5f;
		
		//Y
		jump_start_speed = 10f;
		jump_time_max = 0.25f;
	}

	@Override
	protected void setSprites(float x, float y)
	{
		sprite = new Sprite(world.screen.game.assets.get("person/textures/hold.png", Texture.class));
		sprite.setBounds(x, y, WIDTH, HEIGHT);
	}

	@Override
	protected void enemyCollision()
	{
		if(!is_invincible)
		{
			for(Enemy e : world.enemies)
			{
				if(sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle()))
				{
					//Get the angle between the enemy and the attack. The angle of the knockback will be the flipped version of this angle.
					knockback_angle = MathUtils.radiansToDegrees * MathUtils.atan2(e.sprite.getY() + e.sprite.getHeight() / 2f - sprite.getY() - sprite.getHeight() / 2f, e.sprite.getX() + e.sprite.getWidth() / 2f - sprite.getX() - sprite.getWidth() / 2f);
					knockback_angle += 180f;
					
					//Get damage.
					
					
					//Set the knockback and invincibility.
					is_being_knocked_back = true;
					is_invincible = true;
					invincible_time_current = 0f;
				}
			}	
		}
	}

	@Override
	protected void blockCollision()
	{
	}

	@Override
	public void die(float delta)
	{
	}
}
