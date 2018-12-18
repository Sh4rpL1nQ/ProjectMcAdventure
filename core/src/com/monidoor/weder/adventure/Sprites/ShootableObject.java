package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class ShootableObject extends SpecialObject {

    public ShootableObject(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
