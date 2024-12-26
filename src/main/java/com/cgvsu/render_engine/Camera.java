package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4X4;
import com.cgvsu.math.Vector3;


public class Camera {

    private Vector3 position;
    private Vector3 target;
    private final float fov;
    private float aspectRatio;
    private final float nearPlane;
    private final float farPlane;

    public Camera(final Vector3 position, final Vector3 target,
                  final float fov, final float aspectRatio,
                  final float nearPlane, final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3 position) {
        this.position = position;
    }

    public void setTarget(final Vector3 target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getTarget() {
        return target;
    }

    public void movePosition(final Vector3 translation) {
        this.position = this.position.sum(translation);
    }


    Matrix4X4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4X4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public Vector3 getDirection() {
        return target.subtract(position).normalize();
    }

    public void rotateAroundTarget(float yaw, float pitch, float roll) {
        //  Y (yaw) X (pitch)  Z (roll)
        pitch = Math.max(-90, Math.min(90, pitch));

        Vector3 direction = target.subtract(position);
        float yawRad = (float) Math.toRadians(yaw);
        float cosYaw = (float) Math.cos(yawRad);
        float sinYaw = (float) Math.sin(yawRad);
        float newX = direction.getData(0) * cosYaw - direction.getData(2) * sinYaw;
        float newZ = direction.getData(0) * sinYaw + direction.getData(2) * cosYaw;

        direction.setData(0, newX);
        direction.setData(2, newZ);

        float pitchRad = (float) Math.toRadians(pitch);
        float cosPitch = (float) Math.cos(pitchRad);
        float sinPitch = (float) Math.sin(pitchRad);
        float newY = direction.getData(1) * cosPitch - direction.getData(2) * sinPitch;
        float newZ2 = direction.getData(1) * sinPitch + direction.getData(2) * cosPitch;
        direction.setData(1, newY);
        direction.setData(2, newZ2);


        float rollRad = (float) Math.toRadians(roll);
        float cosRoll = (float) Math.cos(rollRad);
        float sinRoll = (float) Math.sin(rollRad);
        float newX2 = direction.getData(0) * cosRoll - direction.getData(1) * sinRoll;
        float newY2 = direction.getData(0) * sinRoll + direction.getData(1) * cosRoll;
        direction.setData(0, newX2);
        direction.setData(1, newY2);

        position = target.subtract(direction);
    }
}