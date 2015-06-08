package com.leepresswood.wizard.screens.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.data.Assets;
import com.leepresswood.wizard.entities.enemies.Enemy;
import com.leepresswood.wizard.entities.enemies.EnemyFactory;
import com.leepresswood.wizard.entities.player.Player;
import com.leepresswood.wizard.entities.spells.Spell;
import com.leepresswood.wizard.entities.spells.SpellFactory;

/**
 * Holds information about the game world. Sets up camera based upon this world.
 * @author Lee
 *
 */
public class GameWorld
{
	public ScreenGame screen;
	public OrthographicCamera camera;
	public TiledMap map;
	public OrthogonalTiledMapRenderer map_renderer;	
	
	public int WORLD_TOTAL_HORIZONTAL, WORLD_TOTAL_VERTICAL;
	public float WORLD_LEFT, WORLD_RIGHT, WORLD_TOP, WORLD_BOTTOM;	
	public final float WORLD_ZOOM = 2.0f;						//Amount added to the world camera's zoom.
	public final float WORLD_PLAYER_Y_SKEW = 4.0f;			//Higher values of this will move the player closer to the vertical middle. Lower values will move the player down. Anything less than 2 will put the player off the screen.
	public float GROUND;												//Temporary value for the Y-value of the ground. Eventually want to read the blocks themselves and see if they are solid.
	public float GRAVITY;											//Value of gravity. Set by the map. May seek to change eventually (faster/slower falling, or maybe reverse gravity)
	
	public Player player;
	
	public EnemyFactory factory_enemy;							//Creates enemies.
	public ArrayList<Enemy> enemies;
	
	public SpellFactory factory_spell;							//Creates spells. Manages spell recharge time.
	public ArrayList<Spell> spells;	
	
	public ArrayList<Object> remove;								//Deals with the removal of objects that no longer need to be on the screen.
	
	public GameWorld(ScreenGame screen)
	{
		this.screen = screen;
		factory_spell = new SpellFactory(screen);
		factory_enemy = new EnemyFactory(screen);
		
		setUpWorld();
		populateWorld();	
	}
	
	/**
	 * GSet up the world by reading information from the map.
	 */
	private void setUpWorld()
	{
		//Get map data. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = screen.game.assets.getMap(Assets.MAP_TEST);
		float pixel_size = new Float(map.getProperties().get("tilewidth", Integer.class));
		GROUND = Float.parseFloat((String) (map.getProperties().get("ground")));
		GRAVITY =  Float.parseFloat((String) (map.getProperties().get("gravity")));
		
		//Get the map renderer from this data.
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / pixel_size);									//Passed float number is the the inverse of the pixels per unit.		
		
		//Set the bounds of the camera.
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / pixel_size, Gdx.graphics.getHeight() / pixel_size);
		camera.zoom += WORLD_ZOOM;
		camera.update();
		
		/* Set the bounds of the world.
		 * These will be used to give the camera cues as to where to position itself.
		 * For example, the camera will normally follow the player.
		 * However, if the camera's bounds would go outside the world, 
		 * the camera should snap to the world's edge until the player
		 * returns to a location that allows the camera to move more freely.
		 * Think about reaching one of the ends of the world in Terraria as an example. 
		 * 
		 * The positions are not the edges of the world -- that would be a waste of space and time.
		 * Rather, they are the bounds of where the camera can move.
		 * WORLD_BOTTOM will be the lowest Y value of the camera before it stops moving downward.
		 * Extend this to the others.
		 */
		WORLD_TOTAL_HORIZONTAL = map.getProperties().get("width", Integer.class);
		WORLD_TOTAL_VERTICAL = map.getProperties().get("height", Integer.class);		
		
		WORLD_BOTTOM = camera.zoom * camera.viewportHeight / 2f;
		WORLD_TOP = WORLD_TOTAL_VERTICAL - WORLD_BOTTOM;
		WORLD_LEFT = camera.zoom * camera.viewportWidth / 2f;
		WORLD_RIGHT = WORLD_TOTAL_HORIZONTAL - WORLD_LEFT;
		
		//Display information.
		System.out.println("World:\n\tGround Level: " + GROUND + "\n\tGravity: " + GRAVITY + "\n\tBlock Size: " + pixel_size);
		System.out.println("Camera:\n\tPosition: " + camera.position + "\n\tWidth: " + camera.viewportWidth + "\n\tHeight: " + camera.viewportHeight + "\n\tZoom: " + camera.zoom);
	}
	
	/**
	 * Add the initial entities to the game world.
	 */
	private void populateWorld()
	{
		player = new Player(screen, WORLD_TOTAL_HORIZONTAL / 2f, GROUND);
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
		
		//Manage the other items in the world.
		setCameraBounds();
		deleteOldObjects();
	}
	
	/**
	 * Check the camera's position for correctness. It should not go off the world's bounds.
	 */
	private void setCameraBounds()
	{
		//First, set the camera to the player's position.
		camera.position.x = player.sprite.getX() + player.sprite.getWidth() / 2f;
		camera.position.y = player.sprite.getY() + player.sprite.getHeight() / 2f + camera.zoom * camera.viewportHeight / WORLD_PLAYER_Y_SKEW;
		
		//If this moves off the world's bounds, correct it.
		if(camera.position.x < WORLD_LEFT)
			camera.position.x = WORLD_LEFT;
		else if(camera.position.x > WORLD_RIGHT)
			camera.position.x = WORLD_RIGHT;
		else if(camera.position.y < WORLD_BOTTOM)
			camera.position.y = WORLD_BOTTOM;
		else if(camera.position.y > WORLD_TOP)
			camera.position.y = WORLD_TOP;
		camera.update();
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
		
		
		//Do the actual removal.
		if(!remove.isEmpty())
			for(Object o : remove)
				if(o instanceof Spell)
					spells.remove(o);
				else if(o instanceof Enemy)
					enemies.remove(o);
	}
	
	public void draw()
	{
		map_renderer.setView(camera);
		map_renderer.render();
		
		//Game objects
		screen.batch.setProjectionMatrix(camera.combined);
		screen.batch.begin();			
			for(Spell s : spells)
				s.draw(screen.batch);
			for(Enemy e : enemies)
				e.draw(screen.batch);
			player.draw(screen.batch);
		screen.batch.end();
	}
}
