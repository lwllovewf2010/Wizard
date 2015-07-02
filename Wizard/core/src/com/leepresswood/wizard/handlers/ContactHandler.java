package com.leepresswood.wizard.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leepresswood.wizard.world.entities.living.LivingEntity;

public class ContactHandler implements ContactListener
{
	@Override
   public void beginContact(Contact contact)
   {
		if(contact.getFixtureA().getBody().getUserData() instanceof LivingEntity)
		{			
			((LivingEntity) contact.getFixtureA().getBody().getUserData()).enemyCollision();
		}
   }

	@Override
   public void endContact(Contact contact)
   {
   }

	@Override
   public void preSolve(Contact contact, Manifold oldManifold)
   {
   }

	@Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
   }
	
}
