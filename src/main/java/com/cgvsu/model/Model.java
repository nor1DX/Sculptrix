package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3;
import com.cgvsu.math.Vector3f;

import java.util.ArrayList;

public class Model {

    public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();


    float[] scaleM = {1, 1, 1};
    private Vector3 scale = new Vector3(scaleM);

    float [] rotationM = {0,0,0};
    private Vector3 rotation = new Vector3(rotationM);

    float [] translationM = {0,0,0};
    private Vector3 translation = new Vector3(translationM);

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
        float[] scaleM = {1, 1, 1};
        setScale(new Vector3(scaleM));

        float [] rotationM = {0,0,0};
        setRotation(new Vector3(rotationM));
        float [] translationM = {0,0,0};
        setTranslation(new Vector3(translationM));
    }
}
