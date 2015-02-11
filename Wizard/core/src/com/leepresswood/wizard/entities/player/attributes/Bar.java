//For use in the health or mana bars.
package com.leepresswood.wizard.entities.player.attributes;

public class Bar
{	
	public float current_bar_value, max_bar_value;	
	public float x, y, width, height;
	
	public float recovery_amount;
	private final float MAX_BAR_WIDTH;	
	
	public Bar(float x, float y, float width, float height, float recovery_amount)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//This is the return value for the width. You will come here if current_bar_value = max_bar_value.
		MAX_BAR_WIDTH = width;
		
		max_bar_value = 100f;
		current_bar_value = max_bar_value;
		
		this.recovery_amount = recovery_amount;
	}
	
	/**
	 * Deals with recovering over time
	 * @param delta Change in time.
	 */
	public void updateOverTime(float delta)
	{
		change(recovery_amount * delta);
	}
	
	/**
	 * Do math to set the width of the bar after damage/healing.
	 * @return Current health as a decimal percentage of its maximum value.
	 */
	private float getHealthAsPercent()
	{
		return current_bar_value / max_bar_value;
	}
		
	/**
	 * Change the bar's value.
	 * @param amount Positive is an increase; negative is a decrease.
	 */
	public void change(float amount)
	{
		current_bar_value = current_bar_value + amount > max_bar_value ? max_bar_value : current_bar_value + amount;
		width =  MAX_BAR_WIDTH * getHealthAsPercent();
	}
}
