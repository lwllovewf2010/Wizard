package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.ScreenGame;


public class Fireball extends Spell
{
	private float speed_x, speed_y;
	private final float SPEED_MAX = 15f;
	private final float IMPULSE = 0.8f;
	
	private final int MAX_BOUNCES = 6;
	private int bounces;
	
	public Fireball(ScreenGame screen, Vector2 from, Vector2 to)
	{
		super(screen, from, to);
		
		//Determine the initial speeds.
		float angle = to.sub(from).angle();
		speed_x = SPEED_MAX * MathUtils.cosDeg(angle);
		speed_y = SPEED_MAX * MathUtils.sinDeg(angle);
	}
	
	@Override
	protected void makeSprite(Vector2 start)
	{
		float width = 1;
		float height = width;
		
		sprite = new Sprite(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
		sprite.setBounds(from.x - width / 2f, from.y - height / 2f, width, height);
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
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}	
}
