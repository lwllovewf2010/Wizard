package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leepresswood.wizard.world.entities.GameEntity;
import com.leepresswood.wizard.world.entities.spells.Spell;

public class ContactHandler implements ContactListener
{
	public static final byte GROUND = 1;
	public static final byte SPELL_TRANSPARENT = 2;
	public static final byte SPELL_SOLID = 3;
	public static final byte ENEMY = 4;
	public static final byte PLAYER = 5;
	
	@Override
   public void beginContact(Contact contact)
   {
		if(contact.getFixtureA().getBody().getUserData() instanceof Spell)
		{
			((Spell) contact.getFixtureA().getBody().getUserData()).doHit((GameEntity) contact.getFixtureB().getBody().getUserData());
			
		}
		
		System.out.println(contact.getFixtureA().getBody().getUserData());
		System.out.println(contact.getFixtureB().getBody().getUserData());
   }

	@Override
   public void endContact(Contact contact)
   {
   }

	@Override
   public void preSolve(Contact contact, Manifold oldManifold)
   {
		//Determine if the contact is allowed.
		contact.setEnabled(false);
   }

	@Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
   }
	
}
