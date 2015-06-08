package com.leepresswood.wizard.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.PersonEntity;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Parent to all the enemy types.
 */
public abstract class Enemy extends PersonEntity
{
	public String name;
	public Sprite sprite;
	
	public boolean do_jump;
	
	public Enemy(ScreenGame screen, float x, float y, Element data)
	{
		super(screen, x, y);
		
		name = data.get("name");
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	@Override
	protected void setMovementVariables()
	{
		//X
		accel_x = 5f;
		decel_x = 2f * accel_x;
		speed_max_x = 5f;
		
		//Y
		jump_start_speed = 10f;
		jump_time_max = 0.25f;
	}
	
	@Override
	protected void setSprites(ScreenGame screen, float x, float y)
	{
		sprite = new Sprite(screen.game.assets.get("person/textures/hold.png", Texture.class));
		sprite.setBounds(x, y, 10, 10);
	}
	
	@Override
	protected void calcMovementX(float delta)
	{//General AI tells the enemies to move toward the center.
		//Enemy wants to move toward the center of the map.
		if(sprite.getX() > screen.world.WORLD_TOTAL_HORIZONTAL / 2f)
			speed_current_x -= accel_x;
		else
			speed_current_x += accel_x;
		
		//Limit speed by max.
		if(Math.abs(speed_current_x) > speed_max_x)
			speed_current_x = speed_max_x * Math.signum(speed_current_x);
		
		sprite.translateX(speed_current_x * delta);
	}
	
	@Override
	protected void updateTiming(float delta)
	{
	}
	
	@Override
	public void die()
	{
	}
}
