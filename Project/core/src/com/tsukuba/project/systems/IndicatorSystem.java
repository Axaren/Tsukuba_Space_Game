package com.tsukuba.project.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.Assets;
import com.tsukuba.project.components.*;

public class IndicatorSystem extends IteratingSystem {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite enemyIndicator;
	private Sprite bossIndicator;

    public IndicatorSystem(OrthographicCamera camera,SpriteBatch batch) {
        super(Family.all(EnemyComponent.class).get(),5);
        
        this.camera = camera;
        this.batch = batch;
        enemyIndicator = new Sprite(Assets.arrow);
        enemyIndicator.flip(true, false);
        bossIndicator = enemyIndicator;
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Entity playerEntity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
    	
        TransformComponent positionPlayer = ComponentList.TRANSFORM.get(playerEntity);
    	TransformComponent position = ComponentList.TRANSFORM.get(entity);
    	
   		if(!camera.frustum.pointInFrustum(position.position)) {
   			double theta = Math.atan2(position.position.y-positionPlayer.position.y,position.position.x-positionPlayer.position.x);
   			//theta = theta*180/Math.PI;
   					
   			batch.begin();
   			float spritePosX = (float) (positionPlayer.position.x +(4*Math.cos(theta)));
   			float spritePosY = (float) (positionPlayer.position.y +(4*Math.sin(theta)));
   			enemyIndicator.setPosition(spritePosX,spritePosY);
   			enemyIndicator.setRotation((float) (MathUtils.radiansToDegrees*theta));
   			enemyIndicator.setSize(1.5f, 1.5f);
   			enemyIndicator.setOriginCenter();

   			EnemyComponent enemyComponent = ComponentList.ENEMY.get(entity);
   			if (enemyComponent.type == EnemyComponent.EnemyType.BOSS) {
   				bossIndicator = enemyIndicator;
   				bossIndicator.setSize(2f,2f);
   				bossIndicator.draw(batch);
			}
			else
				enemyIndicator.draw(batch);
   			batch.end();
   		}    
    }
}
