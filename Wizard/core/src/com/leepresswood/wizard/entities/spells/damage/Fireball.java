package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
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
	private float impulse;
	
	public Fireball(ScreenGame screen, Vector2 from, Vector2 to, Element data)
	{
		super(screen, from, to, data);
		
		//Read the data from the XML file.
		impulse = data.getFloat("impulse");

		System.out.println(
			"\tImpulse: " + impulse
		);// + "\n\tType: " + type);
	}
	
	public Fireball(Texture t, float x, float y){super(t, x, y);}

	@Override
	protected void updatePosition(float delta)
	{
		//Change the direction of the ball.
		//X doesn't need to be changed, so only change Y.
		speed_y -= screen.world.GRAVITY * delta;
		
		//Move in the direction.
		sprite.translate(speed_x * delta, speed_y * delta);
	}
	
	@Override
	protected void updateCollision()
	{
		//Check floor for bounce.
		if(sprite.getY() < screen.world.GROUND)
		{			
			//Set Y to the ground level.
			sprite.setY(screen.world.GROUND);
			
			//Flip Y speed and impulse to shorten the bounce.
			speed_y *= -impulse;
		}
	}
}
