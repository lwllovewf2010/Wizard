package com.leepresswood.wizard.world.entities.player.types;

import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.world.GameWorld;
import com.leepresswood.wizard.world.entities.player.Player;

public class AirWizard extends Player
{

	public AirWizard(GameWorld world, float x, float y, Element data)
	{
		super(world, x, y, data);
	}

}
