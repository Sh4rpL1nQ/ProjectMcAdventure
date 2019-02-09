package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public abstract class Item extends Sprite {
    protected GameScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(GameScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;
        setPosition(x, y);
        defineItem();
    }

    public abstract void defineItem();
    public abstract void use(Samus mario);

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy(){
        toDestroy = true;
    }
}