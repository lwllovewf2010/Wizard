package com.leepresswood.wizard.screens.levelstore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.handlers.LevelHandler;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.input.InputLevelStore;
import com.leepresswood.wizard.screens.ScreenParent;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.screens.levelstore.gui.LevelUpSpellButton;
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
	
	//This background will be blurred.
	public TextureRegion background;
	
	//There will be buttons that are used to level up attributes.
	public GUIButton[] button_array;
	private final int NUMBER_OF_BUTTONS = 7;
	private final int BUTTON_RETURN = 0;
	private final int BUTTON_SPELL_NUMBER = 1;
	private final int BUTTON_SKILL_ONE = 2;
	private final int BUTTON_SKILL_TWO = 3;
	private final int BUTTON_SKILL_THREE = 4;
	private final int BUTTON_SKILL_FOUR = 5;
	private final int BUTTON_SKILL_FIVE = 6;
	
	public GUIButton[] attack_buttons;
	public GUIButton[] attack_effects_buttons;
	public GUIButton[] defense_buttons;
	public GUIButton[] defense_effects_buttons;
	public GUIButton[] utility_buttons;
	
	public ScreenLevelStore(ScreenGame game_screen, TextureRegion background)
	{
		super(game_screen.game);
		
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
		button_array[BUTTON_RETURN] = new ReturnToGameGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 25f, 25f, 25f, 25f);
		button_array[BUTTON_SPELL_NUMBER] = new SpellLevelUpGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 100f, 25f, 25f, 25f);
		
		//Spell level-up buttons.
		button_array[BUTTON_SKILL_ONE] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), 25f, 100f, 25f, 25f, 0);
		button_array[BUTTON_SKILL_TWO] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), 51f, 100f, 25f, 25f, 1);
		button_array[BUTTON_SKILL_THREE] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), 77f, 100f, 25f, 25f, 2);
		button_array[BUTTON_SKILL_FOUR] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), 103f, 100f, 25f, 25f, 3);
		button_array[BUTTON_SKILL_FIVE] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), 129f, 100f, 25f, 25f, 4);
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
		//Background.
		batch.begin();
			batch.draw(background, 0f, 0f);
		batch.end();
		
		//Background box.
		renderer.begin(ShapeType.Filled);
			renderer.setColor(Color.RED);
			renderer.rect(10f, 10f, Gdx.graphics.getWidth() - 20f, Gdx.graphics.getHeight() - 20f);
		renderer.end();
		
		//Buttons.
		batch.begin();
			for(GUIButton b : button_array)
				b.draw(batch);
		batch.end();
	}
	
	/**
	 * To save the code from ugly dereferences, use this method.
	 * @return Reference to the level handler used in the universe.
	 */
	private LevelHandler getLevelHandler()
	{
		return game_screen.universe.level_handler;
	}

	/**
	 * Level up of number of spells was requested. Increase the number and spend a point.
	 */
	public void levelUpSpellNumber()
   {//Increase the number. Check the bounds. If it hit the max, disable the level up button.
		if(getLevelHandler().canSpend())
		{
			if(++getLevelHandler().spells_available >= getLevelHandler().SPELLS_NUMBER_MAX)
			{
				getLevelHandler().spells_available = getLevelHandler().SPELLS_NUMBER_MAX;
				button_array[BUTTON_SPELL_NUMBER].is_active = false;
			}
			else
			{//Spend a point and activate the corresponding button.
				getLevelHandler().spend();
				button_array[getLevelHandler().spells_available + BUTTON_SKILL_ONE - 1].is_active = true;
			}
		}
   }

	/**
	 * Level up the designated spell.
	 * @param spell_number The spell to level up.
	 */
	public void levelUpSpell(int spell_number)
   {//Increase the number. Check the bounds. If it hit the max, disable the level up button.
		if(getLevelHandler().canSpend())
		{
			if(++getLevelHandler().spell_levels[spell_number] >= getLevelHandler().SPELL_LEVEL_MAX)
			{
				getLevelHandler().spell_levels[spell_number] = getLevelHandler().SPELL_LEVEL_MAX;
				button_array[spell_number + BUTTON_SKILL_ONE].is_active = false;		//Index is shifted to correct for the spell level up buttons starting at an arbitrary point.
			}
			else
			{
				getLevelHandler().spend();
			}
		}
   }
}
