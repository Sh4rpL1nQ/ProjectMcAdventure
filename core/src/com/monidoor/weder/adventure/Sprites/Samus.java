package com.monidoor.weder.adventure.Sprites;

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
    private TextureRegion samusJump;
    private TextureRegion samusDead;

    private float stateTimer;
    private float prevStateTimer;
    private boolean runningRight;
    private boolean samusIsDead;

    private Array<Bullet> bullets;
    private GameScreen screen;

    public Samus(GameScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        defineSamus();
        TextureRegion txt = new TextureRegion(new Texture("sample_player.png"), 16, 16);
        setBounds(0,0,16 / Adventure.PPM,16 / Adventure.PPM);
        setRegion(txt);

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
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:

                break;
            case JUMPING:

                break;
            case RUNNING:

                break;
            case FALLING:
                break;
            case STANDING:
            default:
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2Body.getLinearVelocity().x < 0 || !runningRight)){
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2Body.getLinearVelocity().x > 0 || runningRight)){
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return new TextureRegion(new Texture("sample_player.png"), 16, 16);

    }

    private void defineSamus() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(100 / Adventure.PPM, 32 / Adventure.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = Adventure.SAMUS_BIT;
        fDef.filter.maskBits = Adventure.GROUND_BIT | Adventure.Door_BIT | Adventure.ENEMY_BIT| Adventure.OBJECT_BIT;
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
        die();
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
