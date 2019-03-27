package com.monidoor.weder.adventure.Room;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;
import com.monidoor.weder.adventure.Sprites.Samus;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class RoomMatrix {
    private RoomList rows;

    private Samus samus;
    private GameScreen screen;

    public RoomMatrix(Samus samus, GameScreen screen) {
        rows = new RoomList();
        this.samus = samus;
        this.screen = screen;
        FileHandle dirHandle = null;
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            dirHandle = Gdx.files.internal("rooms");

        for (FileHandle handle : dirHandle.list()) {
            String name = handle.name();
            if (name.contains("Map")) {
                name = name.split(".tmx")[0];
                String[] pathSplit = name.split("_");
                rows.add(new Room(name, rows.size(), 0));
            }
        }
    }

    public Room setRoomByMap(String map) {
        return rows.getRoom(map);
    }

    public Room currentRoom;

    public EventListener RoomListener;

    private List<Room> getPossibleRooms() {
        List<Room> rooms = new ArrayList<Room>();
        if (currentRoom.x > 0)
            rooms.add(rows.getRoom(currentRoom.x - 1));
        if (currentRoom.x < rows.size() - 1)
            rooms.add((rows.getRoom(currentRoom.x + 1)));

        return rooms;
    }

    public Room getNexRoom() {
        List<Room> rooms = getPossibleRooms();
        float test = screen.gamePort.getScreenWidth() / Adventure.PPM;
        float t = samus.getX() * Adventure.PPM;
        return rooms.get(0);
    }
}

class RoomList  {
    private List<Room> rooms;

    public RoomList() {
        rooms = new ArrayList<Room>();
    }

    public Room getRoom(int i) {
        return rooms.get(i);
    }

    public int contains(Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.size() > i && rooms.get(i) != null && room == rooms.get(i))
                return i;
        }

        return -1;
    }

    public void add(Room room) {
        rooms.add(room);
    }

    public boolean containsX(int index) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.size() > i && rooms.get(i) != null && rooms.get(i).x == index)
                return true;
        }
        return false;
    }

    public Room getRoom(String map) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.size() > i && rooms.get(i) != null && rooms.get(i).getTileMap().equals(map))
                return rooms.get(i);
        }

        return null;
    }

    public int size() {
        return rooms.size();
    }
}
