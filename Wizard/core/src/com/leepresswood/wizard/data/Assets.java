package com.leepresswood.wizard.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets extends AssetManager
{
	public static final int MAP_TEST = 0;
	
	public Assets()
	{
		
		//Maps
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		load("maps/map.tmx", TiledMap.class);
	}
	
	public Texture getTexture(int texture)
	{
		Texture t;
		switch(texture)
		{
			case 1:
				t = new Texture("");
				break;
			default:
				System.out.println("Error: Texture type " + texture + " not found.");
				return null;
		}
		
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return t;
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
