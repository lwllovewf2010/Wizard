package com.leepresswood.wizard.screens.levelstore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.game.buttons.LevelGUIButton;
import com.leepresswood.wizard.screens.game.gui.GUIButton;

/**
 * This is the store that may be used for leveling up between rounds. We'll be lazy here and put everything in this class
 * due to the simplicity of the screen..
 *
 * @author Lee
 */
public class ScreenLevelStore extends ScreenParent
{
	private GameWizard game;
	private ScreenGame game_screen;
	
	private TextureRegion background;
	
	private final int NUMBER_OF_BUTTONS = 1;
	private GUIButton[] button_array;
	
	public ScreenLevelStore(GameWizard game, ScreenGame game_screen, TextureRegion background)
	{
		super(game);
		
		//We have collected a reference to the previous screen so we can return to it later.
		this.game = game;
		this.game_screen = game_screen;
		
		//We want to draw the frame buffer as the background to the level screen.
		this.background = background;
		
		//Blur this background.
		
		
		//Set up the level-up buttons.
		button_array = new GUIButton[NUMBER_OF_BUTTONS];
		button_array[0] = new LevelGUIButton(this, game.assets.get("textures/hold.png", Texture.class),1f, 1f, 25f, 25f);
	}

	@Override
	public void setUpBackgroundColor()
	{
		color_background = Color.BLACK;
	}

	@Override
	public void setUpInput()
	{
		Gdx.input.setInputProcessor(new InputProcessor()
		{
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button)
			{
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button)
			{
				//game.setScreen(game_screen);
				
				return false;//return true;
			}
			
			@Override
			public boolean scrolled(int amount)
			{
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{
				return false;
			}
			
			@Override
			public boolean keyUp(int keycode)
			{
				return false;
			}
			
			@Override
			public boolean keyTyped(char character)
			{
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode)
			{
				return false;
			}
		});
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
			for(GUIButton b : button_array)
				b.draw(batch);
		
			//Background.
			batch.draw(background, 0f, 0f);
		batch.end();
	}
}
