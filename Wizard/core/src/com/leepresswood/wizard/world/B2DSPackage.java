package com.leepresswood.wizard.world;

import com.leepresswood.wizard.world.entities.GameEntity;

/**
 * The packaged data that is stored in the body.
 * @author Lee
 */
public class B2DSPackage
{
	public GameEntity entity;
	public byte contact;
	
	public B2DSPackage(GameEntity e, byte contact)
	{
		this.entity = e;
		this.contact = contact;
	}
}