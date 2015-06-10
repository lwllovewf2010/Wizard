package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.screens.game.GameWorld;

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
	public Aether(Texture t, float x, float y){super(t, x, y);}
	
	public Aether(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		super(world, from, to, data);
		time_alive_max = 5f;
		
		//System.out.println("\tType: " + name);
	}
	
	@Override
	protected void updatePosition(float delta)
	{
		//Aether doesn't change direction. Simply move.
		sprite.translate(speed_x * delta, speed_y * delta);
	}

	@Override
	protected void updateCollision()
	{//Aether can hit multiple targets and go through walls, so no real collision is necessary. Time will make it disappear.
	}

	@Override
	public void hit(Enemy enemy)
	{
		//Nothing needs to happen.
	}
}
