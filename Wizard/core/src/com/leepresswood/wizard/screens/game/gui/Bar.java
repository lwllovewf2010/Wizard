//For use in the health or mana bars.
package com.leepresswood.wizard.screens.game.gui;

public class Bar
{
	public float current_bar_value, max_bar_value;
	public float x, y, width, height;
	public final float MAX_BAR_WIDTH;
	
	public Bar(float x, float y, float width, float height, float max_bar_value)
	{		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//This is the return value for the width. You will come here if current_bar_value = max_bar_value.
		MAX_BAR_WIDTH = width;
		this.max_bar_value = max_bar_value;
		current_bar_value = max_bar_value;
	}
	
	/**
	 * Do math to set the width of the bar after damage/healing.
	 * @return Current health as a decimal percentage of its maximum value.
	 */
	public float getAsPercent()
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
		width =  MAX_BAR_WIDTH * getAsPercent();
	}
	
	/**
	 * Set the max bar value to the given amount. Change current bar value as necessary.
	 * @param value New amount.
	 */
	public void setMaxValue(float value)
	{//We need to update the current value as well as the max bar value. Set current to a percentage of the new value.
		max_bar_value = value;
	}
	
	public void setCurrentValue(float value)
	{
		current_bar_value = value;
	}
	
	public void update(float delta)
	{
		width =  MAX_BAR_WIDTH * getAsPercent();
	}
}
