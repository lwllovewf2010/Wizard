package com.leepresswood.wizard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.leepresswood.wizard.GameWizard;

/**
 * Handles draw and update order and screen clearing. Extend from this rather than from ScreenAdapter.
 * 
 * @author Lee
 *
 */
public abstract class ScreenParent extends ScreenAdapter
{
	public GameWizard game;	
	public SpriteBatch batch;
	public ShapeRenderer renderer;
	public OrthographicCamera camera_game;
	public OrthographicCamera camera_gui;
	
	protected Color color_background;
	
	public ScreenParent(GameWizard game)
	{
		this.game = game;
		setUpBackgroundColor();
		setUpInput();
		setUpCameras();
	}
	
	/**
	 * This will be called automatically by the screen. Rather than doing all the drawing here,
	 * just set up the update/draw stack that the extended screens will implement.
	 */
	@Override
	public void render(float delta)
	{
		update(delta);
		camera_game.update();
		
		Gdx.gl.glClearColor(color_background.r, color_background.g, color_background.b, color_background.a);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();
	}
	
	public abstract void setUpBackgroundColor();
	public abstract void setUpInput();
	public abstract void setUpCameras();
	public abstract void update(float delta);
	public abstract void draw();
}
