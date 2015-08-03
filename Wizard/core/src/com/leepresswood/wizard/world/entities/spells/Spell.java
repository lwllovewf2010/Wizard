package com.leepresswood.wizard.world.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.Box2DSprite;
import com.leepresswood.wizard.helpers.datapackage.SpellPackage;
import com.leepresswood.wizard.world.Universe;
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
	
	public SpellPackage spell_data;
	
	//XML data
	public float mana_cost;
	public float recharge;
	
	//Spell location, movement, visibility, etc.
	public Vector2 from, to;
	public float time_alive_current;
	public float time_alive_max;

	/**
	 * Use this constructor for the spell list on the GUI.
	 */
	public Spell(Texture t, float x, float y, float width, float height)
	{//We don't need access to the universe in this constructor.
		super(null);
		
		sprite = new Sprite(t);
		sprite.setBounds(x, y, width, height);
	}
	
	/**
	 * Use this constructor to create a spell entity in the world.
	 * @param universe Reference to the screen.
	 * @param from Player's starting location.
	 * @param to Click point.
	 * @param data Spell data.
	 */
	public Spell(Universe universe, Vector2 from, Vector2 to, Vector2 spawn, SpellPackage data)
	{
		/**
		 * Let's discuss SpellPackages:
		 * The main root of the data will hold the initial information about the spell.
		 * The sub root will cause effects to happen. This could come into direct contact
		 * with the effects of the main root. Therefore, we need to first grab from the
		 * main root and use that to initialize. After that's done, read from the sub
		 * root at the spell's class level and write over the changed effects. Because
		 * this activates on a class-by-class basis, the lower-level classes will be
		 * able to pinpoint what needs to be changed directly.
		 */
		super(universe, spawn.x, spawn.y, SpellPackage.getBasic(data));
		
		this.from = from;
		this.to = to;
		this.spell_data = data;
		
		//Read the data from the XML file.
		time_alive_max = getTimeAlive(data);
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
	protected String getTextureBaseString()
	{
		return "spells/";
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
	
	/**
	 * @return The time alive of the spell.
	 */
	private float getTimeAlive(SpellPackage data)
	{
		Element basic = SpellPackage.getBasic(data);
		Element main = SpellPackage.getMainLevel(data);
		Element sub = SpellPackage.getSubLevel(data);
		
		float time_alive = basic.getFloat("time_alive");
		if(main != null && main.get("time_alive",  null) != null)
			time_alive = main.getFloat("time_alive");
		if(sub != null && sub.get("time_alive",  null) != null)
			time_alive = sub.getFloat("time_alive");
		
		return time_alive;
	}
}
