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
	private BodyDef body_definition_static;
	private BodyDef body_definition_dynamic;
	
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
	 * Add a static block at the given X and Y value.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param width Width of static block.
	 * @param height Height of static block.
	 */
	public void addBlockToWorld(float x, float y, float width, float height)
	{
		
	}
	
	/**
	 * Remove the block at the given coordinates.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void removeBlockFromWorld(int x, int y)
	{
		
	}
}
