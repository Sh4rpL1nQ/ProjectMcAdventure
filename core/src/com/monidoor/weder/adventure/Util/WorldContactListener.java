package com.monidoor.weder.adventure.Util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.Sprites.Bullet;
import com.monidoor.weder.adventure.Sprites.Enemy;
import com.monidoor.weder.adventure.Sprites.Item;
import com.monidoor.weder.adventure.Sprites.Samus;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){
            case Adventure.ENEMY_BIT | Adventure.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Adventure.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Adventure.SAMUS_BIT | Adventure.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Adventure.SAMUS_BIT) {
                    ((Samus) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                }
                else
                    ((Samus) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case Adventure.ENEMY_BIT | Adventure.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case Adventure.ITEM_BIT | Adventure.SAMUS_BIT:
                if(fixA.getFilterData().categoryBits == Adventure.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Samus) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Samus) fixA.getUserData());
                break;
            case Adventure.BULLET_BIT | Adventure.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Adventure.BULLET_BIT)
                    ((Bullet)fixA.getUserData()).setToDestroy();
                else
                    ((Bullet)fixB.getUserData()).setToDestroy();
                break;
            case Adventure.ENEMY_BIT | Adventure.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == Adventure.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).hitten();
                else
                    ((Enemy)fixB.getUserData()).hitten();
                break;
            default: break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
