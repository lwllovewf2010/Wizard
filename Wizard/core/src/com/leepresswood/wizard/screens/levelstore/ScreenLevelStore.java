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
	public GUIButton[] buttons_ultimate;
	public GUIButton[] buttons_ultimate_effect;
	
	//Other buttons.
	public GUIButton button_return;
	
	//Button placement.
	private final float BUTTON_SIZE = 25f;
	private final float BUTTON_GAP = Gdx.graphics.getWidth() / (NUMBER_LEVELS + 1f) - BUTTON_SIZE;
	private final float BUTTON_Y_DIRECT = Gdx.graphics.getHeight() - BUTTON_SIZE;
	private final float BUTTON_Y_DIRECT_EFF = BUTTON_Y_DIRECT - BUTTON_SIZE;
	private final float BUTTON_Y_INDIRECT = BUTTON_Y_DIRECT_EFF - BUTTON_SIZE;
	private final float BUTTON_Y_INDIRECT_EFF = BUTTON_Y_INDIRECT - BUTTON_SIZE;
	private final float BUTTON_Y_DEFENSE = BUTTON_Y_INDIRECT_EFF - BUTTON_SIZE;
	private final float BUTTON_Y_DEFENSE_EFF = BUTTON_Y_DEFENSE - BUTTON_SIZE;
	private final float BUTTON_Y_ULTIMATE = BUTTON_Y_DEFENSE_EFF - BUTTON_SIZE;
	private final float BUTTON_Y_ULTIMATE_EFF = BUTTON_Y_ULTIMATE - BUTTON_SIZE;
	
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
		buttons_ultimate = new GUIButton[NUMBER_LEVELS];
		buttons_ultimate_effect = new GUIButton[NUMBER_LEVELS];
		
		//Create each individual button.
		for(int i = 0; i < NUMBER_LEVELS; i++)
		{
			final float x = BUTTON_GAP + i * (BUTTON_SIZE + BUTTON_GAP);
			
			buttons_direct[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_DIRECT, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_direct_effect[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_DIRECT_EFF, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_indirect[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_INDIRECT, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_indirect_effect[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_INDIRECT_EFF, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_defense[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_DEFENSE, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_defense_effect[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_DEFENSE_EFF, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_ultimate[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_ULTIMATE, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
			buttons_ultimate_effect[i] = new LevelUpSpellButton(this, game.assets.get("textures/hold.png", Texture.class), x, BUTTON_Y_ULTIMATE_EFF, BUTTON_SIZE, BUTTON_SIZE, AttackType.DIRECT, AttackLevel.MAIN, i);
		}
		
		//The first buttons will be initialized.
		buttons_direct[0].is_active = true;
		buttons_direct_effect[0].is_active = true;
		buttons_indirect[0].is_active = true;
		buttons_indirect_effect[0].is_active = true;
		buttons_defense[0].is_active = true;
		buttons_defense_effect[0].is_active = true;
		buttons_ultimate[0].is_active = true;
		buttons_ultimate_effect[0].is_active = true;
		
		//Other buttons.
		button_return = new ReturnToGameGUIButton(this, game.assets.get("textures/hold.png", Texture.class), 0f, 0f, 50f, 50f);
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
		for(int i = 0; i < NUMBER_LEVELS; i++)
		{
			buttons_direct[i].update(delta);
			buttons_direct_effect[i].update(delta);
			buttons_indirect[i].update(delta);
			buttons_indirect_effect[i].update(delta);
			buttons_defense[i].update(delta);
			buttons_defense_effect[i].update(delta);
			buttons_ultimate[i].update(delta);
			buttons_ultimate_effect[i].update(delta);
		}
		
		//Other buttons.
		button_return.update(delta);
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
			for(int i = 0; i < NUMBER_LEVELS; i++)
			{
				buttons_direct[i].draw(batch);
				buttons_direct_effect[i].draw(batch);
				buttons_indirect[i].draw(batch);
				buttons_indirect_effect[i].draw(batch);
				buttons_defense[i].draw(batch);
				buttons_defense_effect[i].draw(batch);
				buttons_ultimate[i].draw(batch);
				buttons_ultimate_effect[i].draw(batch);
			}
			
			//Other buttons.
			button_return.draw(batch);
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
	 * Level up the designated spell.
	 * @param type Direct, Indirect, etc.
	 * @param level Main or Sub ability.
	 * @param button_number The button value.
	 */
	public void levelUpSpell(AttackType type, AttackLevel level, int button_number)
   {//Increase the number. Check the bounds. If it hit the max, disable the level up button.
		//Cost will be dependent upon the type of spell this is.
		int cost = (button_number + 1) * (type == AttackType.ULTIMATE ? ULTIMATE_COST_PER_LEVEL : COST_PER_LEVEL);
		
		//Note: Be sure to disable this button and enable the next one (if possible).					
		//If we can spend that many points, do it.
		if(getLevelHandler().canSpend(cost))
		{
			getLevelHandler().spend(cost);
			
			//Level the correct counter.
			switch(type)
			{
				case DIRECT:
					if(level == AttackLevel.MAIN)
					{
						getLevelHandler().direct_levels = button_number;
						buttons_direct[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_direct[button_number + 1].is_active = true;
						}
					}
					else
					{
						getLevelHandler().direct_sublevels = button_number;
						buttons_direct_effect[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_direct_effect[button_number + 1].is_active = true;
						}
					}
					break;				
				case INDIRECT:
					if(level == AttackLevel.MAIN)
					{
						getLevelHandler().indirect_levels = button_number;
						buttons_indirect[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_indirect[button_number + 1].is_active = true;
						}
					}
					else
					{
						getLevelHandler().indirect_sublevels = button_number;
						buttons_indirect_effect[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_indirect_effect[button_number + 1].is_active = true;
						}
					}
					break;
				case DEFENSE:
					if(level == AttackLevel.MAIN)
					{
						getLevelHandler().defense_levels = button_number;
						buttons_defense[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_defense[button_number + 1].is_active = true;
						}
					}
					else
					{
						getLevelHandler().defense_sublevels = button_number;
						buttons_defense_effect[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_defense_effect[button_number + 1].is_active = true;
						}
					}
					break;
				case ULTIMATE:
					if(level == AttackLevel.MAIN)
					{
						getLevelHandler().ultimate_levels= button_number;
						buttons_ultimate[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_ultimate[button_number + 1].is_active = true;
						}
					}
					else
					{
						getLevelHandler().ultimate_sublevels = button_number;
						buttons_ultimate_effect[button_number].is_active = false;
						if(button_number + 1 < NUMBER_LEVELS)
						{
							buttons_ultimate_effect[button_number + 1].is_active = true;
						}
					}
					break;				
				default:
					System.out.println("Error: Tried to level " + type + ". This should never happen.");
					break;
			}
		}
   }
}
