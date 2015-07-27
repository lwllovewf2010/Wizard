package com.leepresswood.wizard.screens.game.gui;

/**
 * For use in the health or mana bars.
 * @author Lee
 */
public class Bar
{
	public float x, y, width, height;
	public final float MAX_BAR_HEIGHT;
	
	public Bar(float x, float y, float width, float height)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		MAX_BAR_HEIGHT = height;
	}
}
