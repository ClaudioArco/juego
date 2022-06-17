package com.juegogotas;

import com.badlogic.gdx.Game;
import com.juegogotas.extra.AssetMan;
import com.juegogotas.screens.GameOverScreen;
import com.juegogotas.screens.GameScreen;
import com.juegogotas.screens.GetReadyScreen;


public class MainGame extends Game {

	//Instancia de la pantalla durante el juego
	public GameScreen gameScreen;

	//Crear instancia de la pantalla de GetReady
	public GetReadyScreen getReadyScreen;

	//Crear instancia de la pantalla de GameOver
	public GameOverScreen gameOverScreen;

	public AssetMan assetManager;

	@Override
	public void create() {
		this.assetManager = new AssetMan();

		this.gameScreen = new GameScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		this.getReadyScreen = new GetReadyScreen(this);

		//Scene2d nos ayuda a manejar las diferentes instancias de las diferentes pantallas que
		//compondrá nuestro juego.
		setScreen(this.getReadyScreen);


	}


}
