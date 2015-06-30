package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
	
	//Physical blocks.
	public Body[][] blocks;
	
	public WorldHandler(GameWorld world)
	{
		physical_world = new World(new Vector2(0, -9.8f), true);
		
		//Create a block type.
		body_definition_static = new BodyDef();
		body_definition_dynamic = new BodyDef();
		
		body_definition_static.type = BodyDef.BodyType.StaticBody;
		body_definition_dynamic.type = BodyDef.BodyType.DynamicBody;
	}
	
	public void handlerInit(int width, int height)
	{
		//Initialize static block array.
		blocks = new Body[height][width];
	}
	
	/**
	 * Create a dynamic entity.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 * @param width Width of dynamic block.
	 * @param height Height of dynamic block.
	 * @return Reference to the block.
	 */
	public Body createDynamicEntity(float x, float y, float width, float height)
	{
		//For non-gravity units:
		//body_definition_dynamic.gravityScale = 0f;
		Body body = null;
		
		
		
		return body;
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
		//Set the definition to the location.
		body_definition_static.position.set(x * width, y * height);
		
		//Create a body in the world using our definition.
		Body body = physical_world.createBody(body_definition_static);
		
		//Define a shape for the dimensions of the body.
		PolygonShape shape = new PolygonShape();
		
		//Set the box.
		shape.setAsBox(width / 2f, height / 2f);
		
		//Define physical properties.
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		
		//Add the physical properties to the body.
		//Fixture fixture = body.createFixture(fixtureDef);
		body.createFixture(fixtureDef);
		
		//Add the body to the array.
		blocks[(int) y][(int) x] = body;
		
		//Shape should be disposed.
		shape.dispose();
	}
	
	/**
	 * Remove the block at the given coordinates.
	 * @param x X coordinate.
	 * @param y Y coordinate.
	 */
	public void removeBlockFromWorld(int x, int y)
	{
		physical_world.destroyBody(blocks[y][x]);
	}
	
	public void update(float delta)
	{
		physical_world.step(delta, 6, 2);
	}
}
