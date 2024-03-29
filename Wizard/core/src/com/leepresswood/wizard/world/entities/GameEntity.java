package com.leepresswood.wizard.world.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.Box2DSprite;
import com.leepresswood.wizard.world.Universe;

/**
 * All game objects will be filtered through this class. 
 * @author Lee
 */
public abstract class GameEntity
{
	public Universe universe;
	
	//Sprite and texture data.
	private final String TEXTURE_BASE = "textures/";
	private final String TEXTURE_EXTENSION = ".png";
	
	public boolean active;
	
	//XML data.
	public String name;
	public Texture texture;
	
	//Sprites and bounds.
	public Box2DSprite[] parts;
	
	/**
	 * A general version that will be used as the spell icons for the GUI.
	 * @param universe Reference to the universe.
	 */
	public GameEntity(Universe universe)
	{
		this.universe = universe;
	}
	
	public GameEntity(Universe universe, float x, float y, Element data)
   {
		this.universe = universe;
		
		//Gather XML data.
		name = data.get("name");
		texture = universe.screen.game.assets.get(TEXTURE_BASE + getTextureBaseString() + data.get("texture") + TEXTURE_EXTENSION);
		
		//Create a body for all this entity's parts.
		setBodies(x, y, data.getFloat("width"), data.getFloat("height"));
		
		active = true;
   }
	
	/**
	 * Return the name of the directory that holds this type of entity's textures.
	 * @return
	 */
	protected abstract String getTextureBaseString();
	
	/**
	 * Set sprites to their initial values.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected abstract void setBodies(float x, float y, float width, float height);

	/**
	 * Update timing and movement of sprites.
	 * @param delta Change in time.
	 */
	public abstract void update(float delta);
	
	/**
	 * Draw the sprites in the correct order.
	 * @param batch The SpriteBatch for the sprites of this entity.
	 */
	public abstract void draw(SpriteBatch batch);
	
	/**
	 * Calculate movement.
	 * @param delta Change in time.
	 */
	protected abstract void calcMovement(float delta);
	
	/**
	 * This entity collided with the passed entity. Do damage/effects.
	 * @param entity The entity that was hit.
	 */
	public abstract void doHit(GameEntity entity);
	
	/**
	 * Is this entity dead?
	 * @return True if dead. False otherwise.
	 */
	public abstract boolean getDeathStatus();
	
	/**
	 * Send entity into death animation. Also handles what happens afterward.
	 */
	public abstract void die(float delta);
}
