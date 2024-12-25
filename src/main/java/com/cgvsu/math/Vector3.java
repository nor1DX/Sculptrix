package com.cgvsu.math;

import java.util.Arrays;

public class Vector3 implements Vector {

    private final float[] data;

    public Vector3(final float[] data) {
        if (data.length != 3) {
            throw new IllegalArgumentException("Данный вектор не правильной размерности(ожидается размер 3)");
        }
        this.data = data;
    }
    public Vector3(final float x, final float y, final float z) {
        this.data = new float[] {x, y, z};
    }



    @Override
    public String toString() {
        return "Vector3{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public float getData(int index) {
        return data[index];
    }

    @Override
    public void setData(int index, float value) {

        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы диапазона [0, " + (data.length - 1) + "]");
        }


        // Устанавливаем новое значение
        data[index] = value;

    }

    @Override
    public Vector3 sum(Vector other) {
        if (!(other instanceof Vector3)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 3)");
        }
        final float newX = getData(0) + other.getData(0);
        final float newY = getData(1) + other.getData(1);
        final float newZ = getData(2) + other.getData(2);

        final float[] newData = {newX, newY, newZ};
        return new Vector3(newData);
    }


    @Override
    public Vector3 subtract(Vector other) {
        if (!(other instanceof Vector3)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 3)");
        }
        final float newX = getData(0) - other.getData(0);
        final float newY = getData(1) - other.getData(1);
        final float newZ = getData(2) - other.getData(2);

        final float[] newData = {newX, newY, newZ};

        return new Vector3(newData);
    }

    @Override
    public Vector3 multiplyOnScalar(final float scalar) {
        final float newX = getData(0) * scalar;
        final float newY = getData(1) * scalar;
        final float newZ = getData(2) * scalar;

        final float[] newData = {newX, newY, newZ};
        return new Vector3(newData);
    }

    @Override
    public Vector3 divisionOnScalar(final float scalar) {
        final float newX = getData(0) / scalar;
        final float newY = getData(1) / scalar;
        final float newZ = getData(2) / scalar;

        final float[] newData = {newX, newY, newZ};
        return new Vector3(newData);
    }

    @Override
    public float length() {
        return (float) Math.sqrt(Math.pow(getData(0), 2) + Math.pow(getData(1), 2) + Math.pow(getData(2), 2));
    }

    @Override
    public Vector3 normalize() {
        float length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Нельзя нормализовать вектор с нулевой длиной");
        }
        final float newX = getData(0) / length;
        final float newY = getData(1) / length;
        final float newZ = getData(2) / length;

        final float[] newData = {newX, newY, newZ};
        return new Vector3(newData);
    }

    @Override
    public float scalarProduct(Vector other) {
        if (!(other instanceof Vector3)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 3)");
        }
        return getData(0) * other.getData(0) + getData(1) * other.getData(1) + getData(2) * other.getData(2);
    }


    //векторное произведение
    public Vector3 vectorProduct(Vector other) {
        if (!(other instanceof Vector3)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 3)");
        }
        final float newX = getData(1) * other.getData(2) - other.getData(1) * getData(2);
        final float newY = getData(0) * other.getData(2) - other.getData(0) * getData(2);
        final float newZ = getData(0) * other.getData(1) - other.getData(0) * getData(1);

        final float[] newData = {newX, -newY, newZ};
        return new Vector3(newData);
    }
    public boolean equals(Vector3 other){
        final float eps = 1e-7f;
        return Math.abs(getData(0) - other.getData(0)) < eps &&
                Math.abs(getData(1) - other.getData(1)) < eps &&
                Math.abs(getData(2) - other.getData(2)) < eps;
    }
}

