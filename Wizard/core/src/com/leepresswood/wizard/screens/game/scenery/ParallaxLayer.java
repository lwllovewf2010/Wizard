package com.leepresswood.wizard.screens.game.scenery;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.wizard.world.Universe;

/**
 * There will be multiple levels of scenery layers moving in front of or behind the main action of the game.
 * They run in parallel with the stage's main action -- hence the name parallax.
 * While it doesn't contribute to actual gameplay, it does make the game look nicer.
 * @author Lee
 */
public class ParallaxLayer
{
	public Universe universe;
	public float move_percentage;
	public Sprite sprite;
	
	/**
	 * @param move_percentage For every bit of camera motion on the main stage, the ParallaxLayer 
	 * will shift by a percentage of that motion.
	 */
	public ParallaxLayer(Universe universe, float move_percentage, Texture texture)
	{
		this.universe = universe;
		this.move_percentage = move_percentage;
		
		this.sprite = new Sprite(texture);
		sprite.setBounds(-universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2.5f, universe.map_camera_handler.GROUND - 3f, universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL * 2f, universe.map_camera_handler.WORLD_TOTAL_VERTICAL * 0.6f);
	}
	
	/**
	 * This is a ground-level layer. Start drawing from the ground.
	 * @param universe
	 * @param move_percentage
	 * @param texture
	 * @param ground
	 */
	public ParallaxLayer(Universe universe, float move_percentage, Texture texture, float ground)
   {
		this(universe, move_percentage, texture);
		
		sprite.setBounds(0, ground, universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL, universe.map_camera_handler.WORLD_TOTAL_VERTICAL);

   }

	public void update(float delta)
	{//Shift the parallax sprite by the player's movement times the move percentage.
		sprite.translateX(move_percentage * universe.map_camera_handler.dx);
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
}
