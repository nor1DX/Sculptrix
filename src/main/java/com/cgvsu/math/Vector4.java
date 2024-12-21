package com.cgvsu.math;

import java.util.Arrays;

public class Vector4 implements Vector {

    private final float[] data;

    public Vector4(float[] data) {
        if (data.length != 4) {
            throw new IllegalArgumentException("Данный вектор не правильной размерности(ожидается размер 4)");
        }
        this.data = data;
    }

    @Override
    public String toString() {
        return "Vector4{" +
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
    public Vector4 sum(Vector other) {
        if (!(other instanceof Vector4)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 4)");
        }
        final float newX = getData(0) + other.getData(0);
        final float newY = getData(1) + other.getData(1);
        final float newZ = getData(2) + other.getData(2);
        final float newW = getData(3) + other.getData(3);

        final float[] newData = {newX, newY, newZ, newW};
        return new Vector4(newData);
    }


    @Override
    public Vector4 subtract(Vector other) {
        if (!(other instanceof Vector4)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 4)");
        }
        final float newX = getData(0) - other.getData(0);
        final float newY = getData(1) - other.getData(1);
        final float newZ = getData(2) - other.getData(2);
        final float newW = getData(3) - other.getData(3);

        final float[] newData = {newX, newY, newZ, newW};
        return new Vector4(newData);
    }

    @Override
    public Vector4 multiplyOnScalar(final float scalar) {
        final float newX = getData(0) * scalar;
        final float newY = getData(1) * scalar;
        final float newZ = getData(2) * scalar;
        final float newW = getData(3) * scalar;

        final float[] newData = {newX, newY, newZ, newW};
        return new Vector4(newData);

    }


    @Override
    public Vector4 divisionOnScalar(final float scalar) {
        final float newX = getData(0) / scalar;
        final float newY = getData(1) / scalar;
        final float newZ = getData(2) / scalar;
        final float newW = getData(3) / scalar;

        final float[] newData = {newX, newY, newZ, newW};
        return new Vector4(newData);
    }

    @Override
    public float length() {
        return (float) Math.sqrt(Math.pow(getData(0), 2) + Math.pow(getData(1), 2) + Math.pow(getData(2), 2) + Math.pow(getData(3), 2));
    }

    @Override
    public Vector4 normalize() {
        final float length = length();
        if (length == 0) {
            throw new IllegalArgumentException("Нельзя нормализовать вектор с нулевой длиной");
        }
        final float newX = getData(0) / length;
        final float newY = getData(1) / length;
        final float newZ = getData(2) / length;
        final float newW = getData(3) / length;

        final float[] newData = {newX, newY, newZ, newW};
        return new Vector4(newData);
    }

    public Vector3 normalizeTo3() {
        final float newX = getData(0) / getData(3);
        final float newY = getData(1) / getData(3);
        final float newZ = getData(2) / getData(3);

        final float[] newData = {newX, newY, newZ};
        return new Vector3(newData

        );
    }


    @Override
    public float scalarProduct(Vector other) {
        if (!(other instanceof Vector4)) {
            throw new IllegalArgumentException("Переданный вектор не подходит по размеру(ожидается размер 4)");
        }

        return getData(0) * other.getData(0) + getData(1) * other.getData(1) + getData(2) * other.getData(2) + getData(3) * other.getData(3);
    }


}
