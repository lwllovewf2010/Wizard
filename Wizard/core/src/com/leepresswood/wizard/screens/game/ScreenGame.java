package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.screens.ScreenParent;

public class ScreenGame extends ScreenParent
{
	public GUIGame gui;

	public TiledMap map;
	public OrthogonalTiledMapRenderer map_renderer;
	
	public int WORLD_VIEW = 25;
	public int WORLD_TOTAL_HORIZONTAL;
	public int WORLD_TOTAL_VERTICAL;
	
	public Vector2 player_start_point;
	public Player player;
	
	public ScreenGame(GameWizard game)
	{
		super(game);
		
		gui = new GUIGame(this);
		
		player_start_point = new Vector2(0f, 2f);
		//player = new AirWizard(this);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		renderer.dispose();
	}

	@Override
	public void setUpBackgroundColor()
	{
		color_background = new Color(Color.BLACK);
	}

	@Override
	public void setUpCameras()
	{
		//Map stuff. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = game.assets.getMap(Assets.MAP_TEST);							//Load map
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / new Float(map.getProperties().get("tilewidth", Integer.class)));	//Draws passed map. Passed float number is the the inverse of the pixels per unit.
		
		//Create the camera using the found number of blocks above.
		WORLD_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		WORLD_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);		
		camera_game = new OrthographicCamera(WORLD_VIEW, WORLD_VIEW * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		camera_game.position.set(camera_game.viewportWidth / 2f, camera_game.viewportHeight / 2f, 0);
		camera_game.update();
		
		//Create a camera for the GUI.
		camera_gui = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera_gui.position.x = camera_gui.viewportWidth / 2f;
		camera_gui.position.y = camera_gui.viewportHeight / 2f;
		camera_gui.update();
	}
	
	@Override
	public void setUpInput()
	{
		Gdx.input.setInputProcessor(new InputGame(this));
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void draw()
	{		
		//Map
		map_renderer.setView(camera_game);
		map_renderer.render();
		
		//Game objects
		renderer.setProjectionMatrix(camera_game.combined);
		
		//GUI
		renderer.setProjectionMatrix(camera_gui.combined);
		gui.draw();
	}
}
