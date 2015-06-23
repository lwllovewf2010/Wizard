package com.leepresswood.wizard.screens.game.gui;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.gui.GUIButton;
import com.leepresswood.wizard.screens.game.ScreenGame;
import com.leepresswood.wizard.world.entities.player.spells.Spell;
import com.leepresswood.wizard.world.entities.player.spells.damage.Aether;
import com.leepresswood.wizard.world.entities.player.spells.damage.Fireball;
import com.leepresswood.wizard.world.entities.player.spells.utility.Dig;

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
	public Bar bar_health, bar_mana;
	public Color color_health, color_mana;
	
	//Spells
	public final int SPELL_NUMBER_MAX = 3;
	public int spell_number_unlocked = 1;
	public int spell_active = 0;
	public Spell[] spells;
	
	public GUIGame(ScreenGame screen)
	{
		this.screen = screen;
		
		makeCamera();
		makeStatusBars();
		makeSpellList();
		makeButtons();
	}
	
	/**
	 * Remake the spell list upon returning from the level store.
	 * @param spell_number_unlocked New number from the level store.
	 */
	public void refreshSpellList(int spell_number_unlocked)
	{
		//Avoid this method if we didn't update the number of spells.
		if(this.spell_number_unlocked != spell_number_unlocked)
		{
			this.spell_number_unlocked = spell_number_unlocked > SPELL_NUMBER_MAX ? SPELL_NUMBER_MAX : spell_number_unlocked;
			makeSpellList();
		}
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
		final float gap = 2f;
		final float bar_width = Gdx.graphics.getWidth() * 0.3f;
		final float bar_height = Gdx.graphics.getHeight() * 0.02f;
		final float bar_x = gap;
		final float bar_y = Gdx.graphics.getHeight() - gap - bar_height;
		final float recovery_health = 0.75f;
		final float recovery_mana = 0.3f;
		
		//Set bars.
		bar_health = new Bar(bar_x, bar_y, bar_width, bar_height, screen.world.entity_handler.player.health, recovery_health);
		bar_mana = new Bar(bar_x, bar_y - bar_height - gap, bar_width, bar_height, screen.world.entity_handler.player.mana, recovery_mana);
		
		//Set colors.
		color_health = new Color(Color.valueOf("AA3C39FF"));
		color_mana = new Color(Color.valueOf("2E4372FF"));
	}
	
	/**
	 * Create the spell list.
	 */
	private void makeSpellList()
	{
		try
		{
			//Initialize each spell. We will be reading from the player's list of spells.
			spells = new Spell[spell_number_unlocked];
			for(int i = 0; i < spell_number_unlocked; i++)
				spells[i] = parseSpell(new XmlReader().parse(Gdx.files.internal("data/spells.xml")), screen.world.entity_handler.player_root.getChildByName("spell_list").getChild(i).getText(), i);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private Spell parseSpell(Element spell_root, String spell_name, int position)
	{
		Spell s = null;
		
		//Parse name.
		if(spell_name.equalsIgnoreCase("dig"))
			s = new Dig(screen.game.assets.get("textures/hold.png", Texture.class), 3f, 3f + position * 53f);
		else if(spell_name.equalsIgnoreCase("aether"))
			s = new Aether(screen.game.assets.get("textures/hold.png", Texture.class), 3f, 3f + position * 53f);
		else if(spell_name.equalsIgnoreCase("fireball"))
			s = new Fireball(screen.game.assets.get("textures/hold.png", Texture.class), 3f, 3f + position * 53f);
		
		//Get mana cost of this spell.
		s.mana_cost = spell_root.getChildByName(spell_name).getFloat("cost");
		return s;
	}
	
	/**
	 * Make the clickable buttons.
	 */
	private void makeButtons()
	{
		button_array = new GUIButton[MAX_BUTTONS];
		button_array[0] = new LevelGUIButton(screen, screen.game.assets.get("textures/hold.png", Texture.class), Gdx.graphics.getWidth() - 26f, Gdx.graphics.getHeight() - 26f, 25f, 25f);
	}
	
	/**
	 * Timed update. This will be used in the recovery of health/mana, any animations, and possibly a game clock with a day/night system.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		for(GUIButton b : button_array)
			b.update(delta);
		
		bar_health.updateOverTime(delta);
		bar_mana.updateOverTime(delta);
	}
	
	/**
	 * Draw all parts of the GUI.
	 */
	public void draw()
	{
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			for(GUIButton b : button_array)
				b.draw(screen.batch);
			
			for(int i = 0; i < spell_number_unlocked; i++)
				spells[i].sprite.draw(screen.batch);
		screen.batch.end();
		
		//Health/Mana bars.
		screen.renderer.begin(ShapeType.Filled);
			screen.renderer.identity();
			screen.renderer.rect(bar_health.x, bar_health.y, bar_health.width, bar_health.height, color_health, color_health, color_health, color_health);
			screen.renderer.rect(bar_mana.x, bar_mana.y, bar_mana.width, bar_mana.height, color_mana, color_mana, color_mana, color_mana);
		screen.renderer.end();
		
		//Spell outlines.
		screen.renderer.begin(ShapeType.Line);
			screen.renderer.identity();
			for(int i = 0; i < spell_number_unlocked; i++)
				if(i == spell_active)
					screen.renderer.rect(spells[i].sprite.getX() - 1, spells[i].sprite.getY() + 1, spells[i].sprite.getWidth() + 1, spells[i].sprite.getHeight() + 1, Color.RED, Color.RED, Color.RED, Color.RED);
				else
					screen.renderer.rect(spells[i].sprite.getX() - 1, spells[i].sprite.getY() + 1, spells[i].sprite.getWidth() + 1, spells[i].sprite.getHeight() + 1, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);					
		screen.renderer.end();

		//Mouse position outline.
		/*Rectangle r = null;
		for(int j = 0; j < screen.world.map_camera_handler.WORLD_TOTAL_VERTICAL; j++)
		{
			for(int i = 0; i < screen.world.map_camera_handler.WORLD_TOTAL_HORIZONTAL; i++)
			{
				if(screen.world.map_camera_handler.map_rectangles[j][i].contains(screen.input.mouse_position.x, screen.input.mouse_position.y))//screen.world.map_camera_handler.map_rectangles[(int) (screen.input.mouse_position.y)][(int) (screen.input.mouse_position.x)];
				{
					r = screen.world.map_camera_handler.map_rectangles[j][i];
					i = screen.world.map_camera_handler.WORLD_TOTAL_HORIZONTAL;
					j = screen.world.map_camera_handler.WORLD_TOTAL_VERTICAL;
				}
			}System.out.println(screen.input.mouse_position);
		}
		
		if(r != null)
		{
			screen.renderer.setColor(Color.WHITE);
			screen.renderer.begin(ShapeType.Line);
				screen.renderer.identity();
				screen.renderer.rect(r.x, r.y, r.width, r.height);
			screen.renderer.end();
		}*/
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
			spell_active = spell_number_unlocked - 1;
	}
	
	/**
	 * Correct the selected spell after a shift of the active spell right by one.
	 */
	private void shiftSpellRight()
	{
		if(spell_active == spell_number_unlocked)
			spell_active = 0;
	}
	
	/**
	 * Get the active spell.
	 * @return The chosen spell.
	 */
	public Spell getActiveSpell()
	{
		return spells[spell_active];
	}
	
	/**
	 * Can the player cast the required spell?
	 * @param s The spell to examine.
	 * @return If it is possible to cast the spell.
	 */
	public boolean canCast(Spell s)
	{
		return bar_mana.current_bar_value >= s.mana_cost;
	}
	
	/**
	 * Subtract the mana cost from the mana bar.
	 * @param s The spell whose mana we are examining.
	 */
	public void cast(Spell s)
	{
		bar_mana.change(-s.mana_cost);
	}
}
