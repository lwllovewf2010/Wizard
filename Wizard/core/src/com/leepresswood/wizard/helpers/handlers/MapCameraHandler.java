package com.leepresswood.wizard.helpers.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.helpers.AssetHelper;
import com.leepresswood.wizard.world.Universe;

/**
 * Handles the creation and management of the map and camera.
 * @author Lee
 */
public class MapCameraHandler extends OrthographicCamera
{
	public Universe universe;
	
	
	
	//http://www.cokeandcode.com/main/tutorials/path-finding/
	
	
	
	//Map rendering.
	public TiledMap map;
	public TiledMapTileLayer collision_layer;
	public OrthogonalTiledMapRenderer map_renderer;
	
	//Camera properties.
	public int WORLD_TOTAL_HORIZONTAL, WORLD_TOTAL_VERTICAL;
	public float WORLD_LEFT, WORLD_RIGHT, WORLD_TOP, WORLD_BOTTOM;	
	public final float WORLD_ZOOM = 4.0f;										//Amount added to the world camera's zoom.
	public final float WORLD_PLAYER_Y_SKEW = 75f;							//Higher values of this will move the player closer to the vertical middle. Lower values will move the player down.
	public final float WORLD_HORIZONTAL_WHITESPACE = 10f;					//This is the amount of space left on either side of the world that can be used as a place to spawn enemies or allow knockback.
	
	//Others.
	public float GROUND;																//Temporary value for the Y-value of the ground. Eventually want to read the blocks themselves and see if they are solid.
	public float GRAVITY;															//Value of gravity. Set by the map. May seek to change eventually (faster/slower falling, or maybe reverse gravity)
	public float pixel_size;														//Width/Height of each block.
	
	//Positioning for parallax.
	public float dx, dy;
	public float last_x, last_y;
	
	public MapCameraHandler(Universe universe)
	{
		this.universe = universe;
		
		//Get map data. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = universe.screen.game.assets.getMap(AssetHelper.MAP_TEST);
		pixel_size = new Float(map.getProperties().get("tilewidth", Integer.class));
		GROUND = Float.parseFloat((String) (map.getProperties().get("ground")));
		GRAVITY =  Float.parseFloat((String) (map.getProperties().get("gravity")));
		collision_layer = (TiledMapTileLayer) map.getLayers().get(0);
		
		//Get the map renderer from this data.
		map_renderer = new OrthogonalTiledMapRenderer(map, 1f / pixel_size);				//Passed float number is the the inverse of the pixels per unit.		
		
		//Set the bounds of the camera.
		setToOrtho(false, Gdx.graphics.getWidth() / pixel_size, Gdx.graphics.getHeight() / pixel_size);
		zoom += WORLD_ZOOM;
		update();
		
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
		
		WORLD_BOTTOM = zoom * viewportHeight / 2f;
		WORLD_TOP = WORLD_TOTAL_VERTICAL - WORLD_BOTTOM - WORLD_HORIZONTAL_WHITESPACE;
		WORLD_LEFT = zoom * viewportWidth / 2f + WORLD_HORIZONTAL_WHITESPACE;
		WORLD_RIGHT = WORLD_TOTAL_HORIZONTAL - WORLD_LEFT;
		
		//Create the physical blocks.
		createBlocks();
	}
	
	/**
	 * Make the map blocks.
	 * 
	 * To do this, we want to go row-by-row and connect neighboring blocks. If a block is missing,
	 * leave a gap and start a new neighborhood. Each neighborhood will then be used to create
	 * static ground items.
	 */
	public void createBlocks()
	{
		universe.world_handler.handlerInit(GRAVITY);
		for(int j = 0; j < WORLD_TOTAL_VERTICAL; j++)
		{//Neighborhoods start with a width of 0.
			int neighborhood_width = 0;
			for(int i = 0; i < WORLD_TOTAL_HORIZONTAL; i++)
			{//If a block exists at the given cell location, increase the neighborhood size by 1.
				if(collision_layer.getCell(i, j) != null)
				{
					neighborhood_width++;
				}
				//Otherwise, we want to create a new block and reset the neighborhood if the neighborhood is larger than 0.
				else
				{
					if(neighborhood_width > 0)
					{//Add a neighborhood block and start a new neighborhood.
						universe.world_handler.addBlockToWorld(i - neighborhood_width, j, neighborhood_width, 1f);
						neighborhood_width = 0;
					}
				}
				
				//On top of adding the block if a blank cell is found, we also want to add one if this is the end of the row.
				if(neighborhood_width > 0 && i == WORLD_TOTAL_HORIZONTAL - 1)
				{
					universe.world_handler.addBlockToWorld(i - neighborhood_width + 1, j, neighborhood_width, 1f);
				}
			}
		}
	}
	
	/**
	 * Check the camera's position for correctness. It should not go off the world's bounds.
	 */
	public void setCameraBounds()
	{
		//First, set the camera to the player's position.
		//NOTE: Look at player's position, but add a delay if just recently moved to make the camera smoother.
		//position.x += (universe.entity_handler.player.parts[0].body.getPosition().x - position.x) * Gdx.graphics.getDeltaTime() * LERP;
		//position.y += (universe.entity_handler.player.parts[0].body.getPosition().y - position.y) * Gdx.graphics.getDeltaTime() * LERP + zoom * viewportHeight / WORLD_PLAYER_Y_SKEW;
		
		position.x = universe.entity_handler.player.parts[0].body.getPosition().x;
		position.y = universe.entity_handler.player.parts[0].body.getPosition().y;
		
		//If this moves off the world's bounds, correct it.
		if(position.x < WORLD_LEFT)
			position.x = WORLD_LEFT;
		else if(position.x > WORLD_RIGHT)
			position.x = WORLD_RIGHT;
		if(position.y < WORLD_BOTTOM)
			position.y = WORLD_BOTTOM;
		else if(position.y > WORLD_TOP)
			position.y = WORLD_TOP;
		update();
		
		//Determine the change is camera positioning for parallax.
		dx = last_x - position.x;
		dy = last_y - position.y;
		
		last_x = position.x;
		last_y = position.y;
	}
}
