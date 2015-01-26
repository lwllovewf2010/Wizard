package com.leepresswood.wizard.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
	
	protected final float PERCENT_SIZE = 0.8f;
	
	/**
	 * This is a game object. It will have a foreground and background. Size of the object will be
	 * read as an attribute of the background. The actual object will take up 80% of the background's
	 * size to assure a thick, black outline.
	 */
	public GameEntity()
	{
		background = new ArrayList<Shape2D>();
		foreground = new ArrayList<Shape2D>();
	}
	
	/**
	 * Render all the shapes. Renders the background shapes first so that the foreground will be on top of it.
	 * 
	 * @param renderer The ShapeRenderer created in the screen class. Draw all shapes from this.
	 */
	public void draw(ShapeRenderer renderer)
	{
		renderShapes(renderer, background);
		renderShapes(renderer, foreground);
	}
	
	/**
	 * Go through every shape in the background or foreground and render it.
	 */
	private void renderShapes(ShapeRenderer renderer, ArrayList<Shape2D> group)
	{
		for(Shape2D s : background)
		{
			if(s instanceof Circle)
				renderer.circle(((Circle) s).x, ((Circle) s).y, ((Circle) s).radius);
		}
	}
	
	public abstract void addShape(float x, float y, Object... other);
	public abstract void update(float delta);
}
