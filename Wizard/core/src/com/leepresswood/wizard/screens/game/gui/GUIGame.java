package com.leepresswood.wizard.screens.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.helpers.handlers.LevelHandler;
import com.leepresswood.wizard.screens.game.ScreenGame;

/**
 * The GUI for the game.
 * @author Lee
 */
public class GUIGame
{
	private ScreenGame screen;
	public OrthographicCamera camera;
	
	//Buttons
	public final int MAX_BUTTONS = 1;
	public GUIButton[] button_array;
	
	//Health/Mana Bars
	public Bar bar_health, bar_mana, bar_experience;
	public Color color_health, color_mana, color_experience;
	
	//Spells
	public int spell_active = 0;
	public boolean[] spell_shader;
	public Sprite[] spell_sprites;
	
	//Position outline.
	private Vector3 mouse_position;
	
	public GUIGame(ScreenGame screen)
	{
		this.screen = screen;
		
		mouse_position = new Vector3();
		spell_shader = new boolean[LevelHandler.NUMBER_OF_SPELLS];
		spell_sprites = new Sprite[LevelHandler.NUMBER_OF_SPELLS];
		
		makeCamera();
		makeStatusBars();
		makeSpellList();
		makeButtons();
	}
	
	/**
	 * Set up camera. It will never move, so no need to update it again after initialization.
	 */
	private void makeCamera()
	{
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
	}
	
	/**
	 * Create the health and magic bars.
	 */
	private void makeStatusBars()
	{
		final float gap = Gdx.graphics.getWidth() * 0.002f;
		final float bar_width = Gdx.graphics.getWidth() * 0.01f;
		final float bar_height = Gdx.graphics.getHeight() * 0.2f;
		final float bar_x = gap;
		final float bar_y = gap;
		
		//Set bars.
		bar_health = new Bar(bar_x, bar_y, bar_width, bar_height);
		bar_mana = new Bar(bar_x + bar_width + gap, bar_y, bar_width, bar_height);
		bar_experience = new Bar(bar_x + 2 * (bar_width + gap), bar_y, bar_width, bar_height);
		
		//Set colors.
		color_health = new Color(Color.valueOf("AA3C39FF"));
		color_mana = new Color(Color.valueOf("2E4372FF"));
		color_experience = new Color(Color.valueOf("FFFF00FF"));
	}
	
	/**
	 * Create the spell list.
	 */
	public void makeSpellList()
	{//From the level handler, determine the look of the spells.
		final float gap = Gdx.graphics.getWidth() * 0.002f;
		final float width = Gdx.graphics.getWidth() * 0.03f;
		final float height = width;
		
		for(int i = 0; i < LevelHandler.NUMBER_OF_SPELLS; i++)
		{//If greater than 0, we've leveled this spell.
			final float bar_x = i * (width + gap);
			final float bar_y = Gdx.graphics.getHeight() - gap - height;
			
			if(screen.universe.level_handler.castable_spells[i].level > 0)
			{//Available.
				spell_shader[i] = false;
			}
			else
			{//Not available.
				spell_shader[i] = true;
			}
			
			//Grab the sprite of the spell.
			spell_sprites[i] = new Sprite(screen.game.assets.get("textures/hold.png", Texture.class));
			spell_sprites[i].setBounds(bar_x, bar_y, width, height);
		}
	}
	
	/**
	 * Make the clickable buttons.
	 */
	private void makeButtons()
	{
		button_array = new GUIButton[MAX_BUTTONS];
		button_array[0] = new LevelGUIButton(screen, Gdx.graphics.getWidth() - 40f, Gdx.graphics.getHeight() - 55f, 35f, 50f);
	}
	
	/**
	 * Timed update. This will be used in any animations and possibly a game clock with a day/night system.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		for(GUIButton b : button_array)
			b.update(delta);
		
		//Text is handled by the GUI.
		screen.universe.text_handler.update(delta);
	}
	
	/**
	 * Draw all parts of the GUI.
	 */
	public void draw()
	{
		//Mouse position outline.
		mouse_position = screen.universe.map_camera_handler.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
		screen.renderer.setProjectionMatrix(screen.universe.map_camera_handler.combined);
		screen.renderer.setColor(Color.WHITE);
		screen.renderer.begin(ShapeType.Line);
			screen.renderer.identity();
			screen.renderer.rect((int) mouse_position.x, (int) mouse_position.y, 1f, 1f);
		screen.renderer.end();
		screen.renderer.setProjectionMatrix(camera.combined);
		
		//Buttons.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			for(GUIButton b : button_array)
				b.draw(screen.batch);
			
			for(int i = 0; i < LevelHandler.NUMBER_OF_SPELLS; i++)
				spell_sprites[i].draw(screen.batch);
		screen.batch.end();
		
		//Health/Mana bars.		
		//Create the actual bars.
		screen.renderer.begin(ShapeType.Filled);
			screen.renderer.identity();
			screen.renderer.rect(bar_health.x, bar_health.y, bar_health.width, bar_health.MAX_BAR_HEIGHT * screen.universe.entity_handler.player.health_current / screen.universe.entity_handler.player.health_max, color_health, color_health, color_health, color_health);
			screen.renderer.rect(bar_mana.x, bar_mana.y, bar_health.width, bar_mana.MAX_BAR_HEIGHT * screen.universe.entity_handler.player.mana_current / screen.universe.entity_handler.player.mana_max, color_mana, color_mana, color_mana, color_mana);
			screen.renderer.rect(bar_experience.x, bar_experience.y, bar_experience.width, bar_experience.MAX_BAR_HEIGHT * screen.universe.level_handler.getExperienceAsPercentOfLevel(), color_experience, color_experience, color_experience, color_experience);
		screen.renderer.end();
		
		//Create rectangles around the bars.
		screen.renderer.begin(ShapeType.Line);
			screen.renderer.identity();		
			screen.renderer.rect(bar_health.x, bar_health.y, bar_health.width, bar_health.MAX_BAR_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
			screen.renderer.rect(bar_mana.x, bar_mana.y, bar_mana.width, bar_mana.MAX_BAR_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
			screen.renderer.rect(bar_experience.x, bar_experience.y, bar_experience.width, bar_experience.MAX_BAR_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
		screen.renderer.end();
		
		//Spell overlays.
		
		
		//Spell outlines.
		screen.renderer.begin(ShapeType.Line);
			screen.renderer.identity();
			for(int i = 0; i < LevelHandler.NUMBER_OF_SPELLS; i++)
				if(i == spell_active)
					screen.renderer.rect(spell_sprites[i].getX() - 1, spell_sprites[i].getY() + 1, spell_sprites[i].getWidth() + 1, spell_sprites[i].getHeight() + 1, Color.RED, Color.RED, Color.RED, Color.RED);
				else
					screen.renderer.rect(spell_sprites[i].getX() - 1, spell_sprites[i].getY() + 1, spell_sprites[i].getWidth() + 1, spell_sprites[i].getHeight() + 1, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);					
		screen.renderer.end();
		
		//Text is handled by the GUI.
		screen.universe.text_handler.draw(screen.batch);
	}
	
	/**
	 * Player requested a switch to a particular spell in the spell list.
	 * @param count The number requested from the spell list.
	 */
	public void shiftSpellTo(int count)
	{
		spell_active = count;
		shiftSpellLeft();
	}

	/**
	 * Player scrolled the mouse wheel. Change the active spell.
	 * @param amount Amount scrolled. Negative scrolls left; positive scrolls right.
	 */
	public void changeSpell(int amount)
	{
		if(amount == 1)
		{
			spell_active--;
			shiftSpellLeft();
		}
		else
		{
			spell_active++;
			shiftSpellRight();
		}
	}
	
	/**
	 * Correct the selected spell after a shift of the active spell left by one.
	 */
	private void shiftSpellLeft()
	{
		if(spell_active < 0)
			spell_active = LevelHandler.NUMBER_OF_SPELLS - 1;
	}
	
	/**
	 * Correct the selected spell after a shift of the active spell right by one.
	 */
	private void shiftSpellRight()
	{
		if(spell_active == LevelHandler.NUMBER_OF_SPELLS)
			spell_active = 0;
	}
	
	/**
	 * Get the active spell.
	 * @return The chosen spell's index.
	 */
	public int getActiveSpell()
	{
		return spell_active;
	}
}
