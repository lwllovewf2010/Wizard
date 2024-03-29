package com.leepresswood.wizard.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.leepresswood.wizard.helpers.datapackage.B2DSPackage;
import com.leepresswood.wizard.world.entities.GameEntity;

/**
 * Contains both a sprite and a box2d body.
 * @author Lee
 */
public class Box2DSprite
{
	//private Animation animation;
	public Sprite sprite;
	public Body body;
	
	public Box2DSprite(Sprite sprite, Body body, GameEntity e, byte contact)
   {
		//animation = new Animation();
		this.sprite = sprite;
		
		this.body = body;
		this.body.setUserData(new B2DSPackage(e, contact));
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
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2f);
	}
	
	/**
	 * Draw the sprite.
	 */
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
