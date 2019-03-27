package com.monidoor.weder.adventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monidoor.weder.adventure.Room.Room;
import com.monidoor.weder.adventure.Room.RoomMatrix;
import com.monidoor.weder.adventure.Scenes.Hud;
import com.monidoor.weder.adventure.Sprites.Enemy;
import com.monidoor.weder.adventure.Sprites.Item;
import com.monidoor.weder.adventure.Sprites.SmallMonster;
import com.monidoor.weder.adventure.Sprites.SpecialObject;
import com.monidoor.weder.adventure.Util.WorldContactListener;
import com.monidoor.weder.adventure.Util.WorldCreator;
import com.monidoor.weder.adventure.Scenes.Controller;
import com.monidoor.weder.adventure.Sprites.Samus;

public class GameScreen implements Screen {

    private Adventure game;
    private OrthographicCamera gameCam;
    public Viewport gamePort;
    private TmxMapLoader mapLoader;
    public TiledMap tileMap;
    public OrthogonalTiledMapRenderer renderer;
    private Samus samus;
    private Controller controller;
    public RoomMatrix roomMatrix;

    private World world;
    private Box2DDebugRenderer box2DRenderer;
    private WorldCreator creator;
    private TextureAtlas atlas;

    private Hud hud;

    public GameScreen(Adventure game) {
        this.game = game;
        atlas = new TextureAtlas("sprites/packed/samus_and_enemy.txt");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Adventure.V_WIDTH / Adventure.PPM, Adventure.V_HEIGHT / Adventure.PPM, gameCam);
        mapLoader = new TmxMapLoader();

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        samus = new Samus(this);

        roomMatrix = new RoomMatrix(samus, this);
        roomMatrix.currentRoom = roomMatrix.setRoomByMap("Map1_Room2");

        String b = roomMatrix.currentRoom.getTileMap() + ".tmx";
        tileMap = mapLoader.load( "rooms/"+b);
        renderer = new OrthogonalTiledMapRenderer(tileMap, 1 / Adventure.PPM);

        creator = new WorldCreator(this);
        creator.generate();

        box2DRenderer = new Box2DDebugRenderer();

        controller = new Controller();

        world.setContactListener(new WorldContactListener());

        hud = new Hud(game.batch);
    }


    public void InitMap(TiledMap map) {
        world.dispose();
        world = new World(new Vector2(0, -10), true);
        creator = new WorldCreator(this);
        creator.generate();
        samus.world = world;
        samus.defineSamus();
        samus.setPosition(100 / Adventure.PPM, 32 / Adventure.PPM);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TiledMap getMap() {
        return tileMap;
    }

    public World getWorld() {
        return world;
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        samus.update(dt);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < samus.getX() + 224 / Adventure.PPM) {
                enemy.b2body.setActive(true);
            }
        }
        for(Item item : creator.getItems())
            item.update(dt);
        for(SpecialObject inter : creator.getInteractable())
            inter.update(dt);
        if(samus.currentState != Samus.State.DEAD) {
            gameCam.position.x = samus.b2Body.getPosition().x;
        }

        gameCam.update();
        hud.update(samus.health);
        renderer.setView(gameCam);
    }

    private void handleInput(float dt) {
        if (controller.isJumpPressed())
            samus.jump();
        if (controller.isRightPressed() && samus.b2Body.getLinearVelocity().x <= 2)
            samus.b2Body.applyLinearImpulse(new Vector2(0.1f, 0f), samus.b2Body.getWorldCenter(), true);
        if (controller.isLeftPressed() && samus.b2Body.getLinearVelocity().x >= -2)
            samus.b2Body.applyLinearImpulse(new Vector2(-0.1f, 0f), samus.b2Body.getWorldCenter(), true);
        if (controller.isShootPressed())
            if (samus.getBullets().size < 4) {
                samus.fire();
            }
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
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Item item : creator.getItems())
            item.draw(game.batch);
        for (SpecialObject inter : creator.getInteractable())
            inter.draw(game.batch);
        game.batch.end();
        box2DRenderer.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()) {
            dispose();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(game));
        }
    }

    public boolean gameOver(){
        if(samus.currentState == Samus.State.DEAD && samus.getStateTimer() > 3){
            return true;
        }
        return false;
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
