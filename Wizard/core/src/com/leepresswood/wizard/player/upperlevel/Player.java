package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.player.bodyparts.Body;
import com.leepresswood.wizard.player.bodyparts.Foot;
import com.leepresswood.wizard.player.bodyparts.Hand;
import com.leepresswood.wizard.player.bodyparts.Head;

public abstract class Player
{
	public Head head;
	public Body body;
	public Hand hand;
	public Foot foot;
	
	public Player(Assets assets, float x, float y)
	{
		final float width = 0.75f;
		final float height = 2.25f;
		
		final float head_width = width;
		final float head_height = head_width;
		final float head_start_y = y + height - head_height;		
		head = new Head(assets.getTexture(Assets.TEXTURE_CIRCLE));
		head.setBounds(x + width / 2f - head_width / 2f, y + head_start_y, head_width, head_height);
		
		final float body_width = width;
		final float body_height = (height - head_width) * 0.9f;
		final float body_start_y = head_start_y - body_height;
		body = new Body(assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(x, y, body_width, body_height);
		System.out.println(x);
		hand = new Hand(assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(x + width / 2f - head_width / 2f, y + head_start_y, head_width, head_height);
		
		foot = new Foot(assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(x + width / 2f - head_width / 2f, y + head_start_y, head_width, head_height);
	}
	
	public void update(float delta)
	{
		
	}

	public void draw(SpriteBatch batch)
	{
		foot.draw(batch);
		body.draw(batch);
		hand.draw(batch);
		head.draw(batch);
	}
}
