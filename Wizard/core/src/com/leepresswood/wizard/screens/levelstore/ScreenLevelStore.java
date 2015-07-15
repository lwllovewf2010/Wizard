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
	public ScreenGame game_screen;
	
	public TextureRegion background;
	
	public final int NUMBER_OF_BUTTONS = 2;
	public GUIButton[] button_array;
	
	private final int MAX_SPELLS_AVAILABLE = 5;
	
	public ScreenLevelStore(GameWizard game, ScreenGame game_screen, TextureRegion background)
	{
		super(game);
		
		//We have collected a reference to the previous screen so we can return to it later.
		this.game_screen = game_screen;
		
		//We want to draw the frame buffer as the background to the level screen.
		this.background = background;
		
		//Blur this background.
		
		
		//Make elements of the screen.
		makeButtons();
	}
	
	/**
	 * Make the clickable buttons.
	 */
	private void makeButtons()
	{
		button_array = new GUIButton[NUMBER_OF_BUTTONS];
		
		//Attribute level-up buttons.
		button_array[0] = new SpellLevelUpGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 100f, 25f, 25f, 25f);
		button_array[1] = new ReturnToGameGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 25f, 25f, 25f, 25f);
		
		//Spell level-up buttons.
		
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

	/**
	 * Level up of number of spells was requested. Increase the number and spend a point.
	 */
	public void levelUpSpells()
   {
		//Increase the number. Check the bounds. If it hit the max, disable the number level up button.
		if(++game_screen.world.level_handler.spells_available >= MAX_SPELLS_AVAILABLE)
		{
			game_screen.world.level_handler.spells_available = MAX_SPELLS_AVAILABLE;
			button_array[0].is_active = false;
		}
		else
		{
			game_screen.world.level_handler.points_spent++;
		}
   }
}
