//For use in the health or mana bars.
package com.leepresswood.wizard.world.attributes;

public class Bar
{	
	public float current_bar_value, max_bar_value;	
	public float x, y, width, height;
	
	public float recovery_amount;
	private final float MAX_BAR_WIDTH;	
	
	public Bar(float x, float y, float width, float height, float max_bar_value, float recovery_amount)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.recovery_amount = recovery_amount;
		
		//This is the return value for the width. You will come here if current_bar_value = max_bar_value.
		MAX_BAR_WIDTH = width;
		this.max_bar_value = max_bar_value;
		current_bar_value = max_bar_value;
	}
	
	/**
	 * Set the max bar value to the given amount. Change current bar value as necessary.
	 * @param amount New amount.
	 */
	public void setMaxValue(float amount)
	{
		//We need to update the current value as well as the max bar value. Set current to a percentage of the new value.
		float new_percent = getHealthAsPercent();
		max_bar_value = amount;
		current_bar_value = new_percent * amount;
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
