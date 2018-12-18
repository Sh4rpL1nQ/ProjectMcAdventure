package com.monidoor.weder.adventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monidoor.weder.adventure.Creation.WorldCreator;
import com.monidoor.weder.adventure.Scenes.Controller;
import com.monidoor.weder.adventure.Sprites.Samus;

public class GameScreen implements Screen {

    private Adventure game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer renderer;
    private Samus samus;
    private Controller controller;

    private World world;
    private Box2DDebugRenderer box2DRenderer;
    private WorldCreator creator;

    public GameScreen(Adventure game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Adventure.V_WIDTH / Adventure.PPM, Adventure.V_HEIGHT / Adventure.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        tileMap = mapLoader.load("Map1_Room1.tmx");

        renderer = new OrthogonalTiledMapRenderer(tileMap, 1 / Adventure.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        creator = new WorldCreator(world, tileMap);
        creator.generate();

        samus = new Samus(world);

        box2DRenderer = new Box2DDebugRenderer();

        controller = new Controller();
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);
        samus.update(dt);
        gameCam.position.x = samus.b2Body.getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput(float dt) {
        if (controller.isJumpPressed())
            samus.b2Body.applyLinearImpulse(new Vector2(0, 4f), samus.b2Body.getWorldCenter(), true);
        if (controller.isRightPressed() && samus.b2Body.getLinearVelocity().x <= 2)
            samus.b2Body.applyLinearImpulse(new Vector2(0.1f, 0f), samus.b2Body.getWorldCenter(), true);
        if (controller.isLeftPressed() && samus.b2Body.getLinearVelocity().x >= -2)
            samus.b2Body.applyLinearImpulse(new Vector2(-0.1f, 0f), samus.b2Body.getWorldCenter(), true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        renderer.render();
        controller.draw();

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        samus.draw(game.batch);
        game.batch.end();

        box2DRenderer.render(world, gameCam.combined);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tileMap.dispose();
        renderer.dispose();
        box2DRenderer.dispose();
        world.dispose();
    }
}
