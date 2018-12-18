package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.Rectangle;

public class BombableObject extends InteractiveObject {

    protected BombableObject(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
