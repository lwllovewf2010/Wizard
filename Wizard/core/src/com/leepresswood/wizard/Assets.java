package com.leepresswood.wizard;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets extends AssetManager
{	
	//public static final int TEXTURE_BALL = 1;
	
	public Assets()
	{
		//load("entities/ball.png", Texture.class);
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
	
	public void destroy()
	{
		dispose();
	}
}
