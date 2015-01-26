package com.leepresswood.wizard.entities;

import java.util.ArrayList;
import com.badlogic.gdx.math.Shape2D;

/**
 * Holds information about the physical and graphical aspects of the game objects. Effectively just two shapes stacked on top of each other.
 * 
 * @author Lee
 *
 */
public abstract class GameEntity
{
	protected ArrayList<Shape2D> background;
	protected ArrayList<Shape2D> foreground;
	
	public GameEntity()
	{
		
	}
}
