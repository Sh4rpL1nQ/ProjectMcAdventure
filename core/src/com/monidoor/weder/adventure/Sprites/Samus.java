package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.monidoor.weder.adventure.Adventure;

public class Samus extends Sprite {

    public World world;
    public Body b2Body;

    public Samus(World world) {
        this.world = world;

        defineSamus();
    }

    private void defineSamus() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(100 / Adventure.PPM, 32 / Adventure.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Adventure.PPM);
        fDef.shape = shape;

        b2Body.createFixture(fDef);
    }
}
