package com.leepresswood.wizard.player.attributes;

import com.badlogic.gdx.graphics.Color;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.player.attributes.bars.Bar;


public class HealthBar extends Bar
{
	public HealthBar(Assets assets, float x, float y, float width, float height)
	{//Only difference will be the location on the screen and the color
		super(assets, x, y, width, height);
		sprite.setColor(Color.RED);
	}
}
