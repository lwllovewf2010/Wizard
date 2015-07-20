package com.leepresswood.wizard.screens.game.scenery;

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
	
	/**
	 * @param move_percentage For every bit of camera motion on the main stage, the ParallaxLayer 
	 * will shift by a percentage of that motion.
	 */
	public ParallaxLayer(Universe universe, float move_percentage)
	{
		this.universe = universe;
		this.move_percentage = move_percentage;
	}
}
