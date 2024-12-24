package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4X4;
import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphicConveyorTest {

    @Test
    public void testScaleMatrix4f() {
        Matrix4X4 result = GraphicConveyor.scaleMatrix4f(2.0f, 3.0f, 4.0f);

        float[][] expectedMatrix = {
                {2.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 3.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 4.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }
    @Test
    public void testRotateX() {
        Matrix4X4 result = GraphicConveyor.rotateX(90.0f);

        float cos90 = 0.0f;
        float sin90 = 1.0f;

        float[][] expectedMatrix = {
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, cos90, sin90, 0.0f},
                {0.0f, -sin90, cos90, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

         for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }

    @Test
    public void testRotateY() {
        Matrix4X4 result = GraphicConveyor.rotateY(90.0f);

        float cos90 = 0.0f;
        float sin90 = 1.0f;

        float[][] expectedMatrix = {
                {cos90, 0.0f, sin90, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {-sin90, 0.0f, cos90, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }

    @Test
    public void testRotateZ() {
        Matrix4X4 result = GraphicConveyor.rotateZ(90.0f);

        float cos90 = 0.0f;
        float sin90 = 1.0f;

        float[][] expectedMatrix = {
                {cos90, sin90, 0.0f, 0.0f},
                {-sin90, cos90, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }

    @Test
    public void testRotateMatrix4f() {
        Matrix4X4 result = GraphicConveyor.rotateMatrix4f(90.0f, 90.0f, 90.0f);

        float[][] expectedMatrix = {
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, -1.0f, 0.0f, 0.0f},
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix[i].length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f,
                        "Ошибка на позиции [" + i + "][" + j + "]");
            }
        }
    }
    @Test
    public void testTranslationMatrix4X4() {
        Matrix4X4 result = GraphicConveyor.translationMatrix4X4(1.0f, 2.0f, 3.0f);

        float[][] expectedMatrix = {
                {1.0f, 0.0f, 0.0f, 1.0f},
                {0.0f, 1.0f, 0.0f, 2.0f},
                {0.0f, 0.0f, 1.0f, 3.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }
    @Test
    public void testRotateScaleTranslate() {
        Matrix4X4 result = GraphicConveyor.rotateScaleTranslate(
                2.0f, 2.0f, 2.0f, // scale
                90.0f, 90.0f, 90.0f, // rotate
                1.0f, 2.0f, 3.0f // translate
        );

        // Ожидаемая матрица после комбинированного преобразования
        float[][] expectedMatrix = {
                {0.0f, 0.0f, 2.0f, 1.0f},
                {0.0f, -2.0f, 0.0f, 2.0f},
                {2.0f, 0.0f, 0.0f, 3.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix[i].length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f,
                        "Ошибка на позиции [" + i + "][" + j + "]");
            }
        }
    }



    @Test
    public void testLookAt() {
        Vector3 eye = new Vector3(0.0f, 0.0f, 0.0f);
        Vector3 target = new Vector3(0.0f, 0.0f, 1.0f);
        Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);

        Matrix4X4 result = GraphicConveyor.lookAt(eye, target, up);

        float[][] expectedMatrix = {
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }

    }
    @Test
    public void testPerspective() {
        Matrix4X4 result = GraphicConveyor.perspective(
                90.0f, // fov
                1.0f, // aspectRatio
                0.1f, // nearPlane
                100.0f // farPlane
        );


        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(90.f * 0.5F)));

        float[][] expectedMatrix = {
                {tangentMinusOnDegree/1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, tangentMinusOnDegree, 0.0f, 0.0f},
                {0.0f, 0.0f, -1.002f, -0.2002f},
                {0.0f, 0.0f, -1.0f, 0.0f}
        };

        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix.length; j++) {
                assertEquals(expectedMatrix[i][j], result.getMatrix()[i][j], 0.0001f);
            }
        }
    }

    @Test
    public void testVertexToPoint() {
        Vector3 vertex = new Vector3(0.5f, 0.5f, 0.0f);
        Vector2 result = GraphicConveyor.vertexToPoint(vertex, 800, 600);

        assertEquals(800.0f, result.getData(0), 0.0001f);
        assertEquals(600.0f, result.getData(1), 0.0001f);
    }
}