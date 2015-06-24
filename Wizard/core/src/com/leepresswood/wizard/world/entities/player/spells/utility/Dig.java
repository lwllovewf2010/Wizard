package com.leepresswood.wizard.world.entities.player.spells.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.player.spells.Spell;

public class Dig extends Spell
{
	private int dig_width;
	private int dig_height;
	
	public Dig(Texture t, float x, float y){super(t, x, y);}
	
	public Dig(GameWorld world, Vector2 from, Vector2 to, Element data, int level)
	{
		super(world, from, to, data, level);
		
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
		for(int j = (int) (-dig_height / 2f); j < (dig_height / 2f); j++)
			for(int i = (int) (-dig_width / 2f); i < (dig_width / 2f); i++)
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
