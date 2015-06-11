package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.handlers.CameraEntity;
import com.leepresswood.wizard.handlers.EntityHandler;

/**
 * Holds information about the game world. Sets up camera based upon this world.
 * @author Lee
 *
 */
public class GameWorld
{
	public ScreenGame screen;
	public TiledMap map;
	public TiledMapTileLayer collision_layer;
	public OrthogonalTiledMapRenderer map_renderer;	
	
	//Handlers
	public EntityHandler entity_handler;
	public CameraEntity camera;
	
	public GameWorld(ScreenGame screen)
	{
		this.screen = screen;
		
		camera = new CameraEntity(this);
		entity_handler = new EntityHandler(this);
	}
	
	public void update(float delta)
	{
		entity_handler.update(delta);
		camera.setCameraBounds();
	}
	
	public void draw()
	{
		map_renderer.setView(camera);
		map_renderer.render();		
		entity_handler.draw();
	}
}
