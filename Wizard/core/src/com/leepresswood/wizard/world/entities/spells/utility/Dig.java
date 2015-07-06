package com.leepresswood.wizard.world.entities.spells.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.spells.Spell;

/**
 * Remove the targetted block.
 * @author Lee
 */
public class Dig extends Spell
{
	private int dig_width;
	private int dig_height;
	
	public Dig(Texture t, float x, float y){super(t, x, y);}
	
	public Dig(Universe universe, Vector2 from, Vector2 to, Element data, int level)
	{
		super(universe, from, to, to, data, level);
		
		dig_width = data.getChildrenByName("level").get(level).getInt("dig_width");
		dig_height = data.getChildrenByName("level").get(level).getInt("dig_height");
		
		//We don't want this this spell to last long. Do the spell effect now and kill it.
		for(int j = (int) (-dig_height / 2f); j < (dig_height / 2f); j++)
		{	
			for(int i = (int) (-dig_width / 2f); i < (dig_width / 2f); i++)
			{
				//Remove the texture of the block.
				universe.map_camera_handler.collision_layer.setCell((int) to.x + i, (int) to.y + j, null);
				
				//Remove the physical block.
				universe.world_handler.removeBlockFromWorld((int) to.x + i, (int) to.y + j);
				
				//Break the loops.
				i = dig_width;
				j = dig_width;
			}
		}
		active = false;
	}
	
	@Override
   protected void setBodies(float x, float y, float width, float height)
   {
		parts = new Box2DSprite[1];
		
		Sprite s = new Sprite(texture);
		s.setBounds(x, y, width, height);
		
		//We will only have one body here, but we don't want gravity affecting the body.
		parts[0] = new Box2DSprite(s, universe.world_handler.createDynamicEntity(x, y, width, height, false), this, ContactHandler.SPELL_SOLID);
   }

	@Override
	protected void calcMovementX(float delta)
	{//Dig doesn't move.
	}
	
	@Override
	protected void calcMovementY(float delta)
	{//Dig doesn't move.
	}

	@Override
   public void doHit(GameEntity entity)
   {//Dig doesn't collide.
   }
}
