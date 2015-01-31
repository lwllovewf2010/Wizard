package com.leepresswood.wizard.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Holds information about the physical and graphical aspects of a single game entity. Effectively just two shapes stacked on top of each other.
 * 
 * @author Lee
 *
 */
public abstract class GameEntity
{
	protected ArrayList<Shape2D> background, foreground;
	
	protected float center_x, center_y, width, height;
	
	protected Color color;
	protected float rotation;
	
	protected final float PERCENT_SIZE = 0.8f;
	
	/**
	 * It will have a foreground and background. Size of the entity will be
	 * read as an attribute of the background. The actual entity will take up 80% of the background's
	 * size to assure a thick, black outline.
	 */
	public GameEntity(float x, float y, float width, float height)
	{
		background = new ArrayList<Shape2D>();
		foreground = new ArrayList<Shape2D>();
		
		center_x = x;
		center_y = y;
		this.width = width;
		this.height = height;
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
		for(Shape2D s : group)
		{
			if(s instanceof Ellipse)
				renderer.ellipse(((Ellipse) s).x, ((Ellipse) s).y, ((Ellipse) s).width, ((Ellipse) s).height);
			else if(s instanceof Rectangle)
			{
				Vector2 center = ((Rectangle) s).getCenter(new Vector2());
				renderer.rect(((Rectangle) s).x, ((Rectangle) s).y, center.x, center.y, ((Rectangle) s).width, ((Rectangle) s).height, 1f, 1f, rotation, color, color, color, color);
			}
			else if(s instanceof Polygon)
			{//Triangle.
				float v[] = ((Polyline) s).getVertices();
				renderer.triangle(v[0], v[1], v[2], v[3], v[4], v[5], color, color, color);
			}
		}
	}
	
	public abstract void setColor();
	public abstract void makeShapes();
	public abstract void update(float delta);
}
