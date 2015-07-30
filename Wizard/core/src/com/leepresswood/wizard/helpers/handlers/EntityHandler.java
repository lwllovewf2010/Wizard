package com.leepresswood.wizard.helpers.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.Box2DSprite;
import com.leepresswood.wizard.helpers.enums.MagicType;
import com.leepresswood.wizard.helpers.factories.EnemyFactory;
import com.leepresswood.wizard.helpers.factories.SpellFactory;
import com.leepresswood.wizard.helpers.interfaces.DynamicallyCreatedSpellInterface;
import com.leepresswood.wizard.world.Universe;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.living.enemies.Enemy;
import com.leepresswood.wizard.world.entities.living.player.Player;
import com.leepresswood.wizard.world.entities.living.player.types.AirWizard;
import com.leepresswood.wizard.world.entities.spells.Spell;

public class EntityHandler
{
	public Universe universe;
	
	public SpellFactory factory_spell;							//Creates spells. Manages spell recharge time.
	public EnemyFactory factory_enemy;							//Creates enemies.
	
	public MagicType type;											//The type of magic the player has.
	public Player player;											//Reference to playable character.
	public Element player_root;									//Data root of the player. Used in gathering the spell list.
	
	public ArrayList<Enemy> enemies;								//List of enemies.
	public ArrayList<Spell> spells;								//List of spells.
	public ArrayList<DynamicallyCreatedSpellInterface> spell_queue;//New spells being created will be placed here if their creation interrupts world rendering looping.
	public ArrayList<GameEntity> remove;						//Deals with the removal of objects that no longer need to be on the screen.
	
	public Element wave_root;										//Contains wave data.
	public int wave;													//Current wave.
	public Queue<String> enemy_queue;							//Holds the enemies that are to be created over time.
	
	/**
	 * Debug constructor.
	 * @param universe Reference to universe.
	 */
	public EntityHandler(Universe universe){this(universe, MagicType.AIR);}
	
	/**
	 * Spawn player of the given magic type.
	 * @param universe Reference to universe.
	 * @param type Magic type to spawn.
	 */
	public EntityHandler(Universe universe, MagicType type)
	{
		this.universe = universe;
		this.type = type;
		
		//The factories are used to create new game items.
		factory_spell = new SpellFactory(universe);
		factory_enemy = new EnemyFactory(universe);
		
		//Get a reference to the player from the given type.
		try
		{
			//Read the root of the wizard file and gather the requested type of wizard. 
			player_root = new XmlReader().parse(Gdx.files.internal("data/wizards.xml")).getChildByName(type.toString().toLowerCase());
			
			switch(type)
			{
				case AIR:
					player = new AirWizard(universe, universe.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f, universe.map_camera_handler.GROUND + 5f, player_root);
					break;
				case EARTH:
					break;
				case FIRE:
					break;
				case POISON:
					break;
				case SUPPORT:
					break;
				case UTILITY:
					break;
				case VOID:
					break;
				case WATER:
					break;
				default:
					break;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//Initialize the lists.
		enemies = new ArrayList<Enemy>();
		spells = new ArrayList<Spell>();
		spell_queue = new ArrayList<DynamicallyCreatedSpellInterface>();
		remove = new ArrayList<GameEntity>();
		
		//Initialize wave data.
		try
		{
			wave_root = new XmlReader().parse(Gdx.files.internal("data/waves.xml"));
			wave = 1;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Player requested to cast a spell. Manage this here.
	 */
	public void addSpell(Vector2 touch)
	{
		if(player.active && universe.level_handler.castable_spells.size() > 0)
		{
			//Get the selected spell type from the GUI.
			Spell spell_type = universe.screen.gui.getActiveSpell();
			
			//Get the selected spell's mana cost and compare it to the player's current mana. See if it's possible to cast.
			if(player.mana_current >= spell_type.mana_cost)
			{
				//Get the spell from the factory. Two vectors represent the player's center and the click location, respectively.
				Spell spell = universe.entity_handler.factory_spell.getSpell(spell_type.getClass().getSimpleName().toUpperCase(), new Vector2(player.parts[0].sprite.getX() + player.parts[0].sprite.getWidth() / 2f, player.parts[0].sprite.getY() + player.parts[0].sprite.getHeight() / 2f), new Vector2(touch.x, touch.y), universe.level_handler.spell_levels[universe.screen.gui.spell_active]);
				
				//If this spell is null, we weren't able to instantiate it due to recharge timing not being correct or an active spell not being chosen in the GUI.
				if(spell != null)
				{ 
					//Create the selected spell.
					player.mana_current -= spell.mana_cost;
					spells.add(spell);
				}
			}
		}
	}
	
	/**
	 * Next wave is starting.
	 */
	public void spawnWave()
	{
		//Add all these enemies to the list of spawning enemies.
		Element current_wave_data = wave_root.getChild(wave - 1);
		enemy_queue = new LinkedList<String>();
		for(int i = 0; i < current_wave_data.getChildCount(); i++)
			for(int j = 0; j < current_wave_data.getChild(i).getInt("number"); j++)
				enemy_queue.add(current_wave_data.getChild(i).get("name"));
	}
	
	public void update(float delta)
	{
		//Player and enemies.
		factory_enemy.update(delta);
		for(Enemy e : enemies)
			e.update(delta);
		player.update(delta);
		
		//Spells
		factory_spell.update(delta);
		for(Spell s : spells)
			s.update(delta);
		
		//Spawn new spells from the spell queue.
		for(DynamicallyCreatedSpellInterface s : spell_queue)
		{
			s.instantiate();
			spells.add((Spell) s);
		}
		spell_queue.clear();
		
		//Spawn new enemies if we're ready for them.
		if(enemy_queue != null && !enemy_queue.isEmpty() && factory_enemy.isReady())
			enemies.add(factory_enemy.getEnemy(enemy_queue.remove().toUpperCase()));
		
		//Delete old objects.
		deleteOldObjects();
	}
	
	public void draw()
	{
		universe.screen.batch.setProjectionMatrix(universe.map_camera_handler.combined);
		universe.screen.batch.begin();
			for(Enemy e : enemies)
				e.draw(universe.screen.batch);
			player.draw(universe.screen.batch);
			for(Spell s : spells)
				s.draw(universe.screen.batch);
		universe.screen.batch.end();
	}
	
	/**
	 * Delete old objects.
	 */
	private void deleteOldObjects()
	{
		//Delete old spells.
		for(Spell s : spells)
			if(!s.active)				
				remove.add(s);
			
		//Delete old enemies.
		for(Enemy e : enemies)
			if(!e.active && !e.dying)
				remove.add(e);
		
		//Do the removal.
		for(GameEntity o : remove)
		{	
			if(o instanceof Spell)
				spells.remove(o);
			else if(o instanceof Enemy)
				enemies.remove(o);
		
			//On top of removing the entity from its list, destroy its body.
			for(Box2DSprite s : o.parts)
				universe.world_handler.world.destroyBody(s.body);
		}
		remove.clear();
	}
}
