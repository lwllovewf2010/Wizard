package com.leepresswood.wizard.world;

import com.leepresswood.wizard.handlers.MapCameraEntity;
import com.leepresswood.wizard.handlers.EntityHandler;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * Holds information about the game world. Sets up camera based upon this world.
 * @author Lee
 *
 */
public class GameWorld
{
	public ScreenGame screen;
	
	//Handlers
	public EntityHandler entity_handler;
	public MapCameraEntity map_camera_handler;
	
	public GameWorld(ScreenGame screen)
	{
		this.screen = screen;
		
		map_camera_handler = new MapCameraEntity(this);
		entity_handler = new EntityHandler(this);
	}
	
	public void update(float delta)
	{
		entity_handler.update(delta);
		map_camera_handler.setCameraBounds();
	}
	
	public void draw()
	{
		map_camera_handler.map_renderer.setView(map_camera_handler);
		map_camera_handler.map_renderer.render();		
		entity_handler.draw();
	}
}