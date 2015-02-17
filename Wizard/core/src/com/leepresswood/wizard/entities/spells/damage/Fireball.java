package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Bolt spell that is affected by gravity.<br/>
 * Type: Fire<br/>
 * Damage: High<br/>
 * Cost: Medium<br/>
 * Recharge Time: High<br/>
 * @author Lee
 *
 */
public class Fireball extends BoltSpell
{	
	protected final float IMPULSE = 0.8f;
	private final int MAX_BOUNCES = 6;
	private int bounces;
	
	public Fireball(ScreenGame screen, Vector2 from, Vector2 to)
	{
		super(screen, from, to);
		
		System.out.println("\tType: Fireball\n\tImpulse: " + IMPULSE + "\n\tMax Bounces:" + MAX_BOUNCES);
	}
	
	@Override
	protected void makeSpriteTexture()
	{
		sprite.setTexture(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
	}
	
	@Override
	protected float setSpeedMax()
	{
		return 15f;
	}
	
	@Override
	protected void updatePosition(float delta)
	{
		//Change the direction of the ball.
		//X doesn't need to be changed, so only change Y.
		speed_y -= screen.GRAVITY * delta;
		
		//Move in the direction.
		sprite.translate(speed_x * delta, speed_y * delta);
	}
	
	@Override
	protected void updateCollision()
	{
		//Check floor for bounce.
		if(sprite.getY() < screen.GROUND)
		{
			bounces++;
			
			//Set Y to the ground level.
			sprite.setY(screen.GROUND);
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -IMPULSE;
		}
		
		//Stop spell if bouncing has reached its max.
		if(bounces == MAX_BOUNCES)
			active = false;
	}
}
