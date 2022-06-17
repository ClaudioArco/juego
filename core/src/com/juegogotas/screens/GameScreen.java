package com.juegogotas.screens;


import static com.juegogotas.extra.Utils.SCREEN_HEIGHT;
import static com.juegogotas.extra.Utils.SCREEN_WIDTH;
import static com.juegogotas.extra.Utils.USER_CUBO;
import static com.juegogotas.extra.Utils.USER_FLOOR;
import static com.juegogotas.extra.Utils.USER_GOTA_ACID;
import static com.juegogotas.extra.Utils.USER_GOTA_BLUE;
import static com.juegogotas.extra.Utils.USER_LIMITLEFT;
import static com.juegogotas.extra.Utils.USER_LIMITRIGHT;
import static com.juegogotas.extra.Utils.WORLD_HEIGTH;
import static com.juegogotas.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.juegogotas.MainGame;
import com.juegogotas.actors.Cubo;
import com.juegogotas.actors.GoatAcid;
import com.juegogotas.actors.GoatBlue;

//interfaz ContacListener que se implementa para las colisiones
public class GameScreen extends BaseScreen implements ContactListener {

    private Array<GoatAcid> arrayGoatAcid; //Array de las gotas Acidad
    private float TIME_GOATAcid; //
    private float timeToCreateGoatAcid;

    private Array<GoatBlue> arrayGoatBlue; //Array de las gota Azules
    private float TIME_GOATBlue=2f; //tiempo en el qeu se crean la Gotas
    private float timeToCreateGoatBlue;

    private OrthographicCamera fontCamera;
    private BitmapFont score;


    private int scoreNumber;

    private Music music;//musica de fondo

    private Stage stage;

    private Image background;

    private Cubo cubo;


    private World world;// gestionar la parte de la fisica

    //DebugRenderer nos servirá para ver en la pantalla una representación gráfica del mundo físico.
    //private Box2DDebugRenderer debugRenderer;
    public static OrthographicCamera ortCamera;

    public GameScreen(MainGame mainGame){
        super(mainGame);
        //se crea el mundo con dos parametros  x e y (y==gravedad)
        this.world = new World(new Vector2(0,-6),true);//el -10 es l grtavedad
        //Inicializamos el stage le pasamos el tamaño de nuestro mundo
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);
        this.world.setContactListener(this);

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        //this.debugRenderer = new Box2DDebugRenderer();
        //inicializamos la música de fondo
        this.music = this.mainGame.assetManager.getMusic();
        //inicializamos los arrays
        this.arrayGoatAcid= new Array();
        this.TIME_GOATAcid=0.4f;
        this.arrayGoatBlue= new Array();
        //texto de la puntuacion
        prepareScore();

    }
    //creamos un metedo que configure el texto del contador
    private void prepareScore(){
        this.scoreNumber = 0;
        this.score = this.mainGame.assetManager.getFont();
        this.score.getData().scale(2f);
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false, SCREEN_WIDTH,SCREEN_HEIGHT);
        this.fontCamera.update();

    }
    //metodo del fondo
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGTH);
        this.stage.addActor(this.background);
    }

    @Override
    public void show() {
        addBackground();
        addFloor();
        addlimit_right();
        addlimit_left();
        Sound soundWIN = this.mainGame.assetManager.getMUSICWINSound();
        Sound soundOVER = this.mainGame.assetManager.getGAMEOVERSound();
        TextureRegion cuboSprite = mainGame.assetManager.getCubo();
        this.cubo = new Cubo(this.world,cuboSprite,soundWIN,soundOVER, new Vector2(1.35f ,0.2f ));
        this.stage.addActor(this.cubo);
        this.music.setLooping(true);
        //play
        this.music.play();


    }
    public void addGoatAcid (float delta) {

        TextureRegion GoatAcidTexture = mainGame.assetManager.getGoatAcid();
        if (cubo.state == Cubo.STATE_NORMAL) {//si esta vivo
            //Se acumula delta hasta eltiempo que hemos puesto de creacion de la gota
            this.timeToCreateGoatAcid += delta;
            if (this.timeToCreateGoatAcid >= TIME_GOATAcid) {

                this.timeToCreateGoatAcid -= TIME_GOATAcid;
                //ponemos la forma aleatoria de la posicion tanto x como y
                float posRandomY = MathUtils.random(4.5f, 5f);
                float posRandomX = MathUtils.random(0.8f, 7.2f);
                GoatAcid goatAcid = new GoatAcid(this.world, GoatAcidTexture, new Vector2(posRandomX, posRandomY)); //Posición de la tubería inferior
                arrayGoatAcid.add(goatAcid);
                this.stage.addActor(goatAcid);
            }
        }
    }
    //metodo igual que el anterior solo que para el otro tipo de gotas
    public void addGoatBlue (float delta){
        TextureRegion GoatBlueTexture = mainGame.assetManager.getGoatBlue();
        if(cubo.state == Cubo.STATE_NORMAL) {

            this.timeToCreateGoatBlue+=delta;

            if(this.timeToCreateGoatBlue >= TIME_GOATBlue) {
                this.timeToCreateGoatBlue-=TIME_GOATBlue;
                float posRandomY = MathUtils.random(4.5f, 5f);
                float posRandomX = MathUtils.random(0.8f, 7.2f);
                GoatBlue goatBlue= new GoatBlue(this.world, GoatBlueTexture, new Vector2(posRandomX, posRandomY));
                arrayGoatBlue.add(goatBlue);
                this.stage.addActor(goatBlue);
            }
        }
    }
    //metodo del suelo
    private void addFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        EdgeShape edge = new EdgeShape();
        edge.set(0,0,WORLD_WIDTH,0);
        Fixture fixtureFloor=body.createFixture(edge, 1);
        fixtureFloor.setUserData(USER_FLOOR);//al cuerpo lo asocio con un identificador
        edge.dispose();
    }
    //metodo configura pared derecha
   public void addlimit_right(){
       BodyDef bodyDef = new BodyDef();
       bodyDef.position.set(8f, 2.4f);//posicion x la mitad del ancho del mundo e y 0.6
       bodyDef.type = BodyDef.BodyType.StaticBody;
       Body body = world.createBody(bodyDef);

       PolygonShape edge = new PolygonShape();
       edge.setAsBox(0.1f, 2.4f);//mitad ancho y altura//tamaño
       Fixture fixtureRight=body.createFixture(edge, 3);
       fixtureRight.setUserData(USER_LIMITRIGHT);
       edge.dispose();
    }
    //metodo configura pared izq
    public void addlimit_left(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0f, 2.4f);//posicion x la mitad del ancho del mundo e y 0.6
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        PolygonShape edge = new PolygonShape();
        edge.setAsBox(0.1f, 2.4f);//mitad ancho y altura//tamaño
        Fixture fixtureLeft=body.createFixture(edge, 3);
        fixtureLeft.setUserData(USER_LIMITLEFT);
        edge.dispose();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.getBatch().setProjectionMatrix(ortCamera.combined);

        addGoatBlue(delta);
        addGoatAcid(delta);
        this.stage.getBatch().setProjectionMatrix(ortCamera.combined);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();//Ordenamos al stage que dibuje.


        //Actualizamos la cámara para que aplique cualquier cambio en las matrices internas.
        this.ortCamera.update();
        //this.debugRenderer.render(this.world, this.ortCamera.combined);

        removeGoatAcid();
        removeGoatBlue();
        // Se le pasa el mundo físico y las matrices de la camara (combined)

        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.score.draw(this.stage.getBatch(), ""+this.scoreNumber,SCREEN_WIDTH/2, SCREEN_HEIGHT);
        this.stage.getBatch().end();




    }
    //Creamos un método para eliminar las gotas
    public void removeGoatAcid(){
        for (GoatAcid goatAcid : this.arrayGoatAcid) {
            if(!world.isLocked()) {
                if(goatAcid.isOutOfScreen()) {
                    goatAcid.detach();
                    goatAcid.remove();
                    arrayGoatAcid.removeValue(goatAcid,false);
                }
            }
        }
    }
    //Creamos un método para eliminar pipes
    public void removeGoatBlue(){
        for (GoatBlue goatBlue : this.arrayGoatBlue) {
            if(!world.isLocked()) {
                if(goatBlue.isOutOfScreen()) {
                    goatBlue.detach();
                    goatBlue.remove();
                    arrayGoatBlue.removeValue(goatBlue,false);
                }
            }
        }
    }

    @Override
    public void hide() {
        this.cubo.detach();
        this.cubo.remove();
        //se para la música cuando se oculte la pantalla
        this.music.stop();
    }

    @Override
    public void dispose() {
        //Nos acordamos de eliminar los recursos del stage
        this.stage.dispose();
        // eliminamos los recursos  que retiene world
        this.world.dispose();
    }
/// ********************************************* ///
    /// *************** COLISIONES ****************** ///
    /// ********************************************* ///
    //cone este metodo detectamos que objeto choca con cual tanto A como B//como B con A


    public boolean areCollide(Contact contact, Object objectA, Object objectB) {
        return (contact.getFixtureA().getUserData().equals(objectA) && contact.getFixtureB().getUserData().equals(objectB)
                || contact.getFixtureA().getUserData().equals(objectB) && contact.getFixtureB().getUserData().equals(objectA));
    }

    //Método que se llamará cada vez que se produzca cualquier contacto
    @Override
    public void beginContact(Contact contact) {
        if(areCollide(contact,USER_CUBO,USER_GOTA_ACID)){

            this.world = new World(new Vector2(0,0),true);
            this.cubo.hurt();//el cubo muere
            for(GoatAcid goatAcid:this.arrayGoatAcid){
                goatAcid.stopAcid();
                goatAcid.remove();
            }
            for(GoatBlue goatBlue:this.arrayGoatBlue){
                goatBlue.stopBlue();
                goatBlue.remove();
            }
            this.stage.addAction(Actions.sequence(
                    Actions.delay(0.5f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {

                            mainGame.setScreen(mainGame.gameOverScreen);
                        }
                    })
            ));

        }
        else {
            if(areCollide(contact,USER_CUBO,USER_GOTA_BLUE)){
                this.scoreNumber++;
                Sound soundWIN = this.mainGame.assetManager.getMUSICWINSound();

                soundWIN.play();

                }
            }
        }




    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }



}
