package com.monidoor.weder.adventure.Room;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.monidoor.weder.adventure.Sprites.Samus;

public class Room {

    private String tileMap;

    public int x;
    public int y;

    public boolean isActivated;

    public Room(String tileMap, int x, int y) {
        this.x = x;
        this.y = y;

        this.tileMap = tileMap;
    }

    public String getTileMap() {
        return tileMap;
    }
}
