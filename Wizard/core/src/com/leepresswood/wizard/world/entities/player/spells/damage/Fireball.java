package com.leepresswood.wizard.world.entities.player.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.spells.BoltSpell;

/**
 * Bolt spell that is affected by gravity. Explodes on impact.
 * @author Lee
 */
public class Fireball extends BoltSpell
{	
	private float impulse;
	private float speed_decay;
	private Element explosion_data;
	
	public Fireball(Texture t, float x, float y){super(t, x, y);}
	
	public Fireball(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, data, level);
		
		//Read the data from the XML file.
		impulse = data.getFloat("impulse");
		speed_decay = data.getFloat("speed_decay");
		explosion_data = data.getChildrenByName("level").get(level).getChildByName("explosion");
		
		System.out.println(
			"\tImpulse: " + impulse
			+ "\n\tSpeed Decay: " + speed_decay
		);
	}

	@Override
	protected void updatePosition(float delta)
	{
		//Change the direction of the ball.
		//X doesn't need to be changed, so only change Y.
		//speed_y -= universe.map_camera_handler.GRAVITY * delta;
		
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
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null)
		{			
			//Set Y to the block level.
			sprite.setY((int) (sprite.getY() + 1));
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -impulse;
			
			//Decay the X speed by the speed decay.
			speed_x *= speed_decay;
		}
		
		//Top
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth() / 2f), (int) (sprite.getY() + sprite.getHeight())) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			//Set Y to the block level.
			sprite.setY((int) (sprite.getY()));
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -impulse;
			
			//Decay the X speed by the speed decay.
			speed_x *= speed_decay;
		}
		
		//Left
		if(universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || universe.map_camera_handler.collision_layer.getCell((int) sprite.getX(), (int) (sprite.getY() + sprite.getHeight())) != null)
		{
			//Set X to the block level.
			sprite.setX((int) (sprite.getX() + 1));
			
			//Flip X speed and impulse to shorten the bounce.
			speed_x *= -impulse;
			
			//Decay the Y speed by the speed decay.
			speed_y *= speed_decay;
		}
		
		//Right
		if(universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) sprite.getY()) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight() / 2f)) != null || universe.map_camera_handler.collision_layer.getCell((int) (sprite.getX() + sprite.getWidth()), (int) (sprite.getY() + sprite.getHeight())) != null)
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
		
		//The collision death of this bolt will create an explosion.
		Vector2 location = new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f);
		universe.entity_handler.spell_queue.add(new Explosion(universe, location, location, explosion_data, level));
	}
}
