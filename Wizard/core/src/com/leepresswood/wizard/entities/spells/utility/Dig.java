package com.leepresswood.wizard.entities.spells.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.screens.game.GameWorld;

public class Dig extends Spell
{
	private int dig_width;
	private int dig_height;
	
	public Dig(Texture t, float x, float y){super(t, x, y);}
	
	public Dig(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		super(world, from, to, data);
		
		dig_width = data.getInt("dig_width");
		dig_height = data.getInt("dig_height");
	}

	@Override
	protected Rectangle[] makeSprites()
	{
		sprite.setBounds(0, 0, 0, 0);
		
		return new Rectangle[]{sprite.getBoundingRectangle()};
	}

	@Override
	protected void updatePosition(float delta)
	{//Dig doesn't move.
	}

	@Override
	protected void updateCollision()
	{//At this point, remove the X blocks and kill the spell.
		for(int j = 0; j < dig_height; j++)
			for(int i = 0; i < dig_width; i++)
				world.map_camera_handler.collision_layer.setCell((int) to.x + i, (int) to.y + j, null);
		active = false;
	}

	@Override
	public void draw(SpriteBatch batch)
	{//Dig doesn't have a texture.
	}

	@Override
	public void doHit(Enemy enemy)
	{//Dig doesn't collide with enemies.
	}
}
