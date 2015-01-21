package com.leepresswood.wizard.screen;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.GUI;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.Input;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.player.classes.AirWizard;
import com.leepresswood.wizard.player.upperlevel.Player;

public class ScreenGame extends ScreenAdapter
{
	public GameWizard game;
	public GUI gui;
	public ShapeRenderer renderer;

	public TiledMap map;
	public OrthogonalTiledMapRenderer map_renderer;
	
	public OrthographicCamera camera;
	public final int WORLD_VIEW = 25;
	public final int WORLD_TOTAL_HORIZONTAL;
	public final int WORLD_TOTAL_VERTICAL;
	
	public Vector2 player_start_point;
	public Player player;
	
	public ArrayList<Object> remove;
	
	public ScreenGame(GameWizard game)
	{
		this.game = game;
		Gdx.input.setInputProcessor(new Input(this));
		renderer = new ShapeRenderer();
		
		gui = new GUI(this);
		
		//Map stuff. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = game.assets.getMap(Assets.MAP_TEST);							//Load map
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / new Float(map.getProperties().get("tilewidth", Integer.class)));	//Draws passed map. Passed float number is the the inverse of the pixels per unit.
		
		WORLD_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		WORLD_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);
		
		//Create the camera using the found number of blocks above.
		camera = new OrthographicCamera(WORLD_VIEW, WORLD_VIEW * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		player_start_point = new Vector2(0f, 2f);
		player = new AirWizard(this);
		
		remove = new ArrayList<Object>();
	}
	
	private void update(float delta)
	{
		//Updating all objects.
		player.update(delta);
		gui.update(delta);
		
		//Deleting old objects.
		/*for(Object o : remove)
			if(o instanceof Powerup)
				powerups.remove(o);
			else if(o instanceof Ball)
				balls.remove(o);
			else if(o instanceof Block)
				blocks.remove(o);
		remove.clear();*/
	}
	
	@Override
	public void render(float delta)
	{
		//Update
		update(delta);
		
		//Draw
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		//Map
		camera.update();
		map_renderer.setView(camera);
		map_renderer.render();
		
		//Sprites
		renderer.setProjectionMatrix(camera.combined);
		player.draw(renderer);
		
		//GUI
		gui.draw();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		batch.dispose();
		gui.dispose();
	}
}
