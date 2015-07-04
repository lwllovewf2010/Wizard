package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.leepresswood.wizard.data.Assets;
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
	public final float WORLD_PLAYER_Y_SKEW = 104.5f;						//Higher values of this will move the player closer to the vertical middle. Lower values will move the player down. Anything less than 2 will put the player off the screen.
	
	//Others.
	public float GROUND;																//Temporary value for the Y-value of the ground. Eventually want to read the blocks themselves and see if they are solid.
	public float GRAVITY;															//Value of gravity. Set by the map. May seek to change eventually (faster/slower falling, or maybe reverse gravity)
	public float pixel_size;														//Width/Height of each block.
	
	public MapCameraHandler(Universe universe)
	{
		this.universe = universe;
		
		//Get map data. See here: https://github.com/libgdx/libgdx/wiki/Tile-maps
		map = universe.screen.game.assets.getMap(Assets.MAP_TEST);
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
		WORLD_TOP = WORLD_TOTAL_VERTICAL - WORLD_BOTTOM;
		WORLD_LEFT = zoom * viewportWidth / 2f;
		WORLD_RIGHT = WORLD_TOTAL_HORIZONTAL - WORLD_LEFT;
		
		//Make the map blocks. These can be used to get the tile that is being highlighted.
		universe.world_handler.handlerInit(WORLD_TOTAL_HORIZONTAL, WORLD_TOTAL_VERTICAL, GRAVITY);
		for(int j = 0; j < WORLD_TOTAL_VERTICAL; j++)
		{
			for(int i = 0; i < WORLD_TOTAL_HORIZONTAL; i++)
			{
				if(collision_layer.getCell(i, j) != null)
					universe.world_handler.addBlockToWorld(i, j, 1f, 1f);
			}
		}
	}
	
	/**
	 * Check the camera's position for correctness. It should not go off the world's bounds.
	 */
	public void setCameraBounds()
	{
		//First, set the camera to the player's position.
		position.x = universe.entity_handler.player.parts[0].sprite.getX() + universe.entity_handler.player.parts[0].sprite.getWidth() / 2f;
		position.y = universe.entity_handler.player.parts[0].sprite.getY() + universe.entity_handler.player.parts[0].sprite.getHeight() / 2f + zoom * viewportHeight / WORLD_PLAYER_Y_SKEW;
		
		//If this moves off the world's bounds, correct it.
		if(position.x < WORLD_LEFT)
			position.x = WORLD_LEFT;
		else if(position.x > WORLD_RIGHT)
			position.x = WORLD_RIGHT;
		else if(position.y < WORLD_BOTTOM)
			position.y = WORLD_BOTTOM;
		else if(position.y > WORLD_TOP)
			position.y = WORLD_TOP;
		update();
	}
}
