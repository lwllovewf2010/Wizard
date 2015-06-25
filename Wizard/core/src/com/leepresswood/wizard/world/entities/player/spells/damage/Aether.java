package com.leepresswood.wizard.world.entities.player.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.spells.BoltSpell;

/**
 * Bolt spell that is not affected by gravity. Goes through walls and floors. No knockback. Hits enemies once.<br/>
 * Type: Void<br/>
 * Damage: Low<br/>
 * Speed: Low</br/>
 * Cost: Medium<br/>
 * Recast Time: Low<br/>
 * @author Lee
 */
public class Aether extends BoltSpell
{
	public boolean follow;
	private final float RECALCULATE_MAX_DISTANCE = 225f;
	private final float RECALCULATE_TIME = 0.01f;
	private final float RECALCULATE_MAX_ANGLE_DIFFERENCE = 2f;
	private float recalculate_current;
	
	//For multiple bolts.
	private final float ROTATE_SPLIT_ANGLE = 180f;
	
	public Aether(Texture t, float x, float y){super(t, x, y);}
	
	public Aether(GameWorld world, Vector2 from, Vector2 to, Element data, int level)
	{
		super(world, from, to, data, level);
		
		//Aether will be split into multiple bolts. Each split should move and collide individually.
		int split_count = data.getChildrenByName("level").get(level).getInt("split");
		float rotate_by = ROTATE_SPLIT_ANGLE / (split_count + 1);
		for(int i = 1; i < split_count; i++)
		{
			//The main aether bolt will go straight forward. Every other one will be offset by an angle.
			Vector2 new_to = new Vector2(to).sub(from);
			
			/* Half the bolts go in one direction, and half go the other way. Because we will always have an odd number, there
			 * will always be one that is the center bolt. That's the one this instance of the class refers to. The others
			 * will be provided by this class free of charge to the player.
			*/
			if(i <= split_count / 2)
				new_to.rotate(-i * rotate_by);
			else
				new_to.rotate((i - split_count / 2) * rotate_by);
			//System.out.println(new_to.angle());
			world.entity_handler.spells.add(new Aether(world, from, new Vector2(from).add(new_to), data, level, i));
		}
	}
	
	/**
	 * This is a private version of the spell that can be used to create splits.
	 * Send this instance directly into the spell list.
	 */
	private Aether(GameWorld world, Vector2 from, Vector2 to, Element data, int level, int count)
	{
		super(world, from, to, data, level);
	}
	
	@Override
	protected void updatePosition(float delta)
	{
		//Determine how aether will move.
		if(follow)
		{
			recalculate_current += delta;
			if(recalculate_current >= RECALCULATE_TIME)
			{
				recalculate_current -= RECALCULATE_TIME;
			
				//Find the nearest enemy.
				float enemy_distance_min = RECALCULATE_MAX_DISTANCE;
				Enemy enemy_index = null;
				
				for(Enemy e : world.entity_handler.enemies)
				{	
					float length = new Vector2(sprite.getX() + sprite.getWidth() / 2f - (e.sprite.getX() + e.sprite.getWidth() / 2f), sprite.getY() + sprite.getHeight() / 2f - (e.sprite.getY() + e.sprite.getHeight() / 2f)).len2();
					if(length < enemy_distance_min)
					{
						enemy_distance_min = length;
						enemy_index = e;
					}
				}
				
				//Change direction toward that enemy if it exists.
				if(enemy_index != null)
				{
					//float current_angle = MathUtils.atan2(speed_y, speed_x);
					float between_angle = new Vector2(enemy_index.sprite.getX() + enemy_index.sprite.getWidth() / 2f, enemy_index.sprite.getY() + enemy_index.sprite.getHeight() / 2f).sub(new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f)).angle();
					float d_angle =  between_angle - angle;
					if(d_angle > 180f)
						d_angle -= 360f;
					else if(d_angle < -180f)
						d_angle += 360f;
					if(Math.abs(d_angle) > RECALCULATE_MAX_ANGLE_DIFFERENCE)
						d_angle = Math.signum(d_angle) * RECALCULATE_MAX_ANGLE_DIFFERENCE;

					speed_x = speed_max * MathUtils.cosDeg(angle + d_angle);
					speed_y = speed_max * MathUtils.sinDeg(angle + d_angle);
					angle += d_angle;
				}
			}
		}
			
		sprite.translate(speed_x * delta, speed_y * delta);
		
		//Reset the bounds.
		bounds[0] = sprite.getBoundingRectangle();
	}

	@Override
	protected void updateCollision()
	{//Aether can hit multiple targets and go through walls, so no real collision is necessary. Time will make it disappear.
	}

	@Override
	public void doHit(Enemy enemy)
	{
		enemy.health -= damage;
	}
}
