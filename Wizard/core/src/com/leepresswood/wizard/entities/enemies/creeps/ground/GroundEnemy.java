package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * An enemy type that is affected by gravity.
 * @author Lee
 *
 */
public abstract class GroundEnemy extends Enemy
{
	public GroundEnemy(ScreenGame screen, float x, float y, Element element)
	{
		super(screen, x, y, element);
	}

	@Override
	protected void calcMovementY(float delta)
	{//Ground enemies are affected by gravity.
		//If we asked the enemy to jump, do a jump calculation.
		if(do_jump)
		{
			do_jump = false;
			speed_current_y += jump_start_speed;
		}
		
		//Do a fall calculation by simulating gravity.
		if(sprite.getY() > screen.world.GROUND)
			speed_current_y -= delta * screen.world.GRAVITY;
		
		//Move in the Y direction.
		sprite.translateY(speed_current_y * delta);
		
		//Set a hard limit for how low the entity can go. If they pass this limit, they're on a solid block. Reset the variables.
		if(sprite.getY() < screen.world.GROUND)
		{
			sprite.setY(screen.world.GROUND);
			speed_current_y = 0f;
			jump_time_current = 0f;
			
			if(jumping)
				jump_stop_hop = true;
		}
	}
}
