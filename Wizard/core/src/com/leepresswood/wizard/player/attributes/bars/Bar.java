//For use in the health or mana bars.
package com.leepresswood.wizard.player.attributes.bars;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.Assets;

public class Bar
{
	protected Sprite sprite;
	private float max_sprite_width;

	public float current_value;
	public float max_value;
	
	public Bar(Assets assets, float x, float y, float width, float height)
	{
		sprite = new Sprite(assets.getTexture(Assets.TEXTURE_CIRCLE));
		sprite.setBounds(x, y, width, height);
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
		sprite.setSize(max_sprite_width * getHealthAsPercent(), sprite.getHeight());
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
