package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector3Test {

    @Test
    void sum() {
        final float[] m1 = {1, 2, 4};
        final float[] m2 = {3, 4, 5};
        Vector v1 = new Vector3(m1);
        Vector v2 = new Vector3(m2);
        final float[] mRes = {4, 6, 9};
        Vector expectedResult = new Vector3(mRes);

        Vector result = v1.sum(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void subtract() {
        final float[] m1 = {1, 4, 6};
        final float[] m2 = {3, 2, 0};
        Vector v1 = new Vector3(m1);
        Vector v2 = new Vector3(m2);
        final float[] mRes = {-2, 2, 6};
        Vector expectedResult = new Vector3(mRes);

        Vector result = v1.subtract(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void multiplyOnScalar() {
        final float[] m1 = {1, 2, 8};
        Vector v1 = new Vector3(m1);
        final float scalar = 5;
        final float[] mRes = {5, 10, 40};
        Vector expectedResult = new Vector3(mRes);

        Vector result = v1.multiplyOnScalar(scalar);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }

    }

    @Test
    void divisionOnScalar() {
        final float[] m1 = {1, 5, 0};
        Vector v1 = new Vector3(m1);
        final float scalar = 5;
        final float[] mRes = {0.2F, 1, 0};
        Vector expectedResult = new Vector3(mRes);

        Vector result = v1.divisionOnScalar(scalar);

        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void length() {
        final float[] m1 = {2, 1, 2};
        Vector v1 = new Vector3(m1);
        final float expectedResult = 3;

        final float result = v1.length();

        assertEquals(expectedResult, result);
    }

    @Test
    void normalize() {
        final float[] m1 = {2, 1, 2};
        Vector v1 = new Vector3(m1);
        final float[] mRes = {0.666F, 0.333F, 0.666F};
        Vector expectedResult = new Vector3(mRes);

        Vector result = v1.normalize();

        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.getData(i), result.getData(i), 0.001);
        }
    }

    @Test
    void scalarProduct() {
        final float[] m1 = {1, 2, 7};
        final float[] m2 = {4, 5, 6};
        Vector v1 = new Vector3(m1);
        Vector v2 = new Vector3(m2);
        final float expectedResult = 56;

        final float result = v1.scalarProduct(v2);

        assertEquals(expectedResult, result);
    }

    @Test
    void vectorProduct() {
        final float[] m1 = {1, 2, 7};
        final float[] m2 = {4, 5, 6};
        Vector3 v1 = new Vector3(m1);
        Vector3 v2 = new Vector3(m2);
        final float[] mRes = {-23, 22, -3};
        Vector expectedResult = new Vector3(mRes);

        Vector3 result = v1.vectorProduct(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }
}
