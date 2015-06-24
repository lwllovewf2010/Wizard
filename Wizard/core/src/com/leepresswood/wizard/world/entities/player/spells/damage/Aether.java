package com.leepresswood.wizard.world.entities.player.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
	
	public Aether(Texture t, float x, float y){super(t, x, y);}
	
	public Aether(GameWorld world, Vector2 from, Vector2 to, Element data, int level)
	{
		super(world, from, to, data, level);
		level = 2;
		//Aether will be split into multiple bolts. Each split should move and collide individually.
		int split_count = data.getChildrenByName("level").get(level).getInt("split");
		for(int i = 1; i < split_count; i++)
		{
			//The main aether bolt will go straight forward. Every other one will be offset by an angle.
			Vector2 new_to = new Vector2(from).sub(to);
			
			float rotate_by = 180f / (split_count + 1);
			System.out.println(new_to);
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
	 * Send this instance directly.
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
			
		}
		else
		{
			//Aether doesn't change direction. Simply move.
			sprite.translate(speed_x * delta, speed_y * delta);
		}
		
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
