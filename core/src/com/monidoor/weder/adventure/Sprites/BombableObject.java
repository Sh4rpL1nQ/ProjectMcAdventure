package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class BombableObject extends SpecialObject {

    public BombableObject(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);

    }
}
