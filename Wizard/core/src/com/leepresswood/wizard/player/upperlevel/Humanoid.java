/* Human shape.
 * Extends the player class and adds body parts in the shape of a human
 * By this, I mean a head at the top. Stands upright. Body below head. One arm for attacking.
 * Extending classes: Wizards, Ranger, Warrior
 */
package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.player.bodyparts.Body;
import com.leepresswood.wizard.player.bodyparts.Hand;
import com.leepresswood.wizard.player.bodyparts.Head;
import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Humanoid extends Player
{
	public Head head;
	public Body body;
	public Hand hand;
	
	public Humanoid(ScreenGame screen)
	{
		super(screen);
		final float width = 0.75f;
		final float height = 1.5f;
		
		final float head_width = width;
		final float head_height = head_width;
		final float head_start_y = screen.player_start_point.y + height - head_height;		
		head = new Head(screen.game.assets.getTexture(Assets.TEXTURE_FACE));
		head.setBounds(screen.player_start_point.x, head_start_y, head_width, head_height);
		
		final float body_width = width;
		final float body_height = height - head_width;
		body = new Body(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(screen.player_start_point.x, screen.player_start_point.y, body_width, body_height);
		
		final float hand_size = body_height * 0.25f;
		hand = new Hand(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		hand.setBounds(screen.player_start_point.x + width / 2f - hand_size / 2f, screen.player_start_point.y + height / 2f - hand_size, hand_size, hand_size);
	}

	@Override
	public void update(float delta)
	{
		if(moving_left)
		{
			head.translateX(-1f * delta);
		}
		if(moving_right)
		{
			head.translateX(1f * delta);
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		body.draw(batch);
		hand.draw(batch);
		head.draw(batch);
	}
}
