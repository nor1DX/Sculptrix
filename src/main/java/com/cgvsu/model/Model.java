package com.cgvsu.model;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.model_utils.NormalCalculator;
import com.cgvsu.model.model_utils.Triangulator;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
    public ArrayList<Vector2> textureVertices = new ArrayList<Vector2>();
    public ArrayList<Vector3> normals = new ArrayList<Vector3>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    float[] scaleM = {1, 1, 1};
    private Vector3 scale = new Vector3(scaleM);

    float [] rotationM = {0,0,0};
    private Vector3 rotation = new Vector3(rotationM);

    float [] translationM = {0,0,0};
    private Vector3 translation = new Vector3(translationM);
    
    public void prepareForRendering() {
        triangulatePolygons();
        recalculateNormals();
    }
    
    private void triangulatePolygons() {
        ArrayList<Polygon> newPolygons = new ArrayList<>();
        
        for (Polygon polygon : polygons)
            newPolygons.addAll(Triangulator.triangulate(polygon, vertices));
        
        polygons = newPolygons;
    }
    
    private void recalculateNormals() {
        normals.clear();
        
        for (Polygon polygon : polygons)
            normals.add(NormalCalculator.calculateNormal(polygon, vertices));
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
        float[] scaleM = {1, 1, 1};
        setScale(new Vector3(scaleM));

        float [] rotationM = {0,0,0};
        setRotation(new Vector3(rotationM));
        float [] translationM = {0,0,0};
        setTranslation(new Vector3(translationM));
    }
}
