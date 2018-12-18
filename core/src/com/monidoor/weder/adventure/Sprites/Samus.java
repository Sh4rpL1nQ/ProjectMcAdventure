package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        TextureRegion txt = new TextureRegion(new Texture("sample_player.png"), 16, 16);
        setBounds(0,0,16 / Adventure.PPM,16 / Adventure.PPM);
        setRegion(txt);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
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
