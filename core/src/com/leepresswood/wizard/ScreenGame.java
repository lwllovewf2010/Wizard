package com.leepresswood.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ScreenGame extends ScreenAdapter implements InputProcessor
{
	public GameWizard game;
	private OrthographicCamera camera;
	private TiledMap tiled_map;
	private TiledMapRenderer tiled_map_renderer;
	
	public ScreenGame(GameWizard game)
	{
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		tiled_map = new TmxMapLoader().load("test.tmx");
		tiled_map_renderer = new OrthogonalTiledMapRenderer(tiled_map);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void resize(int width, int height)
	{
		
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		tiled_map_renderer.setView(camera);
		tiled_map_renderer.render();
	}
	
	@Override
	public void dispose()
	{
		
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Input.Keys.LEFT)
			camera.translate(-32,0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate(32,0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,-32);
		if(keycode == Input.Keys.DOWN)
			camera.translate(0,32);
		if(keycode == Input.Keys.NUM_1)
			tiled_map.getLayers().get(0).setVisible(!tiled_map.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiled_map.getLayers().get(1).setVisible(!tiled_map.getLayers().get(1).isVisible());
		
		return true;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
