package com.azarkovic.capturezy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by azarkovic on 11.5.2016..
 * param: Intent.getStringExtra("path") - path to the file without extension
 */
public class CameraPhotoCapture extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private static final int REQUEST_WRITE_PERMISSION = 3;
    private String folderPath,filename,extension;
    Uri finalPath;
    Config config;
    Result result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras().containsKey("path") == false)
        {
            try {
                throw new Exception("No path given to the intent!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
        folderPath = getIntent().getStringExtra("path");
        filename = getIntent().getStringExtra("filename");
        extension = getIntent().getStringExtra("extension");
        config = (Config)getIntent().getSerializableExtra("config");
        result = new Result();
        requestForCameraPermission();
    }



    private void requestForCameraPermission()
    {
        checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION, new Runnable() {
            @Override
            public void run() {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_WRITE_PERMISSION, new Runnable() {
                    @Override
                    public void run() {
                        startCameraActivity();
                    }
                });
            }
        });

    }
    private void checkPermission(String permission, int resultCallbackCode, Runnable runnable)
    {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{permission}, resultCallbackCode);
        } else
        {
            runnable.run();
        }
    }
    private void startCameraActivity()
    {
        try
        {
            File f = new File(folderPath);
            f.mkdirs();
            File fReal = File.createTempFile(filename,"."+extension, f);
            f.setWritable(true,false);
            Intent takePictureIntent = new Intent();
            takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            finalPath = Uri.fromFile(fReal);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, finalPath);
            takePictureIntent.putExtra("return-data", false);

            this.startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getName(),e.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION || requestCode == REQUEST_WRITE_PERMISSION)
        {
            requestForCameraPermission();
        }
    }
    private boolean writeToFile(Bitmap bmp, String path) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            bmp.compress(Bitmap.CompressFormat.JPEG, 95, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized Bitmap handleConfig(final Config config, final Bitmap bmp)
    {
        if(config == null)return bmp;
        Bitmap rezBmp = null;
        switch(config.getType())
        {
            case RESIZE:
                rezBmp =  BitmapUtils.resize(bmp,config.getWidth(),config.getHeight());
                break;
            case SCALE:
                rezBmp =  BitmapUtils.scale(bmp,config.getScaleX(),config.getScaleY());
                break;
        }

        if(config.shouldGenerateThumbnail())
        {
            File f = new File(folderPath+"/thumbnail");
            f.mkdirs();
            File fRealT = new File(Uri.fromFile(f).getPath() + "/" + filename+"."+extension);
            Bitmap bmpT = null;
            if(config.getTypeT() == Config.Type.RESIZE)
            {
                bmpT = BitmapUtils.resize(bmp,config.getWidthT(), config.getHeightT());
            }
            else if(config.getTypeT() == Config.Type.SCALE)
            {
                bmpT = BitmapUtils.scale(bmp,config.getScaleXT(),config.getScaleYT());
            }
            if(bmpT != null)
            {
                boolean success = writeToFile(bmpT, Uri.fromFile(fRealT).getPath());
                if(success) result.setResultThumbnailPath(Uri.fromFile(fRealT).getPath());
            }
        }
        return rezBmp;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CAMERA) {
            try
            {
                String fullPath = finalPath.getPath();
                File f = new File(fullPath);
                if(!f.exists())return;
                Bitmap bmp = BitmapUtils.shrink(f,this);
                bmp = BitmapUtils.rotate(bmp, f.getAbsolutePath());

                bmp = handleConfig(config,bmp);

                boolean success = writeToFile(bmp, fullPath);
                if(success) result.setResultImagePath(Uri.fromFile(f).getPath());

                Intent intent = getIntent();
                if(bmp != null && result.getResultImagePath() != null)
                {
                    intent.putExtra("result",result);
                    if (getParent() == null) {
                        setResult(Activity.RESULT_OK,intent);
                    } else {
                        getParent().setResult(Activity.RESULT_OK,intent);
                    }
                }
                else
                {

                    if (getParent() == null) {
                        setResult(Activity.RESULT_CANCELED,intent);
                    } else {
                        getParent().setResult(Activity.RESULT_CANCELED,intent);
                    }
                }

                CameraPhotoCapture.this.finish();

            }
            catch(Exception e)
            {
                Log.e(this.getClass().getName(),e.getMessage());
            }

        }

    }


}
