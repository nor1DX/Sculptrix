package com.cgvsu.model.utils;

import com.cgvsu.math.Vector3;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;

public final class Triangulator {
    
    private static final float EPSILON = 1e-6f;

    public static List<Polygon> triangulate(Polygon polygon, List<Vector3> vertices) {
        List<Polygon> triangles = new ArrayList<>();
        List<Integer> vertexIndices = new ArrayList<>(polygon.getVertexIndices());
        
        while (vertexIndices.size() > 3) {
            for (int i = 0; i < vertexIndices.size(); i++) {
                int prev = (i == 0) ? vertexIndices.size() - 1 : i - 1;
                int next = (i + 1) % vertexIndices.size();
                
                if (isEar(vertexIndices, vertices, prev, i, next)) {
                    Polygon triangle = createTriangle(polygon, vertexIndices.get(prev),
                                                      vertexIndices.get(i), vertexIndices.get(next));
                    triangles.add(triangle);
                    
                    vertexIndices.remove(i);
                    
                    break;
                }
            }
        }
        
        Polygon triangle = createTriangle(polygon, vertexIndices.get(0),
                                          vertexIndices.get(1), vertexIndices.get(2));
        triangles.add(triangle);
        
        return triangles;
    }
    
    protected static boolean isEar(List<Integer> vertexIndices, List<Vector3> vertices, int prev, int current, int next) {
        Vector3 a = vertices.get(prev);
        Vector3 b = vertices.get(current);
        Vector3 c = vertices.get(next);
        
        Vector3 edge1 = b.subtract(a);
        Vector3 edge2 = c.subtract(b);
        float crossProduct = edge1.vectorProduct(edge2).length();
        
        if (crossProduct <= 0)
            return false;
        
        for (int i = 0; i < vertexIndices.size(); i++) {
            if (i == prev || i == current || i == next)
                continue;
            
            Vector3 p = vertices.get(vertexIndices.get(i));
            
            if (isPointInsideTriangle(a, b, c, p))
                return false;
        }
    
        return true;
    }
    
    
    protected static boolean isPointInsideTriangle(Vector3 a, Vector3 b, Vector3 c, Vector3 p) {
        float areaABC = calculateTriangleArea(a, b, c);
        
        float areaABP = calculateTriangleArea(a, b, p);
        float areaBCP = calculateTriangleArea(b, c, p);
        float areaCAP = calculateTriangleArea(c, a, p);
        
        return Math.abs(areaABC - (areaABP + areaBCP + areaCAP)) < EPSILON;
    }
    
    protected static float calculateTriangleArea(Vector3 a, Vector3 b, Vector3 c) {
        Vector3 edge1 = b.subtract(a);
        Vector3 edge2 = c.subtract(a);
        float crossProduct = edge1.vectorProduct(edge2).length();
        
        return 0.5f * crossProduct;
    }
    
    protected static Polygon createTriangle(Polygon polygon, int a, int b, int c) {
        Polygon triangle = new Polygon();
        
        triangle.getVertexIndices().add(a);
        triangle.getVertexIndices().add(b);
        triangle.getVertexIndices().add(c);
        
//        if (!polygon.getTextureVertexIndices().isEmpty()) {
//            triangle.getTextureVertexIndices().add(polygon.getTextureVertexIndices().get(a));
//            triangle.getTextureVertexIndices().add(polygon.getTextureVertexIndices().get(b));
//            triangle.getTextureVertexIndices().add(polygon.getTextureVertexIndices().get(c));
//        }
        
        return triangle;
    }

}
