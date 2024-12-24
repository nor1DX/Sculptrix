package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4X4;
import com.cgvsu.math.Vector3;



import javax.vecmath.Point2f;

public class GraphicConveyor {
    public static Matrix4X4 scaleMatrix4f(float scaleX, float scaleY, float scaleZ) {
        float[][] matrix = new float[][]
                {
                        {scaleX, 0, 0, 0},
                        {0, scaleY, 0, 0},
                        {0, 0, scaleZ, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 rotateX(float angleX) {
        float cos = (float) Math.cos(Math.toRadians(angleX));
        float sin = (float) Math.sin(Math.toRadians(angleX));
        float[][] matrix = new float[][]
                {
                        {1, 0, 0, 0},
                        {0, cos, sin, 0},
                        {0, -sin, cos, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 rotateY(float angleY) {
        float cos = (float) Math.cos(Math.toRadians(angleY));
        float sin = (float) Math.sin(Math.toRadians(angleY));
        float[][] matrix = new float[][]
                {
                        {cos, 0, sin, 0},
                        {0, 1, 0, 0},
                        {-sin, 0, cos, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 rotateZ(float angleZ) {
        float cos = (float) Math.cos(Math.toRadians(angleZ));
        float sin = (float) Math.sin(Math.toRadians(angleZ));
        float[][] matrix = new float[][]
                {
                        {cos, sin, 0, 0},
                        {-sin, cos, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 rotateMatrix4f(float angleX, float angleY, float angleZ) {
        Matrix4X4 spinX = rotateX(angleX);
        Matrix4X4 spinY = rotateY(angleY);
        Matrix4X4 spinZ = rotateZ(angleZ);




        //return Matrix4X4.multiplyOnMatrix(rotateZ(angleZ), Matrix4X4.multiplyOnMatrix(rotateY(angleY), (rotateX(angleX)));
        return (Matrix4X4) spinX.multiplyOnMatrix(spinY).multiplyOnMatrix(spinZ);
    }

    public static Matrix4X4 translationMatrix4X4(float translationX, float translationY, float translationZ) {
        float[][] matrix = new float[][]
                {
                        {1, 0, 0, translationX},
                        {0, 1, 0, translationY},
                        {0, 0, 1, translationZ},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 rotateScaleTranslate(
            float scaleX, float scaleY, float scaleZ,
            float angleX, float angleY, float angleZ,
            float translationX, float translationY, float translationZ
    ) {
        /*return Matrix4X4.multiply(

                translationMatrix4X4(translationX,translationY,translationZ),

                Matrix4X4.multiply(rotateMatrix4f(angleX,angleY,angleZ), scaleMatrix4f(scaleX,scaleY,scaleZ)));*/

        Matrix4X4 rotationMatrix = rotateMatrix4f(angleX, angleY, angleZ);
        Matrix4X4 scaleMatrix = scaleMatrix4f(scaleX, scaleY, scaleZ);
        Matrix4X4 translationMatrix = translationMatrix4X4(translationX, translationY, translationZ);

        return  rotationMatrix.multiplyOnMatrix(scaleMatrix).multiplyOnMatrix(translationMatrix);
    }

    public static Matrix4X4 rotateScaleTranslate() {
        return rotateScaleTranslate(1, 1, 1, 0, 0, 0, 0, 0, 0);
    }

    public static Matrix4X4 lookAt(Vector3 eye, Vector3 target) {
        float[] matUp = {0, 1, 0};
        return lookAt(eye, target, new Vector3(matUp));
    }

    public static Matrix4X4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        Vector3 resultZ = target.subtract(eye);
        Vector3 resultX = up.vectorProduct(resultZ);
        Vector3 resultY = resultZ.vectorProduct(resultX);

        Vector3 resultXN = resultX.normalize();
        Vector3 resultYN = resultY.normalize();
        Vector3 resultZN = resultZ.normalize();

        float[][] matrix = new float[][]
                {
                        {resultXN.getData(0), resultYN.getData(0), resultZN.getData(0), -resultXN.scalarProduct(eye)},
                        {resultXN.getData(1), resultYN.getData(1), resultZN.getData(1), -resultYN.scalarProduct(eye)},
                        {resultXN.getData(2), resultYN.getData(2), resultZN.getData(2), - resultZN.scalarProduct(eye)},
                        {0, 0, 0, 1}
                };
        return new Matrix4X4(matrix);
    }

    public static Matrix4X4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        float[][] matrixPerspective = {
                {tangentMinusOnDegree / aspectRatio, 0, 0, 0},
                {0, tangentMinusOnDegree, 0, 0},
                {0, 0, -(farPlane + nearPlane) / (farPlane - nearPlane), -2 * (nearPlane * farPlane) / (farPlane - nearPlane)},
                {0, 0, -1, 0}
        };


        return new Matrix4X4(matrixPerspective);
    }

    public static Point2f vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2f(vertex.getData(0) * width + width / 2.0F, vertex.getData(1) * height + height / 2.0F);
    }
}