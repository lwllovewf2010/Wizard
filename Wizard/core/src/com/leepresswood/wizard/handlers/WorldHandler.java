package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.leepresswood.wizard.world.GameWorld;

/**
 * Handles the physics of the world.
 * @author Lee
 */
public class WorldHandler
{
	public GameWorld world;
	public World physical_world;
	
	//Block body types.
	BodyDef body_definition_static;
	BodyDef body_definition_dynamic;
	
	public WorldHandler(GameWorld world)
	{
		physical_world = new World(new Vector2(0, -9.8f), true);
		
		//Create a block type.
		BodyDef body_definition = new BodyDef();
		body_definition_dynamic = new BodyDef();
		
		body_definition_static.type = BodyDef.BodyType.StaticBody;
		body_definition_dynamic.type = BodyDef.BodyType.DynamicBody;
		
	}
	
	/**
	 * Add a block at the given X and Y value.
	 * @param x
	 * @param y
	 */
	public void addBlockToWorld(float x, float y)
	{
		
	}
	
	/**
	 * Remove the block at the given coordinates.
	 * @param x
	 * @param y
	 */
	public void removeBlockFromWorld(int x, int y)
	{
		
	}
}
