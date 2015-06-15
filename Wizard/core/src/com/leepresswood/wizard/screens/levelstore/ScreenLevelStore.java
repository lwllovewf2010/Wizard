package com.leepresswood.wizard.screens.levelstore;

import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.ScreenGame;
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
	
	private GUIButton[] button_array;
	
	public ScreenLevelStore(GameWizard game, ScreenGame game_screen)
	{
		super(game);
		
		//We have collected a reference to the previous scree so that we can return to it later.
		this.game = game;
		this.game_screen = game_screen;
	}

	@Override
	public void setUpBackgroundColor()
	{
	}

	@Override
	public void setUpInput()
	{
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void draw()
	{
		
	}
}
