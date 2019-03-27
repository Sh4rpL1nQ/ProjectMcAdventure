package com.monidoor.weder.adventure.Util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;
import com.monidoor.weder.adventure.Sprites.Door;
import com.monidoor.weder.adventure.Sprites.Enemy;
import com.monidoor.weder.adventure.Sprites.Item;
import com.monidoor.weder.adventure.Sprites.Portal;
import com.monidoor.weder.adventure.Sprites.PotionItem;
import com.monidoor.weder.adventure.Sprites.SmallMonster;
import com.monidoor.weder.adventure.Sprites.SpecialObject;

public class WorldCreator {

    private World world;
    private TiledMap tileMap;
    private BodyDef bDef;
    private PolygonShape pShape;
    private FixtureDef fixDef;
    private Body body;
    private GameScreen screen;
    private Array<SmallMonster> smallMonsters;
    private Array<PotionItem> items;
    private Array<Door> doors;
    private Array<Portal> portals;

    public WorldCreator(GameScreen screen) {
        world = screen.getWorld();
        this.screen = screen;

        tileMap = screen.getMap();
        bDef = new BodyDef();
        pShape = new PolygonShape();
        fixDef = new FixtureDef();

        smallMonsters = new Array<SmallMonster>();
        items = new Array<PotionItem>();
        doors = new Array<Door>();
        portals = new Array<Portal>();
    }

    public void generate() {
        defaultGenerate(3, Adventure.GROUND_BIT);
        defaultGenerate(6, Adventure.OBJECT_BIT);

        for (MapObject obj : tileMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            doors.add(new Door(screen, (rect.getX() + rect.getWidth() / 2) / Adventure.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Adventure.PPM,
                    rect.getX() / Adventure.PPM,
                    rect.getY() / Adventure.PPM));
        }

        for (MapObject obj : tileMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            portals.add(new Portal(screen, (rect.getX() + rect.getWidth() / 2) / Adventure.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Adventure.PPM,
                    rect.getX() / Adventure.PPM,
                    rect.getY() / Adventure.PPM));
        }

        for(MapObject object : tileMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            smallMonsters.add(new SmallMonster(screen, rect.getX() / Adventure.PPM, rect.getY() / Adventure.PPM));
        }

        for(MapObject object : tileMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            items.add(new PotionItem(screen, (rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM));
        }
    }

    private void defaultGenerate(int layer, short bit) {
        for(MapObject object : tileMap.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);

            body = world.createBody(bDef);

            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.shape = pShape;
            fixDef.filter.categoryBits = bit;
            body.createFixture(fixDef);
        }
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(smallMonsters);
        return enemies;
    }

    public Array<Item> getItems(){
        Array<Item> itemss = new Array<Item>();
        itemss.addAll(items);
        return itemss;
    }

    public Array<SpecialObject> getInteractable(){
        Array<SpecialObject> itemss = new Array<SpecialObject>();
        itemss.addAll(portals);
        itemss.addAll(doors);
        return itemss;
    }
}
