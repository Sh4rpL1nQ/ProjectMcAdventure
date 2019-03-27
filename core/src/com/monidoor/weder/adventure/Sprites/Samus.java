package com.monidoor.weder.adventure.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.monidoor.weder.adventure.Adventure;
import com.monidoor.weder.adventure.GameScreen;

public class Samus extends Sprite {

    public World world;
    public Body b2Body;
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, SHOOTING };
    public State currentState;
    public State previousState;
    private TextureRegion samusStand;
    private Animation samusRun;

    private float stateTimer;
    private float floatableTimer = 0;
    private boolean runningRight;
    private boolean samusIsDead;
    public int health;
    private boolean isHit;

    private Array<Bullet> bullets;
    private GameScreen screen;

    public Samus(GameScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        health = 100;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("samus_run"), i * 28, 0, 28, 36));
        samusRun = new Animation(0.1f, frames);
        samusStand = new TextureRegion(screen.getAtlas().findRegion("samus_run"), 0, 0, 28, 36);
        defineSamus();
        setBounds(0, 0, 28 / Adventure.PPM, 36 / Adventure.PPM);

        bullets = new Array<Bullet>();
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        currentState = getState();

        setRegion(getFrame(dt));

        for(Bullet ball : bullets) {
            ball.update(dt);
            if(ball.isDestroyed())
                bullets.removeValue(ball, true);
        }

        if (isHit) {
            floatableTimer += Gdx.graphics.getDeltaTime();
            Array<Fixture> a = b2Body.getFixtureList();
            Filter f = new Filter();

            f.categoryBits = Adventure.SAMUS_BIT;
            f.maskBits = Adventure.GROUND_BIT | Adventure.Door_BIT | Adventure.OBJECT_BIT | Adventure.ITEM_BIT;
            a.get(0).setFilterData(f);
            if (floatableTimer >= 3) {
                f.categoryBits = Adventure.SAMUS_BIT;
                f.maskBits = Adventure.GROUND_BIT | Adventure.Door_BIT | Adventure.OBJECT_BIT | Adventure.ITEM_BIT | Adventure.ENEMY_BIT;
                a.get(0).setFilterData(f);
                isHit = false;
            }
        }
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void fire(){
        bullets.add(new Bullet(screen, b2Body.getPosition().x, b2Body.getPosition().y, runningRight ? true : false));
    }

    public State getState(){
        if(samusIsDead)
            return State.DEAD;
        else if((b2Body.getLinearVelocity().y > 0 && currentState == State.JUMPING)
                || (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region = null;

        switch(currentState){
            case DEAD:
                region = samusStand;
                break;
            case JUMPING:
                region = samusStand;
                break;
            case RUNNING:
                region = (TextureRegion)samusRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = samusStand;
                break;
            case STANDING:
                region = samusStand;
                break;
            default:
                region = samusStand;
                break;
        }

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public void defineSamus() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(100 / Adventure.PPM, 32 / Adventure.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = Adventure.SAMUS_BIT;
        fDef.filter.maskBits = Adventure.GROUND_BIT | Adventure.Door_BIT | Adventure.ENEMY_BIT| Adventure.OBJECT_BIT | Adventure.ITEM_BIT | Adventure.PORTAL_BIT;
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Adventure.PPM);
        fDef.shape = shape;

        b2Body.createFixture(fDef).setUserData(this);
    }

    public void jump(){
        if (currentState != State.JUMPING && currentState != State.FALLING) {
            b2Body.applyLinearImpulse(new Vector2(0, 4f), b2Body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void hit(Enemy enemy){
        if (health - enemy.ATTACK_POINTS <= 0) {
            die();
        } else {
            health -= enemy.ATTACK_POINTS;
            isHit = true;
            floatableTimer = 0;
            if (enemy.velocity.x < 0 && runningRight == true)
                b2Body.applyLinearImpulse(new Vector2(-2f, 2f), b2Body.getWorldCenter(), true);
            else if (enemy.velocity.x < 0 && runningRight) {
                b2Body.applyLinearImpulse(new Vector2(-2f, 2f), b2Body.getWorldCenter(), true);
            }
            else if (enemy.velocity.x > 0 && runningRight) {
                b2Body.applyLinearImpulse(new Vector2(2f, 2f), b2Body.getWorldCenter(), true);
            }
            else {
                b2Body.applyLinearImpulse(new Vector2(2f, 2f), b2Body.getWorldCenter(), true);
            }
        }
    }

    public void die() {

        if (!isDead()) {
            samusIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Adventure.NOTHING_BIT;

            for (Fixture fixture : b2Body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
        }
    }

    public boolean isDead(){
        return samusIsDead;
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for(Bullet ball : bullets)
            ball.draw(batch);
    }
}
