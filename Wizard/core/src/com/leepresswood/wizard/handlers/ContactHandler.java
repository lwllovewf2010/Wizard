package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leepresswood.wizard.world.entities.B2DSPackage;
import com.leepresswood.wizard.world.entities.living.LivingEntity;
import com.leepresswood.wizard.world.entities.spells.Spell;

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
			parseContact(contact);
   }
	
	/**
	 * Call this method to determine if there is an effect relationship between the two entities.
	 * @param contact Reference to the contact.
	 */
	private void effectPreprocess(Contact contact)
	{
		//Get the contact bytes.
		a = ((B2DSPackage) contact.getFixtureA().getBody().getUserData()).contact;
		b = ((B2DSPackage) contact.getFixtureB().getBody().getUserData()).contact;
		
		//For effect processing, the only requirement is that both A and B are not references to the ground because the ground does not have contact effects done to it. This could eventually change.
		contact.setEnabled(a != GROUND && b != GROUND);
	}
	
	private void parseContact(Contact contact)
	{
		//At this point, we are guaranteed to have two non-ground GameEntities. Utilize the doHit() methods in each.
		getA(contact, B2DSPackage.class).entity.doHit(getB(contact, B2DSPackage.class).entity);
		getB(contact, B2DSPackage.class).entity.doHit(getA(contact, B2DSPackage.class).entity);
	}
	
	private <T> T getA(Contact contact, Class<T> type)
	{
		return type.cast(contact.getFixtureA().getBody().getUserData());
	}
	
	private <T> T getB(Contact contact, Class<T> type)
	{
		return type.cast(contact.getFixtureB().getBody().getUserData());
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
	
	/**
	 * This is the method that should be called to allow or disallow a physical reaction between a contact pairing.
	 * @param contact Reference to the contact.
	 */
	private void physicsPreprocess(Contact contact)
	{
		a = ((B2DSPackage) contact.getFixtureA().getBody().getUserData()).contact;
		b = ((B2DSPackage) contact.getFixtureB().getBody().getUserData()).contact;
		
		//Ground affects everything but transparent spells.
		if(a == GROUND && b != SPELL_TRANSPARENT || b == GROUND && a != SPELL_TRANSPARENT)
			contact.setEnabled(true);
		
		//Transparent spells do not physically knock back anyone.
		else if(a == SPELL_TRANSPARENT || b == SPELL_TRANSPARENT)
			contact.setEnabled(false);
		
		//Solid spells do not hit the player.
		else if(a == PLAYER && b == SPELL_SOLID || b == PLAYER && a == SPELL_SOLID)
			contact.setEnabled(false);
		
		//Anything else should cause knockback.
		else
			contact.setEnabled(true);
	}

	@Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
   }
}
