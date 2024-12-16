package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4X4;
import com.cgvsu.math.Vector3;
import com.cgvsu.math.Vector4;
import com.cgvsu.model.Model;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.Point2f;
import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {

    public static void render(final GraphicsContext graphicsContext,
                              final Camera camera, final Model mesh,
                              final int width, final int height) {
        Matrix4X4 modelMatrix = rotateScaleTranslate(
                mesh.getScale().getData(0), mesh.getScale().getData(1), mesh.getScale().getData(2),
                mesh.getRotation().getData(0), mesh.getRotation().getData(1), mesh.getRotation().getData(2),
                mesh.getTranslation().getData(0), mesh.getTranslation().getData(1), mesh.getTranslation().getData(2));


        Matrix4X4 viewMatrix = camera.getViewMatrix();

        Matrix4X4 projectionMatrix = camera.getProjectionMatrix();


        Matrix4X4 t1 = viewMatrix.multiplyOnMatrix(modelMatrix);

        Matrix4X4 modelViewProjectionMatrix = projectionMatrix.multiplyOnMatrix(t1);


        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {

                Vector3 vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));


                float[] matrixVertex = {vertex.getData(0), vertex.getData(1), vertex.getData(2),1 };
                Vector4 vertexV = new Vector4(matrixVertex);


                Point2f resultPoint = vertexToPoint(modelViewProjectionMatrix.multiplyOnVector(vertexV).normalizeTo3(), width, height);

                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(resultPoints.get(vertexInPolygonInd - 1).x,
                                           resultPoints.get(vertexInPolygonInd - 1).y,
                                           resultPoints.get(vertexInPolygonInd).x,
                                           resultPoints.get(vertexInPolygonInd).y);
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x, resultPoints.get(0).y);
        }
    }
}
