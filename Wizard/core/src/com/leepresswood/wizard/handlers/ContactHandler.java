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
   {
		if(a == GROUND || b == GROUND)
		{//Nothing needs to happen to the ground for now.
		}
		if(a == SPELL_SOLID || a == SPELL_TRANSPARENT)
		{
			
		}
		if(b == SPELL_SOLID || b == SPELL_TRANSPARENT)
		{
			((Spell) ((B2DSPackage) contact.getFixtureA().getBody().getUserData()).entity).doHit(((B2DSPackage) contact.getFixtureB().getBody().getUserData()).entity);
		}
		if(a == PLAYER || a == ENEMY)
		{
			if(b == GROUND)
				((LivingEntity) getA(contact, B2DSPackage.class).entity).doHit(getB(contact, B2DSPackage.class).body);
		}
		if(b == PLAYER || b == ENEMY)
		{
			if(a == GROUND)
				((LivingEntity) getB(contact, B2DSPackage.class).entity).doHit(getA(contact, B2DSPackage.class).body);
		}
		
		
		//System.out.println("A = " + contact.getFixtureA().getBody().getUserData() + ", B = " + contact.getFixtureB().getBody().getUserData());
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
   {
		a = ((B2DSPackage) contact.getFixtureA().getBody().getUserData()).contact;
		b = ((B2DSPackage) contact.getFixtureB().getBody().getUserData()).contact;
		
		//Determine if the contact is allowed.
		//Ground.
		if(a == GROUND || b == GROUND)
			contact.setEnabled(b != SPELL_TRANSPARENT);
		else if(b == GROUND)
			contact.setEnabled(a != SPELL_TRANSPARENT);
		
		//Spells.
		//Note: Ground has already been processed, so let's not worry about it.
		else if(a == SPELL_TRANSPARENT || a == SPELL_SOLID)
			contact.setEnabled(b != PLAYER && b != SPELL_TRANSPARENT && b != SPELL_SOLID);
		else if(b == SPELL_TRANSPARENT || b == SPELL_SOLID)
			contact.setEnabled(a != PLAYER && a != SPELL_TRANSPARENT && a != SPELL_SOLID);
		
		//Enemies.
		//Note: Ground and spells processed, so we only need to set the player interaction.
		else if(a == ENEMY)
			contact.setEnabled(a == PLAYER);
		else if(b == ENEMY)
			contact.setEnabled(b == PLAYER);
   }

	@Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
   }
	
}
