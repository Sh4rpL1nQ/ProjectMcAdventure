package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.monidoor.weder.adventure.GameScreen;

public class ShootableObject extends SpecialObject {

    public ShootableObject(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
    }
}
