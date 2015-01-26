package com.leepresswood.wizard.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Due to this being a vector-graphics-styled game, all game objects should be rendered through the screen's ShapeRenderer. This upper-level class will manage the objects' designs.<br/><br/>
 * 
 * As an example, the player will have a heavy black outline. The outline will be defined and stored with the actual requested shape.
 * 
 * @author Lee
 *
 */
public class EntityManager
{
	public ArrayList<GameEntity> entities;
	
	private ShapeRenderer renderer;
	
	public EntityManager()
	{
		renderer = new ShapeRenderer();
	}
	
	/**
	 * Manager creates and manages the requested shapes based upon the type of entity requested.
	 * 
	 * @param entity The entity to be created. Call this entity from a static call to the manager.
	 */
	public void addEntity(int entity)
	{
		switch(entity)
		{
			default:
				return;
		}
	}
	
	public static final int PLAYER_HEAD = 0;
	public static final int PLAYER_HAND = 1;
	public static final int PLAYER_BODY = 2;	
}
