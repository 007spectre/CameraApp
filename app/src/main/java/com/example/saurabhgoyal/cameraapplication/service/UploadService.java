package com.example.saurabhgoyal.cameraapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.saurabhgoyal.cameraapplication.Constant.Constants;
import com.example.saurabhgoyal.cameraapplication.realm.GalleryObject;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.MediaHttpUploader;
import com.kinvey.java.core.UploaderProgressListener;
import com.kinvey.java.model.FileMetaData;


import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UploadService extends Service {


    private static final String TAG = "UploadService";

    Realm realm;
    RealmConfiguration realmConfig;
    GalleryObject toUploadObject;
    Client mKinveyClient;

    int tobeUploaded;

    public UploadService()
    {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        tobeUploaded = realm.where(GalleryObject.class).equalTo("synced", false).equalTo("isimage",true).equalTo("local",true).findAll().size();
        Log.e(TAG, "" + tobeUploaded);
        if (tobeUploaded == 0)
            stopSelf();

        mKinveyClient = new Client.Builder(Constants.appId, Constants.appSecret, this.getApplicationContext()).build();

        if (mKinveyClient.user().isUserLoggedIn())
            upload();
        else {
            mKinveyClient.user().login(new KinveyUserCallback()
            {
                @Override
                public void onFailure(Throwable error) {
                    Log.e(TAG, "Login Failure", error);
                    stopSelf();
                }

                @Override
                public void onSuccess(User result) {
                    Log.i(TAG, "Logged in a new implicit user with id: " + result.getId());
                    upload();
                }
            });
        }

        return START_NOT_STICKY;
    }

    private void upload()
    {
        try
        {
            toUploadObject = realm.where(GalleryObject.class).equalTo("synced", false).equalTo("isimage",true).equalTo("local",true).findFirst();
            FileMetaData myFileMetaData = new FileMetaData(toUploadObject.getId());
            myFileMetaData.setPublic(true);
            myFileMetaData.setFileName(toUploadObject.getId());
            java.io.File file = new java.io.File(toUploadObject.getPath());
            if(!file.exists())
            {
                Log.e(TAG,toUploadObject.getId()+" deleted(Not Synced)");
                updateRealmAvoid();
            }
            mKinveyClient.file().upload(myFileMetaData, file, new UploaderProgressListener() {

                @Override
                public void onSuccess(FileMetaData fileMetaData) {
                    Log.e(TAG, "Uploaded:" + fileMetaData.getFileName());
                    updateRealmUpload();
                }

                @Override
                public void onFailure(Throwable error) {
                    Log.e(TAG, "failed to upload file.", error);
                    stopSelf();
                }

                @Override
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    Log.i(TAG, "upload progress: " + uploader.getUploadState());
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
            stopSelf();
        }
    }

    private void updateRealmUpload()
    {

        realm.beginTransaction();
        toUploadObject.setSynced(true);
        realm.commitTransaction();
        next();
    }

    private void updateRealmAvoid()
    {

        realm.beginTransaction();
        toUploadObject.setLocal(false);
        realm.commitTransaction();
        next();
    }

    private void next()
    {

        if (tobeUploaded == 1)
            stopSelf();
        else
        {
            stopSelf();
            startService(new Intent(getBaseContext(), UploadService.class));
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"Stopped");
        super.onDestroy();
    }
}
