package com.leepresswood.wizard.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Manages the loading of the game's assets.
 * @author Lee
 */
public class Assets extends AssetManager
{	
	public static final int MAP_TEST = 0;
	
	public Assets()
	{
		//Maps.
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		load("maps/map.tmx", TiledMap.class);
		
		//Hold textures.
		load("textures/hold.png", Texture.class);
		load("textures/bosses/hold.png", Texture.class);
		load("textures/enemies/hold.png", Texture.class);
		load("textures/players/hold.png", Texture.class);
		
		//Enemies.
		load("textures/enemies/back1.png", Texture.class);
		
		//Spell textures.
		load("textures/spells/aether.png", Texture.class);
		load("textures/spells/fireball.png", Texture.class);
		load("textures/spells/hold.png", Texture.class);
		
		//Parallax. http://imageresize.org/Default.aspx
		load("textures/parallax/smallforest/back1.png", Texture.class);
		load("textures/parallax/smallforest/back2.png", Texture.class);
		load("textures/parallax/smallforest/back3.png", Texture.class);
		load("textures/parallax/smallforest/middle1.png", Texture.class);
		load("textures/parallax/smallforest/middle2.png", Texture.class);
		load("textures/parallax/smallforest/sky.png", Texture.class);
	}
	
	public TiledMap getMap(int map)
	{
		switch(map)
		{
			case MAP_TEST:
				return get("maps/map.tmx", TiledMap.class);
			default:
				return null;
		}
	}
	
	public void destroy()
	{
		dispose();
	}
}
