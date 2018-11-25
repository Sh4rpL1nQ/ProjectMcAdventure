package com.monidoor.weder.adventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private Adventure game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer renderer;

    public GameScreen(Adventure game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Adventure.V_WIDTH, Adventure.V_HEIGHT, gameCam);
        mapLoader = new TmxMapLoader();
        //tileMap = mapLoader.load("MetroidWorld1.tmx");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    }
}
