package com.leepresswood.wizard.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.Input;


public class ScreenGame extends ScreenAdapter
{
	public GameWizard game;
	private SpriteBatch batch;

	public TiledMap map;
	public OrthogonalTiledMapRenderer map_renderer;
	public OrthographicCamera camera;
	
	public ArrayList<Object> remove;
	
	public ScreenGame(GameWizard game)
	{
		this.game = game;
		Gdx.input.setInputProcessor(new Input(this));
		batch = new SpriteBatch();
		
		//Map stuff. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = game.assets.getMap(Assets.MAP_TEST);							//Load map
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / 70f);	//Draws passed map. Passed float number is the the inverse of the pixels per unit.
		camera = new OrthographicCamera(8, Gdx.graphics.getHeight() / Gdx.graphics.getWidth() * 8);
		
		remove = new ArrayList<Object>();
	}
	
	private void update(float delta)
	{
		//Updating all objects.
		
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
		
		camera.update();
		
		//Map
		map_renderer.render();
		
		//Sprites
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.end();
	}
}
