package com.leepresswood.wizard.screens.game;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.attributes.Bar;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.entities.spells.damage.Aether;
import com.leepresswood.wizard.entities.spells.damage.Fireball;
import com.leepresswood.wizard.entities.spells.utility.Dig;

public class GUIGame
{
	private ScreenGame screen;
	public OrthographicCamera camera;
	
	//Health/Mana Bars
	public Bar bar_health, bar_mana;
	private Color color_health, color_mana;
	
	//Spells
	private final int MAX_SPELLS = 10;
	private int spell_active = 0;
	private Spell[] spells;
	
	public GUIGame(ScreenGame screen)
	{
		this.screen = screen;	
				
		makeCamera();
		makeStatusBars();
		makeSpellList();
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
		
		//Set bars
		bar_health = new Bar(bar_x, bar_y, bar_width, bar_height, screen.world.entity_handler.player.health, recovery_health);
		bar_mana = new Bar(bar_x, bar_y - bar_height - gap, bar_width, bar_height, screen.world.entity_handler.player.mana, recovery_mana);
		
		//Set colors
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
			Element root = new XmlReader().parse(Gdx.files.internal("data/spells.xml"));
			spells = new Spell[MAX_SPELLS];
			
			spells[0] = new Fireball(screen.game.assets.get("textures/hold.png", Texture.class), 3f, 1f);
			spells[1] = new Aether(screen.game.assets.get("textures/hold.png", Texture.class), 56f, 1f);
			spells[2] = new Dig(screen.game.assets.get("textures/hold.png", Texture.class), 109f, 1f);
			
			spells[0].mana_cost = root.getChildByName("fireball").getFloat("cost");
			spells[1].mana_cost = root.getChildByName("aether").getFloat("cost");
			spells[2].mana_cost = root.getChildByName("dig").getFloat("cost");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Timed update. This will be used in the recovery of health/mana, any animations, and possibly a game clock with a day/night system.
	 * @param delta Change in time.
	 */
	public void update(float delta)
	{
		bar_health.updateOverTime(delta);
		bar_mana.updateOverTime(delta);
	}
	
	/**
	 * Draw all parts of the GUI.
	 */
	public void draw()
	{
		//Spells.
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();
			for(int i = 0; i < MAX_SPELLS; i++)
				if(spells[i] != null)
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
			for(int i = 0; i < MAX_SPELLS; i++)
				if(spells[i] != null)
					if(i == spell_active)
						screen.renderer.rect(spells[i].sprite.getX() - 1, spells[i].sprite.getY() + 1, spells[i].sprite.getWidth() + 1, spells[i].sprite.getHeight() + 1, Color.RED, Color.RED, Color.RED, Color.RED);
					else
						screen.renderer.rect(spells[i].sprite.getX() - 1, spells[i].sprite.getY() + 1, spells[i].sprite.getWidth() + 1, spells[i].sprite.getHeight() + 1, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);					
		screen.renderer.end();

		//Mouse position outline.
		screen.renderer.setColor(Color.WHITE);
		screen.renderer.begin(ShapeType.Line);
			screen.renderer.identity();
			screen.renderer.rect(((InputGame) Gdx.input.getInputProcessor()).mouse_position.x, 100, 100, 100);
		screen.renderer.end();
	}
	
	/**
	 * Player pressed a number. Switch to that spell.
	 * @param count The number pressed.
	 */
	public void shiftSpellTo(int count)
	{//Setting active to the count variable and then shifting down one will be enough.
		spell_active = count;
		shiftSpellLeft();
	}

	/**
	 * Player scrolled the mouse wheel. Change the active spell.
	 * @param amount Amount scrolled. Negative scrolls left; positive scrolls right.
	 */
	public void changeSpell(int amount)
	{
		if(amount < 0)
			shiftSpellLeft();
		else if(amount > 0)
			shiftSpellRight();
	}
	
	/**
	 * Shift the active spell left by one.
	 */
	private void shiftSpellLeft()
	{
		spell_active--;
		if(spell_active < 0)
			spell_active = MAX_SPELLS - 1;
	}
	
	/**
	 * Shift the active spell right by one.
	 */
	private void shiftSpellRight()
	{
		spell_active++;
		if(spell_active == MAX_SPELLS)
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
