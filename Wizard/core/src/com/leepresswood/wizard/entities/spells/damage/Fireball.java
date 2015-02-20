package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.screens.game.ScreenGame;

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
	protected final float IMPULSE = 0.7f;
	private final int MAX_BOUNCES = 5;
	private int bounces;
	
	public Fireball(ScreenGame screen, Vector2 from, Vector2 to)
	{
		super(screen, from, to);
		
		NAME = "Fireball";
		System.out.println("\tImpulse: " + IMPULSE + "\n\tMax Bounces: " + MAX_BOUNCES + "\n\tType: " + NAME);
	}
	
	@Override
	protected Texture makeSpriteTexture()
	{
		return screen.game.assets.getTexture(Assets.TEXTURE_HOLD);
	}
	
	@Override
	protected float setSpeedMax()
	{
		return 13f;
	}
	
	@Override
	protected void updatePosition(float delta)
	{
		//Change the direction of the ball.
		//X doesn't need to be changed, so only change Y.
		speed_y -= screen.world_game.GRAVITY * delta;
		
		//Move in the direction.
		sprite.translate(speed_x * delta, speed_y * delta);
	}
	
	@Override
	protected void updateCollision()
	{
		//Check floor for bounce.
		if(sprite.getY() < screen.world_game.GROUND)
		{
			bounces++;
			
			//Set Y to the ground level.
			sprite.setY(screen.world_game.GROUND);
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -IMPULSE;
		}
		
		//Stop spell if bouncing has reached its max.
		if(bounces == MAX_BOUNCES)
			active = false;
	}
}
