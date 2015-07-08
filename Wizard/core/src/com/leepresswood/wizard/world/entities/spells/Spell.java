package com.leepresswood.wizard.world.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.enums.MagicType;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.Box2DSprite;
import com.leepresswood.wizard.world.entities.GameEntity;

/**
 * The Spell class is a parent to the various spells in the game. All spells will have a cast-to position, 
 * a cast-from position, a mana cost, a delay, and an animation. 
 * @author Lee
 */
public abstract class Spell extends GameEntity
{
	//This sprite is only used for the spell icon.
	public Sprite sprite;
	
	//XML data
	public MagicType type;
	public float mana_cost;
	public float recharge;
	
	//Spell location, movement, visibility, etc.
	public Vector2 from, to;
	public boolean active;
	public float time_alive_current;
	public float time_alive_max;
	
	//Level information.
	public int level;

	/**
	 * Use this constructor for the spell list on the GUI.
	 */
	public Spell(Texture t, float x, float y)
	{//We don't need access to the universe in this constructor.
		super(null);
		
		sprite = new Sprite(t);
		sprite.setBounds(x, y, 50f, 50f);
	}
	
	/**
	 * Use this constructor to create a spell entity in the world.
	 * @param universe Reference to the screen.
	 * @param from Player's starting location.
	 * @param to Click point.
	 * @param data Spell data.
	 */
	public Spell(Universe universe, Vector2 from, Vector2 to, Vector2 spawn, Element data, int level)
	{
		super(universe, spawn.x, spawn.y, data);
		
		this.from = from;
		this.to = to;
		this.level = level;
		
		//Read the data from the XML file.
		type = MagicType.valueOf(data.get("type").toUpperCase());
		if(data.getChildrenByName("level").size > 0)
			mana_cost = data.getChildrenByName("level").get(level).getFloat("cost");
		recharge = data.getFloat("recharge");
		time_alive_max = data.getFloat("time_alive");
		
		//Create an active version of this spell.
		active = true;
	}
	
	/**
	 * Call the position and collision updates. Also examine the time.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		calcMovement(delta);
		
		for(Box2DSprite s : parts)
			s.update(delta);
		
		//If time is set, compare it. Above TIME_MAX -> make inactive. Note that a negative time alive will make endless as far as time is concerned.
		if(time_alive_max > 0f)
		{
			time_alive_current += delta;
			if(time_alive_current >= time_alive_max)
				active = false;
		}
	}

	@Override
   public void draw(SpriteBatch batch)
   {
		for(Box2DSprite s : parts)
			s.sprite.draw(batch);
   }
	
	@Override
	public boolean getDeathStatus()
	{
	   return false;
	}
	
	@Override
	public void die(float delta)
	{
	}
	
	public abstract void instantiate();
}
