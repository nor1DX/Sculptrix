package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2Test {

    @Test
    void sum() {
        final float[] m1 = {1, 2};
        final float[] m2 = {3, 4};

        Vector v1 = new Vector2(m1);
        Vector v2 = new Vector2(m2);

        final float[] mRes = {4, 6};
        Vector expectedResult = new Vector2(mRes);

        Vector result = v1.sum(v2);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }


    @Test
    void subtract() {
        final float[] m1 = {1, 4};
        final float[] m2 = {3, 2};

        Vector v1 = new Vector2(m1);
        Vector v2 = new Vector2(m2);

        final float[] mRes = {-2, 2};
        Vector expectedResult = new Vector2(mRes);

        Vector result = v1.subtract(v2);
        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void multiplyOnScalar() {
        final float[] m1 = {1, 2};
        Vector v1 = new Vector2(m1);
        final float scalar = 5;
        final float[] mRes = {5, 10};
        Vector expectedResult = new Vector2(mRes);

        Vector result = v1.multiplyOnScalar(scalar);

        for (int i = 0; i < mRes.length; i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void divisionOnScalar() {
        final float[] m1 = {1, 5};
        Vector v1 = new Vector2(m1);
        final float scalar = 5;
        final float[] mRes = {0.2F, 1};
        Vector expectedResult = new Vector2(mRes);

        Vector result = v1.divisionOnScalar(scalar);

        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void length() {
        final float[] m1 = {3, 4};
        Vector v1 = new Vector2(m1);
        final float expectedResult = 5;

        final float result = v1.length();

        assertEquals(expectedResult, result);

    }

    @Test
    void normalize() {
        final float[] m1 = {3, 4};
        Vector v1 = new Vector2(m1);
        final float[] mRes = {0.6F, 0.8F};
        Vector expectedResult = new Vector2(mRes);

        Vector result = v1.normalize();


        for (int i = 0; i < expectedResult.length(); i++) {
            assertEquals(expectedResult.getData(i), result.getData(i));
        }
    }

    @Test
    void scalarProduct() {
        final float[] m1 = {1, 2};
        final float[] m2 = {4, 5};
        Vector v1 = new Vector2(m1);
        Vector v2 = new Vector2(m2);
        final float expectedResult = 14;

        final float result = v1.scalarProduct(v2);

        assertEquals(expectedResult, result);
    }
}
