package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public class PotionItem extends Item {

    float x, y;
    public PotionItem(GameScreen screen, float x, float y) {
        super(screen, x, y);
        this.x = x;
        this.y = y;
        setRegion(new TextureRegion(new Texture("sample_player.png"), 16, 16));
        setBounds(getX(), getY(), 16 / Adventure.PPM, 16 / Adventure.PPM);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Adventure.PPM);
        fdef.filter.categoryBits = Adventure.ITEM_BIT;
        fdef.filter.maskBits = Adventure.SAMUS_BIT | Adventure.GROUND_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Samus samus) {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}