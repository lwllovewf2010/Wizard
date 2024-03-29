package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leepresswood.wizard.GameWizard;
import com.leepresswood.wizard.input.InputGame;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.gui.GUIGame;
import com.leepresswood.wizard.screens.levelstore.ScreenLevelStore;
import com.leepresswood.wizard.world.Universe;

public class ScreenGame extends ScreenParent
{
	public Universe universe;
	public GUIGame gui;	
	
	public boolean go_to_level_store;							//Flag is set when the player requested to visit the level store.
	public ScreenLevelStore screen_level_store;				//Reference to the level store.
	
	public ScreenGame(GameWizard game)
	{
		super(game);
		
		//The game screen has a world in the background and a GUI in the foreground.
		universe = new Universe(this);
		gui = new GUIGame(this);
	}

	@Override
	public Color setUpBackgroundColor()
	{
		return new Color(Color.CYAN);
	}
	
	@Override
	public InputProcessor setUpInput()
	{
		//Gdx.input.setCursorImage(new Pixmap(Gdx.files.internal("person/textures/hold.png")), 0, 0);
		return new InputGame(this);
	}

	@Override
	public void update(float delta)
	{
		//Determine if we have to go to the level store. If so, replace the current screen with the shop screen. Pauses the game in the process.
		if(go_to_level_store)
		{//First time we visit the store will require loading.
			if(screen_level_store == null)
				screen_level_store = new ScreenLevelStore(this, ScreenUtils.getFrameBufferTexture());
			game.setScreen(screen_level_store);
			go_to_level_store = false;
		}
		else
		{
			universe.update(delta);		
			gui.update(delta);
		}
	}
	
	@Override
	public void draw()
	{
		universe.draw();
		gui.draw();
	}
	
	@Override
	public void show()
	{
	   super.show();
	   
	   //Upon returning from the level store, recalculate the spell packages.
	   if(screen_level_store != null)
	   {
	   	universe.level_handler.recalculate();
	   	gui.makeSpellList();
	   }
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		batch.dispose();
		renderer.dispose();
		
		universe.world_handler.dispose();
	}
}
