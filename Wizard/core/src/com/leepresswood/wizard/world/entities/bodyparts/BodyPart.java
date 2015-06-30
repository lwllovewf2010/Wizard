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
	public Sprite sprite;
	public Body body;
	
	public BodyPart(Sprite sprite, Body body)
   {
		this.sprite = sprite;
		this.body = body;
   }

	/**
	 * Set sprite's position to the body's new position.
	 */
	public void update()
	{
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
