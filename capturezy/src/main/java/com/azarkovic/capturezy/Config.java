package com.azarkovic.capturezy;

import java.io.Serializable;

/**
 * Created by azarkovic on 12.5.2016..
 */
public class Config implements Serializable
{
    private int width, height, tWidth, tHeight;
    private float scaleX, scaleY, tScaleX, tScaleY;
    enum Type
    {
        RESIZE,
        SCALE
    }
    Type type,typeT;

    private boolean generateThumbnail = false;

    public Config(int width, int height) {
        this.width = width;
        this.height = height;
        type = Type.RESIZE;
    }

    public Config(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        type = Type.SCALE;
    }

    public void generateThumbnail(int width, int height)
    {
        generateThumbnail = true;
        tWidth = width;
        tHeight = height;
        typeT = Type.RESIZE;
    }
    public void generateThumbnail(float scaleX, float scaleY)
    {
        generateThumbnail = true;
        tScaleX = scaleX;
        tScaleY = scaleY;
        typeT = Type.SCALE;
    }

    public int getWidth() {
        return width;
    }



    public int getHeight() {
        return height;
    }



    public float getScaleX() {
        return scaleX;
    }



    public float getScaleY() {
        return scaleY;
    }



    public Type getType() {
        return type;
    }



    public boolean shouldGenerateThumbnail() {
        return generateThumbnail;
    }

    public int getWidthT() {
        return tWidth;
    }

    public int getHeightT() {
        return tHeight;
    }

    public float getScaleXT() {
        return tScaleX;
    }

    public float getScaleYT() {
        return tScaleY;
    }

    public Type getTypeT() {
        return typeT;
    }


}
