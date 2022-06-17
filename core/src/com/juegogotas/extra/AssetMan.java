package com.juegogotas.extra;

import static com.juegogotas.extra.Utils.ATLAS_MAP;
import static com.juegogotas.extra.Utils.BACKGROUND_IMAGE;
import static com.juegogotas.extra.Utils.BACKGROUND_OVER;
import static com.juegogotas.extra.Utils.CUBO;
import static com.juegogotas.extra.Utils.FONT_FNT;
import static com.juegogotas.extra.Utils.FONT_PNG;
import static com.juegogotas.extra.Utils.GAMEOVER;
import static com.juegogotas.extra.Utils.GOTA_ACID;
import static com.juegogotas.extra.Utils.GOTA_BLUE;
import static com.juegogotas.extra.Utils.MUSIC;
import static com.juegogotas.extra.Utils.MUSICWIN;
import static com.juegogotas.extra.Utils.REFRESH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//Esta clase sirve para centralizar todas los recursos gráficos como texturas, regiones, o animaciones.

public class AssetMan {

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;
    TextureRegion backgroundTexture;



    public AssetMan() {
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.load(MUSICWIN, Sound.class);//cargamos el sonido
        assetManager.load(GAMEOVER, Sound.class);//cargamos el sonido de muerte
        assetManager.load(MUSIC, Music.class);//cargamos la musica
        assetManager.finishLoading();
        textureAtlas = assetManager.get(ATLAS_MAP);
        assetManager.finishLoading();
        textureAtlas = assetManager.get(ATLAS_MAP);
    }
    public TextureRegion getGoatAcid() {
        return  this.textureAtlas.findRegion(GOTA_ACID);
    }
    public TextureRegion getGoatBlue() {
        return  this.textureAtlas.findRegion(GOTA_BLUE);
    }
    public TextureRegion getGameOver() { return this.textureAtlas.findRegion(BACKGROUND_OVER);}

    public TextureRegion getGetReady() {
        backgroundTexture = new TextureRegion(new Texture("taptoplay.png"), 55, 0, 800, 480);
        return backgroundTexture;
    }
    //Creamos un metodo que devuelva la parte de la imagen que corresponde al fondo.
    public TextureAtlas.AtlasRegion getBackground() {
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }
    public TextureRegion getTouch() {
        return this.textureAtlas.findRegion(REFRESH);
    }

   public TextureRegion getCubo() {
       return this.textureAtlas.findRegion(CUBO);
   }
    public Sound getGAMEOVERSound(){
        return this.assetManager.get(GAMEOVER);
    }

    public Sound getMUSICWINSound(){
        return this.assetManager.get(MUSICWIN);
    }

    public Music getMusic(){
        return this.assetManager.get(MUSIC);
    }

    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT),Gdx.files.internal(FONT_PNG), false);
    }

}
