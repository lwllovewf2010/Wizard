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
	public Dig(Texture t, float x, float y){super(t, x, y);}
	
	public Dig(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		super(world, from, to, data);
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
	{//At this point, remove the block and kill the spell.
		world.map_camera_handler.collision_layer.setCell((int) to.x, (int) to.y, null);
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
