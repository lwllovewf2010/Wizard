package com.leepresswood.wizard.world.entities.bodyparts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A body part. Contains a sprite and a box2d body.
 * @author Lee
 */
public class BodyPart
{
	//private Animation animation;
	public Sprite sprite;
	public Body body;
	
	public BodyPart(Sprite sprite, Body body)
   {
		//animation = new Animation();
		
		this.sprite = sprite;
		this.body = body;
   }
	
	/*public void setAnimation(TextureRegion reg, float delay)
	{
		setAnimation(new TextureRegion[] { reg }, delay);
	}
	
	public void setAnimation(TextureRegion[] reg, float delay)
	{
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}*/

	/**
	 * Set sprite's position to the body's new position.
	 */
	public void update(float delta)
	{
		//animation.update(delta);
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
	}
	
	/**
	 * Draw the sprite.
	 */
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
