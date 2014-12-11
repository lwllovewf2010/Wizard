package com.leepresswood.wizard.player.upperlevel;

import com.leepresswood.wizard.Assets;


public abstract class Wizard extends Player
{
	public float mana_max;
	  public float mana_current;
	  
	  public Wizard(Assets assets, float x, float y)
	  {
		  super(assets, x, y);
	  }

	  public abstract void cast();
}
