package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.screens.ScreenParent;

public class ScreenGame extends ScreenParent
{
	public GameWorld world_game;
	public GUIGame gui;	
	
	public ScreenGame(GameWizard game)
	{
		super(game);
		
		//The game screen has a world in the background and a GUI in the foreground.
		world_game = new GameWorld(this);
		gui = new GUIGame(this);
	}

	@Override
	public void setUpBackgroundColor()
	{
		color_background = new Color(Color.CYAN);
	}
	
	@Override
	public void setUpInput()
	{
		Gdx.input.setInputProcessor(new InputGame(this));
		//Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("person/textures/hold.png")), 0, 0);
	}

	@Override
	public void update(float delta)
	{
		//Be sure to update the camera.
		world_game.update(delta);
		world_game.camera.update();
		
		//GUI camera doesn't need to be updated.
		gui.update(delta);
	}
	
	@Override
	public void draw()
	{
		batch.setProjectionMatrix(world_game.camera.combined);
		world_game.draw();

		batch.setProjectionMatrix(gui.camera.combined);
		gui.draw();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		batch.dispose();
		renderer.dispose();
	}
}
