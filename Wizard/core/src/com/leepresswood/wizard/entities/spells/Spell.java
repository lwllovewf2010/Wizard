package com.leepresswood.wizard.entities.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.screens.game.GameWorld;

/**
 * The Spell class is a parent to the various spells in the game. All spells will have a cast-to position, 
 * a cast-from position, a mana cost, a delay, and an animation. 
 * @author Lee
 */
public abstract class Spell
{
	protected GameWorld world;
	
	//Sprite and texture data.
	private final String TEXTURE_BASE = "person/textures/";
	private final String TEXTURE_EXTENSION = ".png";
	public Sprite sprite;
	
	//XML data
	public String name;
	public SpellType type;
	public float mana_cost;
	public Texture texture;
	public float recharge;
	
	//Spell location, movement, visibility, etc.
	public Vector2 from, to;
	public boolean active;
	public float time_alive_current;
	public float time_alive_max;

	/**
	 * Use this constructor to create a spell entity in the world.
	 * @param world Reference to the screen.
	 * @param from Player's starting location.
	 * @param to Click point.
	 * @param data Spell data.
	 */
	public Spell(GameWorld world, Vector2 from, Vector2 to, Element data)
	{
		this.world = world;
		this.from = from;
		this.to = to;
		
		//Read the data from the XML file.
		name = data.get("name");
		texture = world.screen.game.assets.get(TEXTURE_BASE + data.get("texture") + TEXTURE_EXTENSION);
		type = SpellType.valueOf(data.get("type").toUpperCase());
		mana_cost = data.getFloat("cost");
		recharge = data.getFloat("recharge");
		time_alive_max = data.getFloat("time_alive");
		
		//Create an active version of this spell.
		active = true;		
		sprite = new Sprite(texture);
		makeSpriteBounds();
		
		System.out.println(
			"Spell:\n\tName: " + name
			+ "\n\tType: " + type
			+ "\n\tMana Cost: " + mana_cost 
			+ "\n\tRecharge: " + recharge 
			+ "\n\tFrom: " + from 
			+ "\n\tTo: " + to
		);
	}
	
	/**
	 * Use this constructor for the spell list on the GUI.
	 */
	public Spell(Texture t, float x, float y)
	{
		sprite = new Sprite(t);
		sprite.setBounds(x, y, 50f, 50f);
	}
	
	/**
	 * Create the sprite using the "from" and "to" vectors.
	 */
	protected abstract void makeSpriteBounds();
	
	/**
	 * Call the position and collision updates. Also examine the time.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		updatePosition(delta);
		updateCollision();
		
		//If time is set, compare it. Above TIME_MAX -> make inactive. Note that a negative time alive will make endless as far as time is concerned.
		if(time_alive_max > 0f)
		{
			time_alive_current += delta;
			if(time_alive_current >= time_alive_max)
				active = false;
		}
	}
	
	/**
	 * Update the spell's position over time. Also calls the collision collision detection method.
	 * @param delta Change in time.
	 */
	protected abstract void updatePosition(float delta);
	
	/**
	 * Checks the spell's collision with game objects. Deals with damage and visibility as well.
	 */
	protected abstract void updateCollision();
	
	/**
	 * Draw the spell.
	 * @param batch SpriteBatch for the sprite.
	 */
	public abstract void draw(SpriteBatch batch);
}
