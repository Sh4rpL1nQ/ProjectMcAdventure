package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public abstract class SpecialObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public SpecialObject(GameScreen screen, Rectangle bounds) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        this.bounds = bounds;

        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Adventure.PPM, (bounds.getY() + bounds.getHeight() / 2) / Adventure.PPM);

        body = world.createBody(bDef);

        pShape.setAsBox(bounds.getWidth() / 2 / Adventure.PPM, bounds.getHeight() / 2 / Adventure.PPM);
        fDef.shape = pShape;
        fixture = body.createFixture(fDef);
    }
}
