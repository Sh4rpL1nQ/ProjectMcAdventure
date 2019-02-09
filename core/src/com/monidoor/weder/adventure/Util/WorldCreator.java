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
import com.monidoor.weder.adventure.Sprites.BombableObject;
import com.monidoor.weder.adventure.Sprites.Door;
import com.monidoor.weder.adventure.Sprites.Enemy;
import com.monidoor.weder.adventure.Sprites.Item;
import com.monidoor.weder.adventure.Sprites.PotionItem;
import com.monidoor.weder.adventure.Sprites.ShootableObject;
import com.monidoor.weder.adventure.Sprites.SmallMonster;

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

    public WorldCreator(GameScreen screen) {
        world = screen.getWorld();

        tileMap = screen.getMap();

        this.screen = screen;

        bDef = new BodyDef();
        pShape = new PolygonShape();
        fixDef = new FixtureDef();

        smallMonsters = new Array<SmallMonster>();
        items = new Array<PotionItem>();
    }

    public void generate() {
        for (MapObject obj : tileMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);

            body = world.createBody(bDef);

            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.filter.categoryBits = Adventure.GROUND_BIT;
            fixDef.shape = pShape;
            body.createFixture(fixDef);
        }

        for (MapObject obj : tileMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            new BombableObject(screen, rect);
        }

        for (MapObject obj : tileMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);
            body = world.createBody(bDef);
            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.shape = pShape;
            fixDef.filter.categoryBits = Adventure.OBJECT_BIT;
            body.createFixture(fixDef);
            new Door(screen, rect);
        }

        for(MapObject object : tileMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            smallMonsters.add(new SmallMonster(screen, rect.getX() / Adventure.PPM, rect.getY() / Adventure.PPM));
        }

        for(MapObject object : tileMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            items.add(new PotionItem(screen, (rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM));
        }

        for(MapObject object : tileMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);

            body = world.createBody(bDef);

            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.shape = pShape;
            fixDef.filter.categoryBits = Adventure.OBJECT_BIT;
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
}
