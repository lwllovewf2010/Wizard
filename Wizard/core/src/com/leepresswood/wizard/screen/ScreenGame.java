package com.leepresswood.wizard.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	public final int WORLD_VIEW = 10;
	public final int WORLD_TOTAL_HORIZONTAL;
	public final int WORLD_TOTAL_VERTICAL;
	
	public Sprite sprite;
	public boolean moving_left = false;
	public boolean moving_right = false;
	
	public ArrayList<Object> remove;
	
	public ScreenGame(GameWizard game)
	{
		this.game = game;
		Gdx.input.setInputProcessor(new Input(this));
		batch = new SpriteBatch();
		
		//Map stuff. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = game.assets.getMap(Assets.MAP_TEST);							//Load map
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / new Float(map.getProperties().get("tilewidth", Integer.class)));	//Draws passed map. Passed float number is the the inverse of the pixels per unit.
		
		WORLD_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		WORLD_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);
		
		//Create the camera using the found number of blocks above.
		camera = new OrthographicCamera(WORLD_VIEW, WORLD_VIEW * Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		sprite = new Sprite(game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		sprite.setBounds(0, 1, 1, 1);
		
		remove = new ArrayList<Object>();
	}
	
	private void update(float delta)
	{
		//Updating all objects.
		if(moving_left)
			sprite.translateX(-1f * delta);
		if(moving_right)
			sprite.translateX(1f * delta);
		
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
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			sprite.draw(batch);
		batch.end();
	}
}
