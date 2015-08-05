package com.leepresswood.wizard.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Manages the loading of the game's assets.
 * @author Lee
 */
public class AssetHelper extends AssetManager
{
	public static final int MAP_TEST = 0;
	
	//Extracted strings.
	//Spells.
	public static final String TEXTURE_SPELL_AETHER = "textures/spells/aether.png";
	public static final String TEXTURE_SPELL_FIREBALL = "textures/spells/fireball.png";
	public static final String TEXTURE_SPELL_HOLD = "textures/spells/hold.png";
	
	//Enemies.
	
	
	//Parallax.
	public static final String TEXTURE_PARALLAX_BACK1 = "textures/parallax/smallforest/back1.png";
	public static final String TEXTURE_PARALLAX_BACK2 = "textures/parallax/smallforest/back2.png";
	public static final String TEXTURE_PARALLAX_BACK3 = "textures/parallax/smallforest/back3.png";
	public static final String TEXTURE_PARALLAX_MIDDLE1 = "textures/parallax/smallforest/middle1.png";
	public static final String TEXTURE_PARALLAX_MIDDLE2 = "textures/parallax/smallforest/middle2.png";
	public static final String TEXTURE_PARALLAX_SKY = "textures/parallax/smallforest/sky.png";
	
	public AssetHelper()
	{
		//Maps.
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		load("maps/map.tmx", TiledMap.class);
		
		//Hold.
		load("textures/hold.png", Texture.class);
		load("textures/bosses/hold.png", Texture.class);
		load("textures/enemies/hold.png", Texture.class);
		load("textures/players/hold.png", Texture.class);
		
		//GUI.
		load("textures/gui/spellbook.png", Texture.class);
		
		//Enemies.
		load("textures/enemies/back1.png", Texture.class);
		
		//Spell.
		load(TEXTURE_SPELL_AETHER, Texture.class);
		load(TEXTURE_SPELL_FIREBALL, Texture.class);
		load(TEXTURE_SPELL_HOLD, Texture.class);
		
		//Parallax. http://imageresize.org/Default.aspx
		load(TEXTURE_PARALLAX_BACK1, Texture.class);
		load(TEXTURE_PARALLAX_BACK2, Texture.class);
		load(TEXTURE_PARALLAX_BACK3, Texture.class);
		load(TEXTURE_PARALLAX_MIDDLE1, Texture.class);
		load(TEXTURE_PARALLAX_MIDDLE2, Texture.class);
		load(TEXTURE_PARALLAX_SKY, Texture.class);
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
