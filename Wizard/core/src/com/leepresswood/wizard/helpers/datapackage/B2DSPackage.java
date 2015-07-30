package com.leepresswood.wizard.helpers.datapackage;

import com.badlogic.gdx.physics.box2d.Body;
import com.leepresswood.wizard.world.entities.GameEntity;

/**
 * The packaged data that is stored in the body.
 * @author Lee
 */
public class B2DSPackage
{
	public Body body;
	public GameEntity entity;
	public byte contact;
	
	public B2DSPackage(GameEntity entity, byte contact)
	{
		this.entity = entity;
		this.contact = contact;
	}
	
	public B2DSPackage(Body body, byte contact)
	{
		this.body = body;
		this.contact = contact;
	}
}