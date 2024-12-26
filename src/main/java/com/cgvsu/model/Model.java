package com.cgvsu.model;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import java.util.ArrayList;

public class Model {
    //сделать приватными и добавит гетеры?
    public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
    public ArrayList<Vector2> textureVertices = new ArrayList<Vector2>();
    public ArrayList<Vector3> normals = new ArrayList<Vector3>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();


    private  Transform transform = new Transform();

    public Transform getTransform() {
        return transform;
    }

    public void prepareForRendering() {
        PreparationRender.prepareForRendering(this);
    }
}