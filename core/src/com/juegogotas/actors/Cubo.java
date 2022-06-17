package com.juegogotas.actors;

import static com.juegogotas.extra.Utils.USER_CUBO;
import static com.juegogotas.extra.Utils.WORLD_WIDTH;
import static com.juegogotas.screens.GameScreen.ortCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cubo extends Actor {

    private TextureRegion imagencubo;
    private Vector2 position;
    //se declaran los diferentes estados del juego
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DEAD = 1;
    public int state;
    //Creamos el atributo para el cuerpo fisico del pájaro.
    private Body body;
    //Creamos el atributo para la forma fisica del pájaro.
    private Fixture fixture;
    float stateTime;
    private World world;
    private Sound gameover;//se crea sonido cuando muere
    private Sound musicwin;//se crea sonido cuando gana

    //le pasamamos alconstructor la instancia del mundo físico.
    public Cubo(World world, TextureRegion animation, Sound GAMEOVERMUSIC,Sound MUSICWINMUSIC,Vector2 position) {
        this.imagencubo = animation;
        this.position      = position;
        this.world         = world;
        this.gameover = GAMEOVERMUSIC;
        this.musicwin = MUSICWINMUSIC;
        stateTime = 0f;
        state = STATE_NORMAL;
        createBody();
        createFixture();
    }
    public void createBody(){
        //Creamos BodyDef
        BodyDef bodyDef = new BodyDef();
        //Position
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //createBody de mundo
        this.body = this.world.createBody(bodyDef);

    }
    //Creamos un método para crear la forma
    public void createFixture(){
        //Shape
        PolygonShape edge = new PolygonShape();
        //cuadrado
        edge.setAsBox(0.2f, 0.2f);;
        //createFixture
        this.fixture = this.body.createFixture(edge,3);
        //setUserData  --> Utils -> identificadores de cuerpos
        this.fixture.setUserData(USER_CUBO);
        //dispose
        edge.dispose();
    }

    @Override
    public void act(float delta) {

     Vector3 vector=ortCamera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
     boolean toque = Gdx.input.justTouched();
     if(toque &&vector.x>WORLD_WIDTH/2  &&  this.state == STATE_NORMAL){
         this.body.setLinearVelocity(2f, 0);

     }
     if(toque  && vector.x<WORLD_WIDTH/2&& this.state == STATE_NORMAL){
         this.body.setLinearVelocity(-2f, 0);

     }

    }
    public void hurt(){
        this.state = STATE_DEAD;
        this.stateTime = 0;
        this.musicwin.play();//sonido al morir el heroe
    }

    //Sobrecargamos draw
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x-0.3f, body.getPosition().y-0.25f);//posicion imagen
        batch.draw(this.imagencubo,getX(),getY(), 0.6f,0.5f);//tamaño imagen

        stateTime += Gdx.graphics.getDeltaTime();


    }

    //Nos creamos un metodo detach (ayudaa a liberar los recursos de body y fixture)
    public void detach(){

        //(body) destroyFixture
        this.body.destroyFixture(this.fixture);
        //(world) destroyBody
        this.world.destroyBody(this.body);

    }
}
