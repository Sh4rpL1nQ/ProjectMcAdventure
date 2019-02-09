package com.monidoor.weder.adventure.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.Sprites.Samus;

public class Hud {
    public Stage stage;
    private Viewport viewPort;

    private int health;
    private int numRockets;
    private int numUltiBombs;

    Label healthLabel;
    Label numRocketsLabel;
    Label numUltiBombsLabel;

    public Hud(SpriteBatch batch) {
        health = 100;
        numRockets = 0;
        numUltiBombs = 0;

        viewPort = new FitViewport(Adventure.V_WIDTH, Adventure.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, batch);

        Table t = new Table();
        t.top();
        t.setFillParent(true);

        healthLabel = new Label(health + "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numRocketsLabel = new Label(numRockets + "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numUltiBombsLabel = new Label(numUltiBombs + "", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        t.add(healthLabel).expandX().padTop(10);

        stage.addActor(t);
    }

    public void update(int health) {
        this.health = health;
        healthLabel.setText(health + "");
    }
}
