/* Human shape.
 * Extends the player class and adds body parts in the shape of a human
 * By this, I mean a head at the top. Stands upright. Body below head. One arm for attacking.
 * Extending classes: Wizards, Ranger, Warrior
 */
package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.player.bodyparts.Body;
import com.leepresswood.wizard.player.bodyparts.Foot;
import com.leepresswood.wizard.player.bodyparts.Hand;
import com.leepresswood.wizard.player.bodyparts.Head;
import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Humanoid extends Player
{
	public Head head;
	public Body body;
	public Hand hand;
	public Foot foot;
	
	public Humanoid(ScreenGame screen)
	{
		super(screen);
		final float width = 0.75f;
		final float height = 2.25f;
		
		final float head_width = width;
		final float head_height = head_width;
		final float head_start_y = screen.start_point.y + height - head_height;		
		head = new Head(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		head.setBounds(screen.start_point.x + width / 2f - head_width / 2f, screen.start_point.y + head_start_y, head_width, head_height);
		
		final float body_width = width;
		final float body_height = (height - head_width) * 0.9f;
		final float body_start_y = head_start_y - body_height;
		body = new Body(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(screen.start_point.x, screen.start_point.y, body_width, body_height);
		
		hand = new Hand(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(screen.start_point.x + width / 2f - head_width / 2f, screen.start_point.y + head_start_y, head_width, head_height);
		
		foot = new Foot(screen.game.assets.getTexture(Assets.TEXTURE_CIRCLE));
		body.setBounds(screen.start_point.x + width / 2f - head_width / 2f, screen.start_point.y + head_start_y, head_width, head_height);
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		foot.draw(batch);
		body.draw(batch);
		hand.draw(batch);
		head.draw(batch);
	}
}
