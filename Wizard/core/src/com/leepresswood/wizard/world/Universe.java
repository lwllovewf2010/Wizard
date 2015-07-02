package com.leepresswood.wizard.world;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.handlers.EntityHandler;
import com.leepresswood.wizard.handlers.LevelHandler;
import com.leepresswood.wizard.handlers.MapCameraHandler;
import com.leepresswood.wizard.handlers.WaveHandler;
import com.leepresswood.wizard.handlers.WorldHandler;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Holds information about the game world. Sets up camera based upon this world.
 * @author Lee
 */
public class Universe
{
	public ScreenGame screen;
	
	//Handlers
	public WorldHandler world_handler;
	public MapCameraHandler map_camera_handler;
	public EntityHandler entity_handler;
	public LevelHandler level_handler;
	public WaveHandler wave_handler;
	
	Box2DDebugRenderer debug = new Box2DDebugRenderer();
	
	public Universe(ScreenGame screen)
	{
		this.screen = screen;
		
		world_handler = new WorldHandler(this);
		world_handler.world.setContactListener(new ContactHandler());
		map_camera_handler = new MapCameraHandler(this);
		entity_handler = new EntityHandler(this);
		level_handler = new LevelHandler(this, 1);
		wave_handler = new WaveHandler(this);
	}
	
	public void update(float delta)
	{
		world_handler.update(delta);
		entity_handler.update(delta);
		map_camera_handler.setCameraBounds();
		wave_handler.update();
	}
	
	public void draw()
	{
		map_camera_handler.map_renderer.setView(map_camera_handler);
		//map_camera_handler.map_renderer.render();		
		entity_handler.draw();
		debug.render(world_handler.world, map_camera_handler.combined);
	}
}
