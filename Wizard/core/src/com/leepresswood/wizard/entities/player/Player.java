/* Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 */
package com.leepresswood.wizard.entities.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class Player
{	
	protected ScreenGame screen;	
	
	//Direction and movement.
	public boolean facing_left = false;
	public boolean moving_left = false;
	public boolean moving_right = false;
	public float speed = 3f;
	public Vector2 direction;
	
	//Sprites and bounds.
	public Sprite sprite;
	
	public Player(ScreenGame screen, float x, float y)
	{
		this.screen = screen;
		sprite = new Sprite(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
		sprite.setBounds(x, y, 1, 2);
	}
	
	public void die()
	{
		
	}
	
	public void attack(Vector2 click_point)
	{
		
	}
	
	public void update(float delta)
	{
		if(moving_left)
			sprite.translateX(-delta * speed);
		if(moving_right)
			sprite.translateX(delta * speed);
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
