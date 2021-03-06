package com.tsukuba.project.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.tsukuba.project.components.ComponentList;
import com.tsukuba.project.components.DrawableComponent;
import com.tsukuba.project.components.TransformComponent;

import java.util.ArrayList;
import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem{

    static final float GAME_WIDTH = 32;
    static final float GAME_HEIGHT = 32;
    static final float PIXEL_PER_METER = 32.0f;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ArrayList<Entity> renderQueue;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(DrawableComponent.class,TransformComponent.class).get(), new ZComparator(),5);
        renderQueue = new ArrayList<Entity>();
        this.batch = batch;
        camera = new OrthographicCamera(GAME_WIDTH,GAME_HEIGHT);
        camera.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0);
    }

    private static class ZComparator implements Comparator<Entity> {
        private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(pm.get(e1).position.z - pm.get(e2).position.z);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity e : renderQueue) {
            DrawableComponent edc = ComponentList.DRAWABLE.get(e);

            if (edc.sprite == null)
                continue;

            TransformComponent etc = ComponentList.TRANSFORM.get(e);

            float w = etc.width * PIXEL_PER_METER;
            float h = etc.height * PIXEL_PER_METER;
            float x = w * 0.5f;
            float y = h * 0.5f;

            batch.draw(edc.sprite,
                    etc.position.x - x, etc.position.y - y,
                    x, y, w, h,
                    etc.scale.x * (1/PIXEL_PER_METER), etc.scale.y * (1/PIXEL_PER_METER),
                    MathUtils.radiansToDegrees * etc.rotation);
        }
        
        batch.end();
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
