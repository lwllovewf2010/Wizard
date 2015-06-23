package com.leepresswood.wizard.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.enums.MagicType;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.enemies.Enemy;
import com.leepresswood.wizard.world.entities.enemies.EnemyFactory;
import com.leepresswood.wizard.world.entities.player.Player;
import com.leepresswood.wizard.world.entities.player.spells.Spell;
import com.leepresswood.wizard.world.entities.player.spells.SpellFactory;
import com.leepresswood.wizard.world.entities.player.types.AirWizard;

public class EntityHandler
{
	public GameWorld world;
	
	public SpellFactory factory_spell;							//Creates spells. Manages spell recharge time.
	public EnemyFactory factory_enemy;							//Creates enemies.
	
	public MagicType type;											//The type of magic the player has.
	public Player player;											//Reference to playable character.
	public Element player_root;									//Data root of the player. Used in gathering the spell list.
	
	public ArrayList<Enemy> enemies;								//List of enemies.
	public ArrayList<Spell> spells;								//List of spells.
	public ArrayList<Object> remove;								//Deals with the removal of objects that no longer need to be on the screen.
	
	public Element wave_root;
	public int wave;
	public Queue<String> enemy_queue;
	
	/**
	 * Debug constructor.
	 * @param world Reference to world.
	 */
	public EntityHandler(GameWorld world){this(world, MagicType.AIR);}
	
	/**
	 * Spawn player of the given magic type.
	 * @param world Reference to world.
	 * @param type Magic type to spawn.
	 */
	public EntityHandler(GameWorld world, MagicType type)
	{
		this.world = world;
		this.type = type;
		
		//The factories are used to create new game items.
		factory_spell = new SpellFactory(world);
		factory_enemy = new EnemyFactory(world);
		
		//Get a reference to the player from the given type.
		try
		{
			//Read the root of the wizard file and gather the requested type of wizard. 
			player_root = new XmlReader().parse(Gdx.files.internal("data/wizards.xml")).getChildByName(type.toString().toLowerCase());
			
			switch(type)
			{
				case AIR:
					player = new AirWizard(world, world.map_camera_handler.WORLD_TOTAL_HORIZONTAL / 2f, world.map_camera_handler.GROUND, player_root);
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
		remove = new ArrayList<Object>();
		
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
		if(!player.is_dead)
		{
			//Get the selected spell type from the GUI.
			Spell spell_type = world.screen.gui.getActiveSpell();
			
			//Get the selected spell's mana cost and compare it to the player's current mana. See if it's possible to cast.
			if(world.screen.gui.canCast(spell_type))
			{
				//Get the spell from the factory. Two vectors represent the player's center and the click location, respectively.
				Spell spell = world.entity_handler.factory_spell.getSpell(spell_type.getClass().getSimpleName().toUpperCase(), new Vector2(player.sprite.getX() + player.sprite.getWidth() / 2f, player.sprite.getY() + player.sprite.getHeight() / 2f), new Vector2(touch.x, touch.y));
				
				//If this spell is null, we weren't able to instantiate it due to recharge timing not being correct or an active spell not being chosen in the GUI.
				if(spell != null)
				{
					//Create the selected spell.
					world.screen.gui.cast(spell);
					world.entity_handler.spells.add(spell);
				}
			}
		}
	}
	
	/**
	 * Next wave is starting.
	 */
	public void spawnWave()
	{
		//Get wave.
		Element current_wave_data = wave_root.getChild(wave - 1);
		
		//Add all these enemies to the list of spawning enemies.
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
		
		//Spawn new enemies if we're ready for them.
		if(enemy_queue != null && !enemy_queue.isEmpty() && factory_enemy.isReady())
			enemies.add(factory_enemy.getEnemy(enemy_queue.remove().toUpperCase()));
		
		//Delete old objects.
		deleteOldObjects();
	}
	
	public void draw()
	{
		world.screen.batch.setProjectionMatrix(world.map_camera_handler.combined);
		world.screen.batch.begin();			
			for(Spell s : spells)
				s.draw(world.screen.batch);
			for(Enemy e : enemies)
				e.draw(world.screen.batch);
			player.draw(world.screen.batch);
		world.screen.batch.end();
	}
	
	/**
	 * Delete old objects.
	 */
	private void deleteOldObjects()
	{
		remove.clear();
		
		//Delete old spells.
		for(Spell s : spells)
			if(!s.active)				
				remove.add(s);
			
		//Delete old enemies.
		for(Enemy e : enemies)
			if(e.is_dead)
				remove.add(e);
		
		//Do the actual removal.
		if(!remove.isEmpty())
			for(Object o : remove)
				if(o instanceof Spell)
					spells.remove(o);
				else if(o instanceof Enemy)
					enemies.remove(o);
	}
}
