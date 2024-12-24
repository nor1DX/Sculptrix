package com.cgvsu.render_engine.utils;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;

public final class Rasterizator {
    
    public static void rasterizeTriangle(final GraphicsContext graphicsContext, final float[] ZBuffer,
                                         final Vector3 v1, final Vector3 v2, final Vector3 v3,
                                         final Vector2 p1, final Vector2 p2, final Vector2 p3,
                                         final Color color, final int width, final int height) {
        final int minX = Math.max(0, (int) Math.min(Math.min(p1.getData(0), p2.getData(0)), p3.getData(0)));
        final int maxX = Math.min(width - 1, (int) Math.max(Math.max(p1.getData(0), p2.getData(0)), p3.getData(0)));
        final int minY = Math.max(0, (int) Math.min(Math.min(p1.getData(1), p2.getData(1)), p3.getData(1)));
        final int maxY = Math.min(height - 1, (int) Math.max(Math.max(p1.getData(1), p2.getData(1)), p3.getData(1)));
        
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
                                                            final Vector2 p1, final Vector2 p2, final Vector2 p3) {
        float denominator = (p2.getData(1) - p3.getData(1)) * (p1.getData(0) - p3.getData(0)) +
                            (p3.getData(0) - p2.getData(0)) * (p1.getData(1) - p3.getData(1));
        
        if (denominator == 0)
            return null;
        
        float alpha = ((p2.getData(1) - p3.getData(1)) * (x - p3.getData(0)) +
                       (p3.getData(0) - p2.getData(0)) * (y - p3.getData(1)))
                      / denominator;
        float beta = ((p3.getData(1) - p1.getData(1)) * (x - p3.getData(0)) +
                      (p1.getData(0) - p3.getData(0)) * (y - p3.getData(1)))
                     / denominator;
        float gamma = 1 - alpha - beta;
        
        if (alpha < 0 || beta < 0 || gamma < 0)
            return null;
        
        return new float[]{ alpha, beta, gamma };
    }

}
