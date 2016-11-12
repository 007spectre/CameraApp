package com.example.saurabhgoyal.cameraapplication.customview;

import android.content.Context;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


import com.example.saurabhgoyal.cameraapplication.PhotoActivity;

import java.io.IOException;
import java.util.List;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private static final String TAG = "Camera Preview";
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    Context context;
    int width,height;
    List<Camera.Size> sizeList;
    
    public CameraPreview(Context context, Camera camera) 
    {
        super(context);
        this.context = context;
        this.camera = camera;
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
    }

    public void switchCamera(Camera camera)
    {

        this.camera = camera;
        try {
        camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
        orientationChange(width,height);
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) 
    {
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) 
    {
        camera.stopPreview();
        camera.release();
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) 
    {
        camera.stopPreview();
        this.width = width;
        this.height = height;
        orientationChange(width,height);
    }

    private void orientationChange(int width,int height)
    {

        Camera.Parameters parameters = camera.getParameters();

        WindowManager wm = (WindowManager)(context).getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Camera.Size imageSize = getImageSize(width, height, parameters);


        if(display.getRotation() == Surface.ROTATION_0)
            camera.setDisplayOrientation(90);

        if(display.getRotation() == Surface.ROTATION_270)
            camera.setDisplayOrientation(0);
        parameters.setPictureSize(imageSize.width, imageSize.height);
        parameters.setPreviewSize(imageSize.width, imageSize.height);

        camera.setParameters(parameters);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setFrameLayout(imageSize.width,imageSize.height);
    }

    private Camera.Size getImageSize(int width, int height, Camera.Parameters parameters)
    {

        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        bestSize = sizeList.get(0);

        for(int i = 1; i < sizeList.size(); i++){
            if((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)){
                bestSize = sizeList.get(i);
            }
        }

        return bestSize;
    }


    private void setFrameLayout(int imageWidth,int imageHeight)
    {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int layoutheight = (metrics.widthPixels*imageWidth)/imageHeight;
        ((PhotoActivity)context).setFrameLayout(metrics.widthPixels,layoutheight);
    }
}
