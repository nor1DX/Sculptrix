package com.cgvsu.render_engine.utils;

import com.cgvsu.math.Vector3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;

public final class Rasterizator {
    
    public static void rasterizeTriangle(final GraphicsContext graphicsContext, final float[] ZBuffer,
                                         final Vector3 v1, final Vector3 v2, final Vector3 v3,
                                         final Point2f p1, final Point2f p2, final Point2f p3,
                                         final Color color, final int width, final int height) {
        final float minX = Math.min(Math.min(p1.x, p2.x), p3.x);
        final float maxX = Math.max(Math.max(p1.x, p2.x), p3.x);
        final float minY = Math.min(Math.min(p1.y, p2.y), p3.y);
        final float maxY = Math.max(Math.max(p1.y, p2.y), p3.y);
        
        for (float y = minY; y <= maxY; y++) {
            for (float x = minX; x <= maxX; x++) {
                float[] barycentric = calculateBarycentricCoordinate(x + 0.5f, y + 0.5f, p1, p2, p3);
                
                if (barycentric != null) {
                    float z = barycentric[0] * v1.getData(2) +
                              barycentric[1] * v2.getData(2) +
                              barycentric[2] * v3.getData(2);
                    
                    int index = (int) y * width + (int) x;
                    if (index >= 0 && index < ZBuffer.length && z < ZBuffer[index]) {
                        ZBuffer[index] = z;
                        graphicsContext.setFill(color);
                        graphicsContext.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }
    
    private static float[] calculateBarycentricCoordinate(final float x, final float y,
                                                            final Point2f p1, final Point2f p2, final Point2f p3) {
        float denominator = (p2.y - p3.y) * (p1.x - p3.x) +
                            (p3.x - p2.x * (p1.y - p3.y));
        
        if (denominator == 0)
            return null;
        
        float alpha = ((p2.y - p3.y) * (x - p3.x) +
                       (p3.x - p2.x) * (y - p3.y))
                      / denominator;
        float beta = ((p3.y - p1.y) * (x - p3.x) +
                      (p1.x - p3.x) * (y - p3.y))
                     / denominator;
        float gamma = 1 - alpha - beta;
        
        if (alpha < 0 || beta < 0 || gamma < 0)
            return null;
        
        return new float[]{ alpha, beta, gamma };
    }

}
