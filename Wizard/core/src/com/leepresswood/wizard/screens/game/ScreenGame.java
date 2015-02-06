package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.screens.ScreenParent;

public class ScreenGame extends ScreenParent
{
	public GUIGame gui;

	public TiledMap map;
	public OrthogonalTiledMapRenderer map_renderer;
	
	public int WORLD_TOTAL_HORIZONTAL;
	public int WORLD_TOTAL_VERTICAL;
	public float WORLD_LEFT;
	public float WORLD_RIGHT;
	public float WORLD_TOP;
	public float WORLD_BOTTOM;
	
	public float GROUND;
	public float GRAVITY;
	
	public Player player;
	
	public ScreenGame(GameWizard game)
	{
		super(game);
		
		gui = new GUIGame(this);
		player = new Player(this, 0f, 4f);
	}

	@Override
	public void setUpBackgroundColor()
	{
		color_background = new Color(Color.CYAN);
	}

	@Override
	public void setUpCameras()
	{
		//World camera.
		setUpWorldCamera();
		
		//GUI camera.
		camera_gui = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera_gui.position.set(camera_gui.viewportWidth / 2f, camera_gui.viewportHeight / 2f, 0);
		camera_gui.update();
	}
	
	/**
	 * Get the world coordinates from the map to set up the camera.
	 */
	private void setUpWorldCamera()
	{
		//Get map data. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = game.assets.getMap(Assets.MAP_TEST);
		float pixel_size = Float.parseFloat((String) (map.getProperties().get("tilewidth")));
		GROUND = Float.parseFloat((String) (map.getProperties().get("ground")));
		GRAVITY =  Float.parseFloat((String) (map.getProperties().get("gravity")));
		
		//Get the map renderer from this data.
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / pixel_size);									//Passed float number is the the inverse of the pixels per unit.		
		
		//Set the bounds of the camera.
		camera_game = new OrthographicCamera(Gdx.graphics.getWidth() / pixel_size, Gdx.graphics.getHeight() / pixel_size);
		camera_game.position.set(camera_game.viewportWidth / 2f, camera_game.viewportHeight / 2f, 0);
		camera_game.update();
		
		
		/* Set the bounds of the world.
		 * These will be used to give the camera cues as to where to position itself.
		 * For example, the camera will normally follow the player.
		 * However, if the camera's bounds would go outside the world, 
		 * the camera should snap to the world's edge until the player
		 * returns to a location that allows the camera to move more freely.
		 * Think about reaching one of the ends of the world in Terraria as an example. 
		 * 
		 * The positions are not the edges of the world -- that would be a waste of space and time.
		 * Rather, they are the bounds of where the camera can move.
		 * WORLD_BOTTOM will be the lowest Y value of the camera before it stops moving downward.
		 * Extend this to the others.
		 */
		WORLD_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		WORLD_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);		
		
		WORLD_BOTTOM = camera_game.viewportHeight / 2f;
		WORLD_TOP = WORLD_TOTAL_VERTICAL - WORLD_BOTTOM;
		WORLD_LEFT = camera_game.viewportWidth / 2f;
		WORLD_RIGHT = WORLD_TOTAL_HORIZONTAL - WORLD_LEFT;
	}
	
	@Override
	public void setUpInput()
	{
		Gdx.input.setInputProcessor(new InputGame(this));
	}

	@Override
	public void update(float delta)
	{
		//Player and enemies.
		player.update(delta);
		
		//Camera.
		setCameraBounds();
	}

	/**
	 * Check the camera's position for correctness. It should not go off the world's bounds.
	 */
	private void setCameraBounds()
	{
		//First, set the camera to the player's position.
		camera_game.position.x = player.sprite.getX() + player.sprite.getWidth() / 2f;
		camera_game.position.y = player.sprite.getY() + player.sprite.getHeight() / 2f;
		
		//If this moves off the world's bounds, correct it.
		if(camera_game.position.x < WORLD_LEFT)
			camera_game.position.x = WORLD_LEFT;
		if(camera_game.position.x > WORLD_RIGHT)
			camera_game.position.x = WORLD_RIGHT;
		if(camera_game.position.y < WORLD_BOTTOM)
			camera_game.position.y = WORLD_BOTTOM;
		if(camera_game.position.y > WORLD_TOP)
			camera_game.position.y = WORLD_TOP;
	}
	
	@Override
	public void draw()
	{		
		//Map
		map_renderer.setView(camera_game);
		map_renderer.render();
		
		//Game objects
		batch.setProjectionMatrix(camera_game.combined);
		batch.begin();
			player.draw(batch);
		batch.end();
		
		//GUI
		batch.setProjectionMatrix(camera_gui.combined);
		gui.draw(renderer, batch);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		renderer.dispose();
	}
}
