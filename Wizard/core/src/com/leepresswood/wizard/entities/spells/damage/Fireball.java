package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * Bolt spell that is affected by gravity. Explodes on impact.<br/>
 * Type: Fire<br/>
 * Damage: High<br/>
 * Speed: Medium</br/>
 * Cost: Medium<br/>
 * Recast Time: High<br/>
 * @author Lee
 */
public class Fireball extends BoltSpell
{	
	private float impulse;
	private float speed_decay;
	
	public Fireball(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		super(world, from, to, data);
		
		//Read the data from the XML file.
		impulse = data.getFloat("impulse");
		speed_decay = data.getFloat("speed_decay");
		
		System.out.println(
			"\tImpulse: " + impulse
			+ "\n\tSpeed Decay: " + speed_decay
		);
	}
	
	public Fireball(Texture t, float x, float y){super(t, x, y);}

	@Override
	protected void updatePosition(float delta)
	{
		//Change the direction of the ball.
		//X doesn't need to be changed, so only change Y.
		speed_y -= world.GRAVITY * delta;
		
		//Move in the direction.
		sprite.translate(speed_x * delta, speed_y * delta);
		
		//Reset the bounds.
		bounds[0] = sprite.getBoundingRectangle();
	}
	
	@Override
	protected void updateCollision()
	{
		//Check blocks for bounce.
		//Bottom
		if(world.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null)
		{			
			//Set Y to the block level.
			sprite.setY((int) (sprite.getY() + 1));
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -impulse;
			
			//Decay the X speed by the speed decay.
			speed_x *= speed_decay;
		}
		
		//Top
		if(world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) (sprite.getY() + sprite.getHeight())) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			//Set Y to the block level.
			sprite.setY((int) (sprite.getY()));
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -impulse;
			
			//Decay the X speed by the speed decay.
			speed_x *= speed_decay;
		}
		
		//Left
		if(world.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || world.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			//Set X to the block level.
			sprite.setX((int) (sprite.getX() + 1));
			
			//Flip X speed and impulse to shorten the bounce.
			speed_x *= -impulse;
			
			//Decay the Y speed by the speed decay.
			speed_y *= speed_decay;
		}
		
		//Right
		if(world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || world.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			//Set X to the block level.
			sprite.setX((int) (sprite.getX()));
			
			//Flip X speed and impulse to shorten the bounce.
			speed_x *= -impulse;
			
			//Decay the Y speed by the speed decay.
			speed_y *= speed_decay;
		}
	}

	@Override
	public void doHit(Enemy enemy)
	{
		//Remove life.
		enemy.damage(damage);
		
		//Destroy this bolt.
		active = false;
	}
}
