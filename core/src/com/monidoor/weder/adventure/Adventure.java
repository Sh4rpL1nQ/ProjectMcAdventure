package com.monidoor.weder.adventure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Adventure extends Game {

	public static SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 240;
	public static final float PPM = 100f;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short SAMUS_BIT = 2;
	public static final short Door_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	public static final short ITEM_BIT = 128;
	public static final short BULLET_BIT = 256;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
