package com.leepresswood.wizard.screens.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class GUIButton
{
	public ScreenGame screen;
	
	public Sprite sprite;
	public boolean is_active;
	
	public GUIButton(ScreenGame screen, Texture t, float x, float y, float width, float height)
	{
		this.screen = screen;
		
		sprite = new Sprite(t);
		sprite.setBounds(x, y, width, height);
		
		is_active = true;
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
