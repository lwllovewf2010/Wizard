package com.leepresswood.wizard.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * Holds information about the physical and graphical aspects of a single game shape. Effectively just two shapes stacked on top of each other.
 * 
 * @author Lee
 */
public class GameShape
{
	protected ArrayList<Shape2D> background, foreground;
	
	protected float x, y, width, height, rotation;
	protected Color color;
	
	protected final float PERCENT_SIZE = 0.85f;
	
	/**
	 * It will have a foreground and background. Size of the shape will be read as an attribute
	 * of the background. The actual shape will take up a percentage of the background's size
	 * to assure a thick, black outline.
	 */
	public GameShape(float x, float y, float width, float height, float rotation, Color color, Shape2D... shapes)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
		this.color = color;
		
		//Make shapes and their outlines out of the passed shapes.
		background = new ArrayList<Shape2D>();
		foreground = new ArrayList<Shape2D>();
		for(Shape2D s : shapes)
		{
			//Original shape is the background.
			background.add(s);
			
			//The other shape should be smaller that the background to cause a black border.
			
		}
	}
	
	/**
	 * Render all the shapes. Renders the background shapes first so that the foreground will be on top of it.
	 * 
	 * @param renderer The ShapeRenderer created in the screen class. Draw all shapes from this.
	 */
	public void draw(ShapeRenderer renderer)
	{
		renderShapes(renderer, background, Color.BLACK);
		renderShapes(renderer, foreground, this.color);
	}

	/**
	 * Go through every shape in the background or foreground and render it.
	 * 
	 * @param renderer The ShapeRenderer from the parent screen class.
	 * @param group The shapes to be rendered.
	 * @param color The color of the rendered shapes.
	 */
	public void renderShapes(ShapeRenderer renderer, ArrayList<Shape2D> group, Color color)
	{
		renderer.setColor(color);
		renderer.rotate(0, 0, 1, this.rotation);
		
		for(Shape2D s : group)
		{
			if(s instanceof Ellipse)
			{
				renderer.ellipse(((Ellipse) s).x, ((Ellipse) s).y, ((Ellipse) s).width, ((Ellipse) s).height);
			}
			else if(s instanceof Rectangle)
			{
				renderer.rect(((Rectangle) s).x, ((Rectangle) s).y, ((Rectangle) s).width, ((Rectangle) s).height);
			}
			else if(s instanceof Polygon)
			{//Triangle.
				float v[] = ((Polyline) s).getVertices();
				renderer.triangle(v[0], v[1], v[2], v[3], v[4], v[5]);
			}
		}
	}
}
