package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public class SmallMonster extends Enemy
{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;

    public SmallMonster(GameScreen screen, float x, float y) {
        super(screen, x, y);
        //frames = new Array<TextureRegion>();
        //TextureRegion txt = new TextureRegion(new Texture("sample_enemy.png"), 16, 16);
        //frames.add(txt);
        //frames.add(txt);
        //walkAnimation = new Animation(0.4f, frames);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy_one"), i * 32, -19, 32, 87));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        ATTACK_POINTS = 30;
        setBounds(getX(), getY(), 32 / Adventure.PPM, 87 / Adventure.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("enemy_one"), 32, -19, 32, 87));

            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            handleRegion((TextureRegion)walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    private void handleRegion(TextureRegion region) {
        if((b2body.getLinearVelocity().x < 0) && !region.isFlipX()){
            region.flip(true, false);
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0) && region.isFlipX()){
            region.flip(true, false);
        }

        setRegion(region);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Adventure.PPM);
        fdef.filter.categoryBits = Adventure.ENEMY_BIT;
        fdef.filter.maskBits =
                Adventure.GROUND_BIT |
                Adventure.ENEMY_BIT |
                Adventure.OBJECT_BIT |
                Adventure.SAMUS_BIT |
                Adventure.BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitten() {
        setToDestroy = true;
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true, false);
    }
}
