package com.leepresswood.wizard.screens.levelstore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.helpers.enums.AttackLevel;
import com.leepresswood.wizard.helpers.enums.AttackType;
import com.leepresswood.wizard.helpers.guielements.GUIButton;
import com.leepresswood.wizard.helpers.handlers.LevelHandler;
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
	
	//There will be buttons that are used to level up attributes. These are the attributes.
	public final int COST_PER_LEVEL = 2;
	public final int ULTIMATE_COST_PER_LEVEL = 5;
	public final int NUMBER_LEVELS = LevelHandler.TOTAL_LEVELS;
	public final int NUMBER_SKILLS = LevelHandler.NUMBER_OF_SPELLS;
	
	//The actual buttons.
	public GUIButton[] buttons_direct;
	public GUIButton[] buttons_direct_effect;
	public GUIButton[] buttons_indirect;
	public GUIButton[] buttons_indirect_effect;
	public GUIButton[] buttons_defense;
	public GUIButton[] buttons_defense_effect;
	
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
		//Initialize each array of skill buttons.
		buttons_direct = new GUIButton[NUMBER_LEVELS];
		buttons_direct_effect = new GUIButton[NUMBER_LEVELS];
		buttons_indirect = new GUIButton[NUMBER_LEVELS];
		buttons_indirect_effect = new GUIButton[NUMBER_LEVELS];
		buttons_defense = new GUIButton[NUMBER_LEVELS];
		buttons_defense_effect = new GUIButton[NUMBER_LEVELS];
		
		//Create each individual button.
		for(int i = 0; i < NUMBER_LEVELS; i++)
		{
			final float size = 25f;
			final float x = size * i;
			
			buttons_direct[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", x, 0f, size, size, spell_number)
		}
		
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
	 * @param type Direct, Indirect, etc.
	 * @param level Main or Sub ability.
	 * @param button_number The button value.
	 */
	public void levelUpSpell(AttackType type, AttackLevel level, int button_number)
   {//Increase the number. Check the bounds. If it hit the max, disable the level up button.
		//Cost will be dependent upon the type of spell this is.
		int cost = button_number * (type == AttackType.ULTIMATE ? ULTIMATE_COST_PER_LEVEL : COST_PER_LEVEL);
		
		//If we can spend that many points, do it.
		if(getLevelHandler().canSpend(cost))
		{
			getLevelHandler().spend(cost);
			
			//Level the correct counter.
			switch(type)
			{
				case DIRECT:
					if(level == AttackLevel.MAIN)
						getLevelHandler().direct_levels = button_number;
					else
						getLevelHandler().direct_sublevels = button_number;
					break;				
				case INDIRECT:
					if(level == AttackLevel.MAIN)
						getLevelHandler().indirect_levels = button_number;
					else
						getLevelHandler().indirect_sublevels = button_number;
					break;
				case DEFENSE:
					if(level == AttackLevel.MAIN)
						getLevelHandler().defense_levels = button_number;
					else
						getLevelHandler().defense_sublevels = button_number;
					break;
				case ULTIMATE:
					if(level == AttackLevel.MAIN)
						getLevelHandler().ultimate_levels= button_number;
					else
						getLevelHandler().ultimate_sublevels = button_number;
					break;				
				default:
					System.out.println("Error: Tried to level " + type + ". This should never happen.");
					break;
			}
		}
   }
}
