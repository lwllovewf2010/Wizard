package com.leepresswood.wizard.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.player.attributes.Bar;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.entities.spells.damage.Aether;
import com.leepresswood.wizard.entities.spells.damage.Fireball;

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
		bar_health = new Bar(bar_x, bar_y, bar_width, bar_height, recovery_health);
		bar_mana = new Bar(bar_x, bar_y - bar_height - gap, bar_width, bar_height, recovery_mana);

		//Set colors
		color_health = new Color(Color.valueOf("AA3C39FF"));
		color_mana = new Color(Color.valueOf("2E4372FF"));
	}
	
	/**
	 * Create the spell list.
	 */
	private void makeSpellList()
	{
		spells = new Spell[MAX_SPELLS];
		
		spells[0] = new Fireball(screen.game.assets.getTexture(Assets.TEXTURE_HOLD), 1f, 1f);
		spells[1] = new Aether(screen.game.assets.getTexture(Assets.TEXTURE_HOLD), 52f, 1f);
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
		screen.batch.setProjectionMatrix(camera.combined);
		screen.renderer.begin(ShapeType.Filled);
			screen.renderer.identity();
				
			screen.renderer.rect(bar_health.x, bar_health.y, bar_health.width, bar_health.height, color_health, color_health, color_health, color_health);
			screen.renderer.rect(bar_mana.x, bar_mana.y, bar_mana.width, bar_mana.height, color_mana, color_mana, color_mana, color_mana);
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
		return bar_mana.current_bar_value >= s.cost;
	}
	
	/**
	 * Subtract the mana cost from the mana bar.
	 * @param s The spell whose mana we are examining.
	 */
	public void cast(Spell s)
	{
		bar_mana.change(-s.cost);
	}
}
