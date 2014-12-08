package com.leepresswood.wizard.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.Input;


public class ScreenGame extends ScreenAdapter
{
	public GameWizard game;
	private SpriteBatch batch;

	public ArrayList<Object> remove;
	
	public ScreenGame(GameWizard game)
	{
		this.game = game;
		Gdx.input.setInputProcessor(new Input(this));
		batch = new SpriteBatch();
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
		batch.begin();
		
		batch.end();
	}
}
