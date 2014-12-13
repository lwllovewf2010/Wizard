/* Human shape.
 * Extends the player class and adds body parts in the shape of a human
 * By this, I mean a head at the top. Stands upright. Body below head. One arm for attacking.
 * Extending classes: Wizards, Ranger, Warrior
 */
package com.leepresswood.wizard.player.upperlevel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leepresswood.wizard.Assets;
import com.leepresswood.wizard.screen.ScreenGame;

public abstract class Humanoid extends Player
{
	public Sprite sprite;
	
	private float animation_timer = 0f;
	private final float animation_tick = 1.5f;		//Length of time for one complete stride before looping.
	private int current_animation_counter = 0;
	protected TextureRegion[] left_walk;
	protected TextureRegion[] right_walk;
	
	private boolean is_facing_left = false;
	
	public Humanoid(ScreenGame screen)
	{
		super(screen);
		
		//Grab the spritesheet's frames
		Texture sheet = screen.game.assets.getTexture(Assets.TEXTURE_PERSON_SPRITES);
		TextureRegion[][] temporary = TextureRegion.split(sheet, 52, 81);
		
		//Split them into left and right walking frames.
		left_walk = new TextureRegion[temporary.length];
		right_walk = new TextureRegion[temporary.length];
		
		int left_count = temporary.length - 1;
		int counter = 0;
		int right_count = 0;
		
		while(left_count >= 0)
			left_walk[counter++] = temporary[0][left_count--];
			
		counter = 0;
		while(right_count < temporary.length)
			right_walk[counter++] = temporary[0][right_count++];
		
		//Initialize sprite
		final float width = 0.75f;
		final float height = 1.5f;
		
		sprite = new Sprite(right_walk[0]);
		sprite.setBounds(screen.player_start_point.x, screen.player_start_point.y, width, height);
		
	}

	@Override
	public void update(float delta)
	{
		if(moving_left)
		{
			//Physical translation
			is_facing_left = true;
			sprite.translateX(-1f * delta);
			
			//Spritesheet translation
			animation_timer += delta;
			if(animation_timer >= animation_tick / left_walk.length)
			{
				animation_timer -= animation_tick / left_walk.length;
				sprite.setTexture(left_walk[current_animation_counter++].getTexture());
			}
		}
		else if(moving_right)
		{
			//Physical translation
			is_facing_left = true;
			sprite.translateX(1f * delta);
			
			//Spritesheet translation
			animation_timer += delta;
			if(animation_timer >= animation_tick / left_walk.length)
			{
				animation_timer -= animation_tick / left_walk.length;
				sprite.setTexture(left_walk[current_animation_counter++].getTexture());
			}
		}
		else
		{
			animation_timer = 0f;
			current_animation_counter = 0;
				
			if(is_facing_left)
				sprite.setTexture(left_walk[0].getTexture());
			else
				sprite.setTexture(right_walk[0].getTexture());
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
