package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.entities.player.attributes.Bar;

public class GUIGame
{
	private ScreenGame screen;
	
	public Bar bar_health, bar_mana;
	private Color color_health, color_mana;
	
	public GUIGame(ScreenGame screen)
	{
		this.screen = screen;		
		makeBars();
	}
	
	/**
	 * Create the health and magic bars.
	 */
	private void makeBars()
	{
		final float gap = 2f;
		
		final float bar_width = Gdx.graphics.getWidth() * 0.3f;
		final float bar_height = Gdx.graphics.getHeight() * 0.02f;
		
		final float bar_x = gap;
		final float bar_y = Gdx.graphics.getHeight() - gap - bar_height;
		
		final float recovery_health = 0.75f;
		final float recovery_mana = 0.3f;
		
		//Set bars
		bar_health = new Bar(bar_x, bar_y, bar_width, bar_height, recovery_health);
		bar_mana = new Bar(bar_x, bar_y - bar_height - gap, bar_width, bar_height, recovery_mana);

		//Set colors
		color_health = new Color(Color.valueOf("AA3C39FF"));
		color_mana = new Color(Color.valueOf("2E4372FF"));
	}

	public void update(float delta)
	{
		bar_health.updateOverTime(delta);
		bar_mana.updateOverTime(delta);
	}
	
	public void draw()
	{
		screen.renderer.begin(ShapeType.Filled);
			screen.renderer.identity();
			
			screen.renderer.rect(bar_health.x, bar_health.y, bar_health.width, bar_health.height, color_health, color_health, color_health, color_health);
			screen.renderer.rect(bar_mana.x, bar_mana.y, bar_mana.width, bar_mana.height, color_mana, color_mana, color_mana, color_mana);
		screen.renderer.end();
	}
}
