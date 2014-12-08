package com.leepresswood.wizard;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets extends AssetManager
{	
	//public static final int TEXTURE_BALL = 1;
	public static final int MAP_TEST = -1;
	
	public Assets()
	{
		//load("entities/ball.png", Texture.class);
		
		//Maps
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		load("maps/map.tmx", TiledMap.class);
	}
	
	public Texture getTexture(int texture)
	{
		switch(texture)
		{
			//case TEXTURE_BALL:
			//	return get("entities/ball.png", Texture.class);
			default:
				return null;
		}
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
