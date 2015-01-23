package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.entities.player.attributes.Bar;

public class GUIGame
{
	private ScreenGame screen;
	public ShapeRenderer renderer;	
	
	public Bar health_bar;
	public Bar mana_bar;
	
	public GUIGame(ScreenGame screen)
	{
		this.screen = screen;
		renderer = new ShapeRenderer();
		
		final float gap = 2f;
		
		final float bar_width = Gdx.graphics.getWidth() * 0.3f;
		final float bar_height = Gdx.graphics.getHeight() * 0.02f;
		
		final float bar_x = gap;
		final float bar_y = Gdx.graphics.getHeight() - gap - bar_height;
		
		final float recovery_health = 0.75f;
		final float recovery_mana = 0.3f;
		
		health_bar = new Bar(bar_x, bar_y, bar_width, bar_height, recovery_health);
		mana_bar = new Bar(bar_x, bar_y - bar_height - gap, bar_width, bar_height, recovery_mana);
	}

	public void update(float delta)
	{
		health_bar.updateTime(delta);
		mana_bar.updateTime(delta);
	}
	
	public void draw()
	{
		renderer.begin(ShapeType.Filled);
			renderer.identity();
			renderer.rect(health_bar.x, health_bar.y, health_bar.width, health_bar.height, Color.RED, Color.RED, Color.RED, Color.RED);
		renderer.end();
		
		renderer.begin(ShapeType.Filled);
			renderer.identity();
			renderer.rect(mana_bar.x, mana_bar.y, mana_bar.width, mana_bar.height, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
		renderer.end();
	}

	public void dispose()
	{
		renderer.dispose();
	}
}
