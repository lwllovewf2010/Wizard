package com.leepresswood.wizard.gui.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.screens.game.ScreenGame;

public abstract class GUIButton
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
	
	public void draw(SpriteBatch batch)
	{
		if(is_active)
			sprite.draw(batch);
	}
	
	public abstract void update(float delta);
	public abstract void doClick();
}
