package com.leepresswood.wizard.entities;

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
	
	/**
	 * Manager creates and manages the requested shapes based upon the type of entity requested.
	 * 
	 * @param entity The entity to be created. Call this entity from a static call to the manager.
	 * @return The requested entity, or null if invalid.
	 */
	public GameEntity getEntity(int entity)
	{
		switch(entity)
		{
			default:
				return null;
		}
	}
}
