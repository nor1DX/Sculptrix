package com.cgvsu.model.utils;

import com.cgvsu.math.Vector3;
import com.cgvsu.model.Polygon;

import java.util.List;

public final class NormalCalculator {

    public static Vector3 calculateNormal(final Polygon polygon, final List<Vector3> vertices) {
        Vector3 v1 = vertices.get(polygon.getVertexIndices().get(0));
        Vector3 v2 = vertices.get(polygon.getVertexIndices().get(1));
        Vector3 v3 = vertices.get(polygon.getVertexIndices().get(2));
        
        Vector3 edge1 = v2.subtract(v1);
        Vector3 edge2 = v3.subtract(v1);
        
        return edge1.vectorProduct(edge2).normalize();
    }

}
