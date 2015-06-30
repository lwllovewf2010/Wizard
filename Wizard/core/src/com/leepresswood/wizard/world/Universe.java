package com.leepresswood.wizard.world;

import com.leepresswood.wizard.handlers.EntityHandler;
import com.leepresswood.wizard.handlers.LevelHandler;
import com.leepresswood.wizard.handlers.MapCameraEntity;
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
	public MapCameraEntity map_camera_handler;
	public EntityHandler entity_handler;
	public LevelHandler level_handler;
	public WaveHandler wave_handler;
	
	public Universe(ScreenGame screen)
	{
		this.screen = screen;
		
		world_handler = new WorldHandler(this);
		map_camera_handler = new MapCameraEntity(this);
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
		map_camera_handler.map_renderer.render();		
		entity_handler.draw();
	}
}