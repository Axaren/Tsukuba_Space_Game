package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public Vector2 velocity;
    public Vector2 acceleration;

    public VelocityComponent(Vector2 velocity, Vector2 acceleration) {
        this.velocity = new Vector2(velocity);
        this.acceleration = new Vector2(acceleration);
    }

    public VelocityComponent() {
        velocity = new Vector2(0.0f,0.0f);
        acceleration = new Vector2(0.0f,0.0f);
    }
}
