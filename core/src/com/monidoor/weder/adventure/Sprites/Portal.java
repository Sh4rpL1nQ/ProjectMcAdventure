package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public class Portal extends SpecialObject {

    public GameScreen screen;
    public Portal(GameScreen screen, float x, float y, float w, float h) {
        super(screen, x, y);
        this.screen = screen;
        setRegion(new TextureRegion(new Texture("portal.png"), 16, 64));

        setBounds(w, h, 16 / Adventure.PPM, 64 / Adventure.PPM);
    }

    public boolean toDestroy;

    @Override
    public void open() {
        TmxMapLoader l = new TmxMapLoader();
        screen.tileMap = l.load("rooms/" + screen.roomMatrix.getNexRoom().getTileMap() + ".tmx");
        screen.renderer = new OrthogonalTiledMapRenderer(screen.tileMap, 1 / Adventure.PPM);
        screen.InitMap(screen.tileMap);
    }

    @Override
    public void update(float dt){

    }

    public void draw(Batch batch){
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
        fdef.filter.categoryBits = Adventure.PORTAL_BIT;
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
