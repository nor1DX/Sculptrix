package com.cgvsu.math;

import java.util.Arrays;

public class Vector2 implements Vector {

    private final float[] data;

    public Vector2(final float[] data) {
        if (data.length != 2) {
            throw new IllegalArgumentException("Данный вектор не правильной размерности(ожидается размер 2)");
        }
        this.data = data;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "data=" + Arrays.toString(data) +
                '}';
    }


    @Override
    public float getData(int i) {
        return data[i];
    }


    @Override
    public Vector2 sum(Vector other) {
        if (!(other instanceof Vector2)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 2)");
        }
        final float newX = getData(0) + other.getData(0);
        final float newY = getData(1) + other.getData(1);

        final float[] newData = {newX, newY};
        return new Vector2(newData);
    }


    @Override
    public Vector2 subtract(Vector other) {
        if (!(other instanceof Vector2)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 2)");
        }
        final float newX = getData(0) - other.getData(0);
        final float newY = getData(1) - other.getData(1);

        final float[] newData = {newX, newY};
        return new Vector2(newData);
    }

    @Override
    public Vector2 multiplyOnScalar(final float scalar) {
        final float newX = getData(0) * scalar;
        final float newY = getData(1) * scalar;

        final float[] newData = {newX, newY};
        return new Vector2(newData);
    }

    @Override
    public Vector2 divisionOnScalar(final float scalar) {
        final float newX = getData(0) / scalar;
        final float newY = getData(1) / scalar;

        final float[] newData = {newX, newY};
        return new Vector2(newData);
    }


    @Override
    public float length() {
        return (float)  Math.sqrt(Math.pow(getData(0), 2) + Math.pow(getData(1), 2));
    }

    @Override
    public Vector2 normalize() {
        float length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Нельзя нормализовать вектор с нулевой длиной");
        }
        final float newX = getData(0) / length;
        final float newY = getData(1) / length;

        final float[] newData = {newX, newY};
        return new Vector2(newData);
    }

    @Override
    public float scalarProduct(Vector other) {
        if (!(other instanceof Vector2)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 2)");
        }
        return getData(0) * other.getData(0) + getData(1) * other.getData(1);
    }
}
