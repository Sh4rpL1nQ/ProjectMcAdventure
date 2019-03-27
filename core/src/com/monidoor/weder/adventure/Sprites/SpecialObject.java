package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public abstract class SpecialObject extends Sprite {

    protected World world;
    protected GameScreen screen;
    public Body b2body;
    protected boolean destroyed;

    public SpecialObject(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);

        defineObject();
    }

    public abstract void open();
    public abstract void update(float dt);
    public abstract void defineObject();
}
