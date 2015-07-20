package com.leepresswood.wizard.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.leepresswood.wizard.handlers.ContactHandler;
import com.leepresswood.wizard.handlers.EntityHandler;
import com.leepresswood.wizard.handlers.LevelHandler;
import com.leepresswood.wizard.handlers.MapCameraHandler;
import com.leepresswood.wizard.handlers.WaveHandler;
import com.leepresswood.wizard.handlers.WorldHandler;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.game.scenery.ParallaxLayer;

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
	
	//Parallax
	public ParallaxLayer[] parallax_layers;
	
	//Box2DDebugRenderer debug = new Box2DDebugRenderer();
	
	public Universe(ScreenGame screen)
	{
		this.screen = screen;
		
		world_handler = new WorldHandler(this);
		world_handler.world.setContactListener(new ContactHandler());
		map_camera_handler = new MapCameraHandler(this);
		entity_handler = new EntityHandler(this);
		level_handler = new LevelHandler(this, 3);
		wave_handler = new WaveHandler(this);
		
		//Make the parallax layers.
		parallax_layers = new ParallaxLayer[6];
		parallax_layers[0] = new ParallaxLayer(this, 0.05f, screen.game.assets.get("textures/parallax/smallforest/sky.png", Texture.class));
		parallax_layers[1] = new ParallaxLayer(this, 0.10f, screen.game.assets.get("textures/parallax/smallforest/back3.png", Texture.class));
		parallax_layers[2] = new ParallaxLayer(this, 0.15f, screen.game.assets.get("textures/parallax/smallforest/back2.png", Texture.class));
		parallax_layers[3] = new ParallaxLayer(this, 0.20f, screen.game.assets.get("textures/parallax/smallforest/back1.png", Texture.class));
		parallax_layers[4] = new ParallaxLayer(this, 0.60f, screen.game.assets.get("textures/parallax/smallforest/middle2.png", Texture.class));
		parallax_layers[5] = new ParallaxLayer(this, 0.75f, screen.game.assets.get("textures/parallax/smallforest/middle1.png", Texture.class));
	}
	
	public void update(float delta)
	{
		world_handler.update(delta);
		entity_handler.update(delta);
		map_camera_handler.setCameraBounds();
		wave_handler.update();
		
		for(ParallaxLayer layer : parallax_layers)
			layer.update(delta);
	}
	
	public void draw()
	{
		//Draw parallax background.
		screen.batch.setProjectionMatrix(map_camera_handler.combined);
		screen.batch.begin();
		for(ParallaxLayer layer : parallax_layers)
			layer.draw(screen.batch);
		screen.batch.end();
		
		map_camera_handler.map_renderer.setView(map_camera_handler);
		map_camera_handler.map_renderer.render();		
		entity_handler.draw();
		//debug.render(world_handler.world, map_camera_handler.combined);
	}
}
