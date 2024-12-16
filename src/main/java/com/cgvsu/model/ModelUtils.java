package com.cgvsu.model;

import java.util.ArrayList;

public class ModelUtils {

    public static ArrayList<Polygon> triangulatePolygon(Polygon polygon) {
        ArrayList<Polygon> triangles = new ArrayList<>();
        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        
        if (vertexIndices.size() == 3) {
            triangles.add(polygon);
            return triangles;
        }
        
        
    }

}
