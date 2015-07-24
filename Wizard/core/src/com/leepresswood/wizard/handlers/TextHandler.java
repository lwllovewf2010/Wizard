package com.leepresswood.wizard.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Handles the creation, movement, and deletion of on-screen text.
 * @author Lee
 */
public class TextHandler
{
	private final float TIME_TO_INVISIBLE = 0.75f;
	private final float TEXT_DEATH_SPEED = 2f / TIME_TO_INVISIBLE;
	
	private BitmapFont in_game_text;
	
	//Each piece of test will be on the screen for a certain period of time.
	private ArrayList<Float> time_to_live;
	private ArrayList<Float> time_current;
	private ArrayList<Float> alpha;
	private ArrayList<String> strings;
	
	public TextHandler()
	{
		in_game_text = new BitmapFont();
		
		time_to_live = new ArrayList<Float>();
		alpha = new ArrayList<Float>();
		strings = new ArrayList<String>();
	}
	
	/**
	 * This is text that appears in a single location. It is static.
	 * @param text Text to write.
	 * @param time_to_live How long it should be on the screen.
	 * @param x X location of left side of text.
	 * @param y X location of text.
	 */
	public void createText(String text, float time_to_live, float x, float y)
	{
		
	}
	
	/**
	 * This is the text that spawns after an item pickup or after an enemy is killed. It should
	 * float upward and tween toward transparency.
	 * @param text Text to write.
	 * @param x X location of left side of text.
	 * @param y X location of text.
	 */
	public void createDecayText(String text, float x, float y)
	{
		
	}
	
	public void update(float delta)
	{
		
	}
	
	public void draw(SpriteBatch batch)
	{
		
	}
}
