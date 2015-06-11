package com.leepresswood.wizard.handlers;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.enemies.EnemyFactory;
import com.leepresswood.wizard.entities.player.AirWizard;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.entities.spells.SpellFactory;
import com.leepresswood.wizard.enums.MagicType;
import com.leepresswood.wizard.screens.game.GameWorld;

public class EntityHandler
{
	public GameWorld world;
	
	public SpellFactory factory_spell;							//Creates spells. Manages spell recharge time.
	public EnemyFactory factory_enemy;							//Creates enemies.
	
	public MagicType type;											//The type of magic the player has.
	public Player player;											//Reference to playable character.
	
	public ArrayList<Enemy> enemies;								//List of enemies.
	public ArrayList<Spell> spells;								//List of spells.
	public ArrayList<Object> remove;								//Deals with the removal of objects that no longer need to be on the screen.
	
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
			Element root = new XmlReader().parse(Gdx.files.internal("data/wizards.xml")).getChildByName(type.toString().toLowerCase());
			
			switch(type)
			{
				case AIR:
					player = new AirWizard(world, world.camera.WORLD_TOTAL_HORIZONTAL / 2f, world.camera.GROUND, root);
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
		
		//Delete old objects.
		deleteOldObjects();
	}
	
	public void draw()
	{
		world.screen.batch.setProjectionMatrix(world.camera.combined);
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
