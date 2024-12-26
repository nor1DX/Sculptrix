package com.cgvsu.model;

import com.cgvsu.math.Vector3;

public class Transform {
    private Vector3 scale;
    private Vector3 rotation;
    private Vector3 translation;

    public Transform() {
        this.scale = new Vector3(1, 1, 1);
        this.rotation = new Vector3(0, 0, 0);
        this.translation = new Vector3(0, 0, 0);
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }

    public void resetTransformations() {
        this.scale = new Vector3(1, 1, 1);
        this.rotation = new Vector3(0, 0, 0);
        this.translation = new Vector3(0, 0, 0);
    }
}