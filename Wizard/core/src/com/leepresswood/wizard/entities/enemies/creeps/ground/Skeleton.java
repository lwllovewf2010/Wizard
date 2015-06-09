package com.leepresswood.wizard.entities.enemies.creeps.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.screens.game.GameWorld;

public class Skeleton extends GroundEnemy
{
	public Skeleton(GameWorld world, float x, float y, Element element)
	{
		super(world, x, y, element);
	}
	
	@Override
	protected void setSprites(float x, float y)
	{
		sprite = new Sprite(world.screen.game.assets.get("person/textures/hold.png", Texture.class));
		sprite.setBounds(x, y, 3f, 3f);
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
	public void attack(Vector2 touch)
	{
	}
}
