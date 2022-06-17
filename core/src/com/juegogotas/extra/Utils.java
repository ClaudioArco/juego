package com.juegogotas.extra;


//Suele ser necesario tener una clase que se encarge de centralizar todos los datos comunes a los
// que se va a acceder durante  la aplicación y que se centralice en un sitio
public class Utils {

    //Debemos crear variables que definan los tamaños de pantalla
    public static final int SCREEN_HEIGHT = 480;
    public static final int SCREEN_WIDTH = 800;

    //Debemos crear variables que definan los tamaños de nuestro mundo
    public static final float WORLD_HEIGTH = 4.8f ;
    public static final float WORLD_WIDTH = 8f;

    //Identificadores de assets
    public static final String ATLAS_MAP = "imagenes.txt";
    public static final String BACKGROUND_IMAGE = "Background";
    public static final String BACKGROUND_OVER = "GameOverBackground";
    public static final String GOTA_BLUE = "droplet";
    public static final String GOTA_ACID = "AcidDrop";
    public static final String CUBO = "bucket";
    public static final String REFRESH = "Refresh";
    //identificador para los sonidos
    public static final String GAMEOVER = "GameOver.mp3";
    public static final String MUSICWIN = "drop.wav";
    public static final String MUSIC = "rain.mp3";

    //resto de identificadores
    public static final String USER_FLOOR = "floor";
    public static final String USER_LIMITRIGHT = "Limitright";
    public static final String USER_LIMITLEFT = "Limitleft";
    public static final String USER_CUBO = "cubo";
    public static final String USER_GOTA_ACID = "gotaacid";
    public static final String USER_GOTA_BLUE = "gotablue";
    //fuente que usamos en el contador
    public static final String FONT_FNT     = "ClaudioA.fnt";
    public static final String FONT_PNG     = "ClaudioA.png";

}
