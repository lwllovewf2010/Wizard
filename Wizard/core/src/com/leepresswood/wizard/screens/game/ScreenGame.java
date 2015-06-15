package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.input.InputGame;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.gui.GUIGame;
import com.leepresswood.wizard.world.GameWorld;

public class ScreenGame extends ScreenParent
{
	public GameWorld world;
	public GUIGame gui;	
	public InputGame input;
	
	public ScreenGame(GameWizard game)
	{
		super(game);
		
		//The game screen has a world in the background and a GUI in the foreground.
		world = new GameWorld(this);
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
		input = new InputGame(this);
		Gdx.input.setInputProcessor(input);
		//Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("person/textures/hold.png")), 0, 0);
	}

	@Override
	public void update(float delta)
	{
		world.update(delta);		
		gui.update(delta);
	}
	
	@Override
	public void draw()
	{
		world.draw();
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
