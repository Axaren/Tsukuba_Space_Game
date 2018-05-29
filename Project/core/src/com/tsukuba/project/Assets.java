package com.tsukuba.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static Texture spaceship;
    public static Texture arrow;
    public static Texture enemy;
    public static Texture bullets;

    public static TextureRegion playerBullet;

    public static Texture planet;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        spaceship = loadTexture("spaceship.png");
        enemy = loadTexture("enemy.png");
        arrow = loadTexture("arrow.png");
        bullets = loadTexture("bullets.png");

        playerBullet = new TextureRegion(bullets,21,14,10,6);


        planet = loadTexture("planet1.png");
    }
}
