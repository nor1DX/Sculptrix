package com.cgvsu.math;

public interface Vector {

    float getData(int index);

    Vector sum(Vector other);

    Vector subtract(Vector other);

    Vector multiplyOnScalar(final float scalar);

    Vector divisionOnScalar(final float scalar);

    float length();

    Vector normalize();

    // скалярное произведение
    float scalarProduct(Vector other);
}
