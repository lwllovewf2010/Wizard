package com.leepresswood.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.player.attributes.bars.HealthBar;
import com.leepresswood.wizard.player.attributes.bars.ManaBar;
import com.leepresswood.wizard.screen.ScreenGame;

public class GUI
{
	private ScreenGame screen;
	private SpriteBatch batch;
	
	public HealthBar health_bar;
	public ManaBar mana_bar;
	
	public GUI(ScreenGame screen)
	{
		this.screen = screen;
		batch = new SpriteBatch();
		
		final float bar_width = Gdx.graphics.getWidth() * 0.3f;
		final float bar_height = Gdx.graphics.getHeight() * 0.09f;
		final float bar_x = Gdx.graphics.getWidth() * 0.01f;
		final float bar_y = Gdx.graphics.getHeight() * 0.9f;
		health_bar = new HealthBar(screen.game.assets, bar_x, bar_y, bar_width, bar_height);
		mana_bar = new ManaBar(screen.game.assets, bar_x, bar_y - Gdx.graphics.getHeight() * 0.1f, bar_width, bar_height);
	}

	public void update(float delta)
	{
		
	}
	
	public void draw()
	{
		batch.begin();
			health_bar.draw(batch);
			mana_bar.draw(batch);
		batch.end();
	}

	public void dispose()
	{
		batch.dispose();
	}
}
