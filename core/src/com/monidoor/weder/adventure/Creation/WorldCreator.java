package com.monidoor.weder.adventure.Creation;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.monidoor.weder.adventure.Adventure;

public class WorldCreator {

    private World world;
    private TiledMap tileMap;
    private BodyDef bDef;
    private PolygonShape pShape;
    private FixtureDef fixDef;
    private Body body;

    public WorldCreator(World world, TiledMap tileMap) {
        this.world = world;
        this.tileMap = tileMap;

        bDef = new BodyDef();
        pShape = new PolygonShape();
        fixDef = new FixtureDef();
    }

    public void generate() {
        for (MapObject obj : tileMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);

            body = world.createBody(bDef);

            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.shape = pShape;
            body.createFixture(fixDef);
        }

        for (MapObject obj : tileMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Adventure.PPM, (rect.getY() + rect.getHeight() / 2) / Adventure.PPM);

            body = world.createBody(bDef);

            pShape.setAsBox(rect.getWidth() / 2 / Adventure.PPM, rect.getHeight() / 2 / Adventure.PPM);
            fixDef.shape = pShape;
            body.createFixture(fixDef);
        }
    }
}
