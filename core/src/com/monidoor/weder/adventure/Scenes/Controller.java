package com.monidoor.weder.adventure.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monidoor.weder.adventure.Adventure;

public class Controller {

    private Viewport viewPort;
    private Stage stage;
    private boolean jumpPressed, shootPressed, leftPressed, rightPressed, upPressed, downPressed;
    private OrthographicCamera cam;

    public Controller() {
        cam = new OrthographicCamera();
        viewPort = new FitViewport(800, 480, cam);
        stage = new Stage(viewPort, Adventure.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.bottom();

        Image leftImage = new Image(new Texture("arrow_left.png"));
        Image rightImage = new Image(new Texture("arrow_right.png"));
        Image upImage = new Image(new Texture("arrow_up.png"));
        Image downImage = new Image(new Texture("arrow_down.png"));
        Image jumpImage = new Image(new Texture("jump.png"));
        Image shootImage = new Image(new Texture("shoot.png"));

        leftImage.setSize(50,50);
        rightImage.setSize(50,50);
        upImage.setSize(50,50);
        downImage.setSize(50,50);
        jumpImage.setSize(50,50);
        shootImage.setSize(50,50);

        leftImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        rightImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        jumpImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = false;
            }
        });

        shootImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = false;
            }
        });

        Table cross = new Table();
        Table action = new Table();
        table.setFillParent(true);

        cross.add();
        cross.add(upImage).size(upImage.getWidth(), upImage.getHeight());
        cross.add();
        cross.row().pad(5, 5, 5, 5);

        cross.add(leftImage).size(leftImage.getWidth(), leftImage.getHeight());
        cross.add();
        cross.add(rightImage).size(rightImage.getWidth(), rightImage.getHeight());
        cross.row().padBottom(5);

        cross.add();
        cross.add(downImage).size(downImage.getWidth(), downImage.getHeight());
        cross.add();

        action.add();
        action.add();
        action.add();
        action.row().pad(5,5,5,5);
        action.add();
        action.add(shootImage).size(shootImage.getWidth(), shootImage.getHeight());
        action.add(jumpImage).size(jumpImage.getWidth(), jumpImage.getHeight());
        action.row().padBottom(5);
        action.add();
        action.add();
        action.add();

        float a = viewPort.getWorldWidth();
        table.add(cross);
        table.add().width(a/6);
        table.add().width(a/6);
        table.add().width(a/6);
        table.add(action);

        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public boolean isJumpPressed() {
        return jumpPressed;
    }

    public boolean isShootPressed() {
        return shootPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void resize(int w, int h) {
        viewPort.update(w, h);
    }
}
