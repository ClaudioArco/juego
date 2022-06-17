package com.juegogotas.actors;


import static com.juegogotas.extra.Utils.USER_GOTA_ACID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GoatAcid extends Actor {
    private static final float SPEED =-2f;//velocidad de caida de las Gotas
    private TextureRegion goatAcidTR;
    private Vector2 position;
    private World world;
    private float stateTime;
    private Body body;
    private Fixture fixture;

    public GoatAcid(World world, TextureRegion  animation, Vector2 position) {
        this.goatAcidTR = animation;
        this.position = position;
        this.world = world;
        stateTime = 0f;
        createBody();
        createFixture();
    }
    public void createBody() {
        //Creamos BodyDef
        BodyDef bodyDef = new BodyDef();
        //Position
        bodyDef.position.set(position);
        //tipo
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        //createBody de mundo
        this.body = this.world.createBody(bodyDef);

        this.body.setLinearVelocity(0,SPEED);
    }
    public void createFixture() {
        //Shape
        CircleShape circle = new CircleShape();
        //radio
        circle.setRadius(0.2f);
        //createFixture

        this.fixture = this.body.createFixture(circle, 8);
        this.fixture.setUserData(USER_GOTA_ACID);
        this.fixture.setSensor(true);
        //dispose
        circle.dispose();
    }
    public void stopAcid(){
        this.body.setLinearVelocity(0,0);
    }
    //nos dice si las gotas estan fuera de la pantalla
    public boolean isOutOfScreen(){
        return this.body.getPosition().y <= -1f;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - 0.25f, body.getPosition().y - 0.22f);
        batch.draw(this.goatAcidTR, getX(), getY(), 0.5f, 0.6f);
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void detach() {
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }
}
