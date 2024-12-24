package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4X4;
import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import com.cgvsu.math.Vector4;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.render_engine.utils.Rasterizator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {

    public static void render(final GraphicsContext graphicsContext,
                              final Camera camera, final Model mesh,
                              final int width, final int height) {
        float[] ZBuffer = new float[width * height];
        for (int i = 0; i < ZBuffer.length; i++)
            ZBuffer[i] = Float.MAX_VALUE;
        
        Matrix4X4 modelMatrix = rotateScaleTranslate(
                mesh.getScale().getData(0), mesh.getScale().getData(1), mesh.getScale().getData(2),
                mesh.getRotation().getData(0), mesh.getRotation().getData(1), mesh.getRotation().getData(2),
                mesh.getTranslation().getData(0), mesh.getTranslation().getData(1), mesh.getTranslation().getData(2));

        Matrix4X4 viewMatrix = camera.getViewMatrix();
        Matrix4X4 projectionMatrix = camera.getProjectionMatrix();
        Matrix4X4 t1 = viewMatrix.multiplyOnMatrix(modelMatrix);
        Matrix4X4 modelViewProjectionMatrix = projectionMatrix.multiplyOnMatrix(t1);
        
        for (int i = 0; i < mesh.polygons.size(); ++i) {
            Polygon polygon = mesh.polygons.get(i);
            List<Integer> vertexIndices = polygon.getVertexIndices();
            
            Vector3 v1 = mesh.vertices.get(vertexIndices.get(0));
            Vector3 v2 = mesh.vertices.get(vertexIndices.get(1));
            Vector3 v3 = mesh.vertices.get(vertexIndices.get(2));
            
            Vector4 v1H = modelViewProjectionMatrix.multiplyOnVector(new Vector4(new float[]{v1.getData(0), v1.getData(1),
                                                                                             v1.getData(2), 1}));
            Vector4 v2H = modelViewProjectionMatrix.multiplyOnVector(new Vector4(new float[]{v2.getData(0), v2.getData(1),
                                                                                             v2.getData(2), 1}));
            Vector4 v3H = modelViewProjectionMatrix.multiplyOnVector(new Vector4(new float[]{v3.getData(0), v3.getData(1),
                                                                                             v3.getData(2), 1}));
            
            Vector3 v1N = v1H.normalizeTo3();
            Vector3 v2N = v2H.normalizeTo3();
            Vector3 v3N = v3H.normalizeTo3();
            
            Vector2 p1 = vertexToPoint(v1N, width, height);
            Vector2 p2 = vertexToPoint(v2N, width, height);
            Vector2 p3 = vertexToPoint(v3N, width, height);
            
            Rasterizator.rasterizeTriangle(graphicsContext, ZBuffer, v1, v2, v3, p1, p2, p3, Color.BLUE, width, height);
        }

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Vector2> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                Vector3 vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));


                float[] matrixVertex = {vertex.getData(0), vertex.getData(1), vertex.getData(2),1 };
                Vector4 vertexV = new Vector4(matrixVertex);


                Vector2 resultPoint = vertexToPoint(modelViewProjectionMatrix.multiplyOnVector(vertexV).normalizeTo3(), width, height);

                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(resultPoints.get(vertexInPolygonInd - 1).getData(0),
                                           resultPoints.get(vertexInPolygonInd - 1).getData(1),
                                           resultPoints.get(vertexInPolygonInd).getData(0),
                                           resultPoints.get(vertexInPolygonInd).getData(1));
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(resultPoints.get(nVerticesInPolygon - 1).getData(0),
                        resultPoints.get(nVerticesInPolygon - 1).getData(1),
                        resultPoints.get(0).getData(0),
                        resultPoints.get(0).getData(1));
        }
    }
}
