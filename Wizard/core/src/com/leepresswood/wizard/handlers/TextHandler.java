package com.leepresswood.wizard.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles the creation, movement, and deletion of on-screen text.
 * @author Lee
 */
public class TextHandler
{
	private final float TIME_TO_INVISIBLE = 1.0f;
	private final float TEXT_DEATH_SPEED = 2f / TIME_TO_INVISIBLE;
	
	private BitmapFont in_game_text;
	
	//Each piece of test will be on the screen for a certain period of time.
	private ArrayList<Float> time_max;
	private ArrayList<Float> time_current;
	private ArrayList<Float> alpha;
	private ArrayList<Boolean> fade;
	private ArrayList<String> strings;
	private ArrayList<Vector2> position;
	private ArrayList<Color> colors;
	
	public TextHandler()
	{
		in_game_text = new BitmapFont();
		
		time_max = new ArrayList<Float>();
		time_current = new ArrayList<Float>();
		alpha = new ArrayList<Float>();
		fade = new ArrayList<Boolean>();
		strings = new ArrayList<String>();
		position = new ArrayList<Vector2>();
		colors = new ArrayList<Color>();
	}
	
	/**
	 * This is text that appears in a single location. It is static.
	 * @param text Text to write.
	 * @param time_to_live How long it should be on the screen.
	 * @param x X location of left side of text.
	 * @param y X location of text.
	 */
	public void createText(String text, float time_to_live, float x, float y, Color color)
	{//Create a new text instance.
		time_max.add(time_to_live);
		time_current.add(0f);
		alpha.add(1f);
		fade.add(false);
		strings.add(text);
		position.add(new Vector2(x, y));
		colors.add(color);
	}
	
	/**
	 * This is the text that spawns after an item pickup or after an enemy is killed. It should
	 * float upward and tween toward transparency.
	 * @param text Text to write.
	 * @param x X location of left side of text.
	 * @param y X location of text.
	 */
	public void createDecayText(String text, float x, float y, Color color)
	{//Create a new text instance.
		time_max.add(TIME_TO_INVISIBLE);
		time_current.add(0f);
		alpha.add(1f);
		fade.add(true);
		strings.add(text);
		position.add(new Vector2(x, y));
		colors.add(color);
	}
	
	public void update(float delta)
	{//Loop through all the ArrayLists and update.
		for(int i = 0; i < time_max.size(); i++)
		{
			//Set new alpha.
			time_current.set(i, time_current.get(i).floatValue() + delta);
			if(fade.get(i))
			{
				alpha.set(i, 1f - time_current.get(i) / time_max.get(i));
				if(alpha.get(i) < 0f)
				{
					alpha.set(i, 0f);
				}
				
				//Move the text upward.
				position.get(i).y += TEXT_DEATH_SPEED * delta;
			}
		}
	}
	
	public void draw(SpriteBatch batch)
	{//Loop through the ArrayList and draw.
		for(int i = 0; i < time_max.size(); i++)
		{
			in_game_text.setColor(colors.get(i));
			in_game_text.draw(batch, strings.get(i), position.get(i).x, position.get(i).y);
			
			//Remove old items.
			if(time_current.get(i) >= time_max.get(i))
			{
				time_max.remove(i);
				time_current.remove(i);
				alpha.remove(i);
				fade.remove(i);
				strings.remove(i);
				position.remove(i);
			}
		}
	}
}
