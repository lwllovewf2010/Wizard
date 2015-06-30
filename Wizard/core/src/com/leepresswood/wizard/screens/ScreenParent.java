package com.leepresswood.wizard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	
	protected Color color_background;
	protected InputProcessor input;
	
	public ScreenParent(GameWizard game)
	{
		this.game = game;
		this.batch = new SpriteBatch();
		this.renderer = new ShapeRenderer();
		
		color_background = setUpBackgroundColor();
		input = setUpInput();
	}
	
	/**
	 * This will be called automatically by the screen. Rather than doing all the drawing here,
	 * just set up the update/draw stack that the extended screens will implement.
	 */
	@Override
	public void render(float delta)
	{
		//Display FPS
		Gdx.graphics.setTitle(GameWizard.GAME_NAME + " : " + GameWizard.GAME_VERSION + " - FPS: " + Gdx.graphics.getFramesPerSecond());
		
		//Update
		update(delta);
		
		//Draw
		Gdx.gl.glClearColor(color_background.r, color_background.g, color_background.b, color_background.a);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();
	}
	
	@Override
	public void show()
	{//Replace the current InputProcessor with this screen's version. This will be useful for switching between the game screen and the derivative menus.
		Gdx.input.setInputProcessor(input);
	}
	
	public abstract Color setUpBackgroundColor();
	public abstract InputProcessor setUpInput();
	public abstract void update(float delta);
	public abstract void draw();
}
