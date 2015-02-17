package com.leepresswood.wizard.entities.spells.damage;

import com.badlogic.gdx.math.Vector2;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.spells.BoltSpell;
import com.leepresswood.wizard.screens.game.ScreenGame;

public class Ether extends BoltSpell
{	
	public Ether(ScreenGame screen, Vector2 from, Vector2 to)
	{
		super(screen, from, to);
		MAX_TIME = 5f;
	}
	
	@Override
	protected void makeSpriteTexture()
	{
		sprite.setTexture(screen.game.assets.getTexture(Assets.TEXTURE_HOLD));
	}

	@Override
	protected void setSpeedMax()
	{
		SPEED_MAX = 8f;
	}
	
	@Override
	protected void updatePosition(float delta)
	{
		//Ether doesn't change direction.		
		//Simply move in the direction.
		sprite.translate(speed_x * delta, speed_y * delta);
	}

	@Override
	protected void updateCollision()
	{
		//Ether can hit multiple targets, so no real collision is necessary. Time will make it disappear.
	}
}
