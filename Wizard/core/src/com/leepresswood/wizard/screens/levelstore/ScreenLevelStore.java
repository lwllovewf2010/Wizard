package com.leepresswood.wizard.screens.levelstore;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.guielements.GUIButton;
import com.leepresswood.wizard.input.InputLevelStore;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.levelstore.gui.ReturnToGameGUIButton;
import com.leepresswood.wizard.screens.levelstore.gui.SpellLevelUpGUIButton;

/**
 * This is the store that may be used for leveling up between rounds. We'll be lazy here and put everything in this class
 * due to the simplicity of the screen.
 * @author Lee
 */
public class ScreenLevelStore extends ScreenParent
{
	public GameWizard game;
	public ScreenGame game_screen;
	
	public TextureRegion background;
	
	public final int NUMBER_OF_BUTTONS = 2;
	public GUIButton[] button_array;
	
	public int current_spells_available;
	
	public ScreenLevelStore(GameWizard game, ScreenGame game_screen, TextureRegion background)
	{
		super(game);
		
		//We have collected a reference to the previous screen so we can return to it later.
		this.game = game;
		this.game_screen = game_screen;
		
		//We want to draw the frame buffer as the background to the level screen.
		this.background = background;
		
		//Blur this background.
		
		
		//Make elements of the screen.
		current_spells_available = 0;
		makeButtons();
	}
	
	/**
	 * Make the clickable buttons.
	 */
	private void makeButtons()
	{
		button_array = new GUIButton[NUMBER_OF_BUTTONS];
		button_array[0] = new SpellLevelUpGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 100f, 100f, 25f, 25f);
		button_array[1] = new ReturnToGameGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 25f, 25f, 25f, 25f);
	}
	
	@Override
	public Color setUpBackgroundColor()
	{
		return new Color(Color.BLACK);
	}
	
	@Override
	public InputProcessor setUpInput()
	{
		return new InputLevelStore(this);
	}
	
	@Override
	public void update(float delta)
	{
		for(GUIButton b : button_array)
			b.update(delta);
	}
	
	@Override
	public void draw()
	{
		batch.begin();
			//Background.
			//batch.draw(background, 0f, 0f);
			
			for(GUIButton b : button_array)
				b.draw(batch);
		batch.end();
	}
}
