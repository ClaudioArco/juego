package com.juegogotas.screens;


import static com.juegogotas.extra.Utils.WORLD_HEIGTH;
import static com.juegogotas.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.juegogotas.MainGame;

public class GetReadyScreen extends BaseScreen {

    private Stage stage;
    private Image background;
    private Image getReady;


    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage = new Stage(fitViewport);
    }

    public void addBackground() {
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0, 0);
        this.background.setSize(WORLD_WIDTH, WORLD_HEIGTH);
        this.stage.addActor(this.background);
    }

    public void addGetReady() {
        this.getReady = new Image(mainGame.assetManager.getGetReady());
        this.getReady.setPosition(0.90f, 2f);
        this.getReady.setSize(6f, 2f);
        this.stage.addActor(this.getReady);
    }

    @Override
    public void show() {
        addBackground();
        addGetReady();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        boolean touch = Gdx.input.justTouched();

        if(touch){
            mainGame.setScreen(mainGame.gameScreen);//al tocar pantalla que vaya a gameScreen
        }
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}