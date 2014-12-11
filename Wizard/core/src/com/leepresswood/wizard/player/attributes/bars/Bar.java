//For use in the health or mana bars.
package com.leepresswood.wizard.player.attributes.bars;

public class Bar
{	
	public float current_value;
	public float max_value;
	
	public float x;
	public float y; 
	public float width; 
	public float height;
	private float max_sprite_width;
	
	public Bar(float x, float y, float width, float height)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		max_sprite_width = width;
		
		max_value = 100f;
		current_value = max_value;
	}
	
	public float getHealthAsPercent()
	{//Returns the current health as a percentage of its maximum value.
		return current_value / max_value;
	}
		
	public void change(float amount)
	{//Change the bar's value by "amount". Positive is an increase, and negative is a decrease.
		current_value = current_value + amount > max_value ? max_value : current_value + amount;
		width = max_sprite_width * getHealthAsPercent();
	}
}
