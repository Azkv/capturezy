package com.azarkovic.capturezy;

import java.io.Serializable;

/**
 * Created by azarkovic on 12.5.2016..
 */
public class Config implements Serializable
{
    private int width, height, tWidth, tHeight,x,y;
    private float scaleX, scaleY, tScaleX, tScaleY;
    enum Type
    {
        RESIZE,
        SCALE,
        EMPTY,
        CROP
    }
    Type type,typeT;

    private boolean generateThumbnail = false;

    /**
     * Returns a new bitmap resized exactly to width and height provided
     * @param width
     * @param height
     */
    public Config(int width, int height) {
        this.width = width;
        this.height = height;
        type = Type.RESIZE;
    }

    /**
     * Returns a new bitmap scaled by scaleX and scaleY factors
     * @param scaleX
     * @param scaleY
     */
    public Config(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        type = Type.SCALE;
    }

    /**
     * Returns a cropped bitmap
     * @param x Can't exceed bitmap width
     * @param y Can't exceed bitmap height
     * @param w
     * @param h
     */
    public Config(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.type = Type.CROP;
    }

    /**
     * Empty constructor used mainly if you just want to generate a thumbnail
     * Returns the same bitmap that was passed in by param
     */
    public Config()
    {
        //empty constructor
    }

    /**
     * Generates a @width x @height thumbnail
     * @param width
     * @param height
     */
    public void generateThumbnail(int width, int height)
    {
        generateThumbnail = true;
        tWidth = width;
        tHeight = height;
        typeT = Type.RESIZE;
    }

    /**
     * Generates a scaled thumbnail based on scaleX and scaleY factors
     * @param scaleX
     * @param scaleY
     */
    public void generateThumbnail(float scaleX, float scaleY)
    {
        generateThumbnail = true;
        tScaleX = scaleX;
        tScaleY = scaleY;
        typeT = Type.SCALE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
