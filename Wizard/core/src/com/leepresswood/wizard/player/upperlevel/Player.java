/* Class should include all attributes every player will have.
 * This includes health, power, defense, speed, jump height, etc.
 * Body parts included in extended classes to allow different shapes of classes.
 */
package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Player
{	
	protected ScreenGame screen;	

	public boolean moving_left = false;
	public boolean moving_right = false;
	
	public Player(ScreenGame screen)
	{
		this.screen = screen;
	}
	
	public void die()
	{
		
	}
	
	public abstract void attack(Vector2 click_point);
	public abstract void update(float delta);
	public abstract void draw(SpriteBatch batch);
}