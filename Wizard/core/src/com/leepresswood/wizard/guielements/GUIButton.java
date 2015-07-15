package com.leepresswood.wizard.guielements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.screens.ScreenParent;

public abstract class GUIButton
{
	public ScreenParent screen;
	
	public Sprite sprite;
	public boolean is_active;
	
	//Colors for disabling the buttons.
	private Color color_batch;
	private Color color_active;
	
	public GUIButton(ScreenParent screen, Texture t, float x, float y, float width, float height)
	{
		this.screen = screen;
		
		sprite = new Sprite(t);
		sprite.setBounds(x, y, width, height);
		
		is_active = true;
		color_active = new Color(Color.BLUE);
	}
	
	public void update(float delta)
	{
	}
	
	public void draw(SpriteBatch batch)
	{
		//Grab the color of the batch as a return color.
		if(color_batch == null)
		{
			color_batch = batch.getColor();
		}
		
		//If active, draw normally.
		if(is_active)
		{
			sprite.draw(batch);
		}
		//Otherwise, change the batch color to the disabled color and draw.
		else
		{
			batch.setColor(color_active);
			sprite.draw(batch);
			batch.setColor(color_batch);
		}
	}
	
	public abstract void doClick();
}
