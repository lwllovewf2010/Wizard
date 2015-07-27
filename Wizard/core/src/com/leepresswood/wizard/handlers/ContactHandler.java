package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leepresswood.wizard.world.entities.B2DSPackage;
import com.leepresswood.wizard.world.entities.living.LivingEntity;

public class ContactHandler implements ContactListener
{
	public static final byte GROUND = 1;
	public static final byte SPELL_TRANSPARENT = 2;
	public static final byte SPELL_SOLID = 3;
	public static final byte ENEMY = 4;
	public static final byte PLAYER = 5;
	
	//Contact data.
	private byte a, b;
	
	@Override
   public void beginContact(Contact contact)
   {//This is called before the physical contact has happened. Set the effects that will be done.
		effectPreprocess(contact);
		if(contact.isEnabled())
		{//At this point, we are guaranteed to have two non-ground GameEntities. Utilize the doHit() methods in each if possible.
			if(getA(contact, B2DSPackage.class).entity.active && getB(contact, B2DSPackage.class).entity.active)
			{
				getA(contact, B2DSPackage.class).entity.doHit(getB(contact, B2DSPackage.class).entity);
				getB(contact, B2DSPackage.class).entity.doHit(getA(contact, B2DSPackage.class).entity);
			}
		}
		else
		{//Contact was with the ground. We want the living entities to be able to jump again if the collision was done in the correct way.
			if(a == GROUND && getB(contact, B2DSPackage.class).entity instanceof LivingEntity)
			{
				((LivingEntity) getB(contact, B2DSPackage.class).entity).doHit(getA(contact, B2DSPackage.class).body);
			}
			else if(b == GROUND && getA(contact, B2DSPackage.class).entity instanceof LivingEntity)
			{
				((LivingEntity) getA(contact, B2DSPackage.class).entity).doHit(getB(contact, B2DSPackage.class).body);
			}
		}
   }

	@Override
   public void endContact(Contact contact)
   {
   }

	@Override
   public void preSolve(Contact contact, Manifold oldManifold)
   {//This is called while processing the physics. Disable the contact if necessary.
		physicsPreprocess(contact);
   }

	@Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
   }
	
	/**
	 * Call this method to determine if there is an effect relationship between the two entities.
	 * @param contact Reference to the contact.
	 */
	private void effectPreprocess(Contact contact)
	{
		//Get the contact bytes.
		a = getA(contact, B2DSPackage.class).contact;
		b = getB(contact, B2DSPackage.class).contact;
		
		//For effect processing, the only requirement is that both A and B are not references to the ground because the ground does not have contact effects done to it. This could eventually change.
		contact.setEnabled(a != GROUND && b != GROUND);
	}
	
	private <T> T getA(Contact contact, Class<T> type)
	{
		return type.cast(contact.getFixtureA().getBody().getUserData());
	}
	
	private <T> T getB(Contact contact, Class<T> type)
	{
		return type.cast(contact.getFixtureB().getBody().getUserData());
	}	
	
	/**
	 * This is the method that should be called to allow or disallow a physical reaction between a contact pairing.
	 * @param contact Reference to the contact.
	 */
	private void physicsPreprocess(Contact contact)
	{
		//Get the contact bytes.
		a = getA(contact, B2DSPackage.class).contact;
		b = getB(contact, B2DSPackage.class).contact;
		
		//Transparent spells do not physically knock back anyone.
		if(a == SPELL_TRANSPARENT || b == SPELL_TRANSPARENT)
			contact.setEnabled(false);
		
		//Solid spells do not hit the player.
		else if(a == PLAYER && b == SPELL_SOLID || b == PLAYER && a == SPELL_SOLID)
			contact.setEnabled(false);
		
		//The same entities do not run into each other.
		else if(a == b)
			contact.setEnabled(false);
		
		//Solid spells are not affected by friction.
		if(a == SPELL_SOLID || b == SPELL_SOLID)
			contact.setFriction(0f);
	}
}
