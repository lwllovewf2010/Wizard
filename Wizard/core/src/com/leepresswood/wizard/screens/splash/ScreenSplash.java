//Visualize loading of assets here. Simple enough.
package com.leepresswood.wizard.screens.splash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.helpers.Assets;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class ScreenSplash extends ScreenAdapter
{
	private GameWizard game;
	private ShapeRenderer renderer;
	
	public ScreenSplash(GameWizard game)
	{
		this.game = game;
		this.game.assets = new Assets();	
		renderer = new ShapeRenderer();
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(game.assets.update())		//Done loading
		{
			game.setScreen(new ScreenGame(game));
		}
		else									//Still loading. Update graphics.
		{			
			renderer.begin(ShapeType.Filled);
				renderer.identity();
				renderer.rect(0, 0, Gdx.graphics.getWidth() * game.assets.getProgress(), 20, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
			renderer.end();
		}
	}
}
