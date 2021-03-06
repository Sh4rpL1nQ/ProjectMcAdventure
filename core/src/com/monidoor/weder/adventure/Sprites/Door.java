package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public class Door extends SpecialObject {

    public Door(GameScreen screen, float x, float y, float w, float h) {
        super(screen, x, y);

        setRegion(new TextureRegion(new Texture("door.png"), 16, 64));

        setBounds(w, h, 16 / Adventure.PPM, 64 / Adventure.PPM);
    }

    public boolean toDestroy;

    @Override
    public void open() {
        toDestroy = true;
    }

    @Override
    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    @Override
    public void defineObject() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(16 / 2 / Adventure.PPM,64 / 2 / Adventure.PPM);
        fdef.filter.categoryBits = Adventure.Door_BIT;
        fdef.filter.maskBits =
                Adventure.GROUND_BIT |
                        Adventure.ENEMY_BIT |
                        Adventure.OBJECT_BIT |
                        Adventure.SAMUS_BIT |
                        Adventure.BULLET_BIT;

        fdef.shape = poly;
        b2body.createFixture(fdef).setUserData(this);
    }
}
