package com.azarkovic.capturezy;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by azarkovic on 12.5.2016..
 */
public class Result implements Serializable
{
    private String resultImagePath, resultThumbnailPath;

    public Result(){}

    public String getResultImagePath() {
        return resultImagePath;
    }

    public void setResultImagePath(String resultImagePath) {
        this.resultImagePath = resultImagePath;
    }

    public String getResultThumbnailPath() {
        return resultThumbnailPath;
    }

    public void setResultThumbnailPath(String resultThumbnailPath) {
        this.resultThumbnailPath = resultThumbnailPath;
    }
}
