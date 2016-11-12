package com.example.saurabhgoyal.cameraapplication.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.saurabhgoyal.cameraapplication.R;
import com.example.saurabhgoyal.cameraapplication.adapters.GalleryAdapter;
import com.example.saurabhgoyal.cameraapplication.realm.GalleryObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by saurabh goyal on 11/12/2016.
 */
public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryActivity";

    Toolbar mToolbar;
    Switch toolbarSwitch;

    Realm realm;
    RealmConfiguration realmConfig;
    RealmResults<GalleryObject> galleryList;

    RecyclerView galleryRecyclerView;
    private GridLayoutManager gridLayoutManager;
    GalleryAdapter galleryAdapter;

    SharedPreferences syncPreference;
    SharedPreferences.Editor editor;

    int camId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gallery, container, false);
        realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        realm = Realm.getInstance(realmConfig);

//        camId = getIntent().getIntExtra("Camera",99);

        syncPreference = getActivity().getSharedPreferences("EventData", 0); //This SharedPreference stores the user's option whether to AutoSync

//        initToolbar();
       // setSyncSwitch();

        galleryList = realm.where(GalleryObject.class).findAll();   //Get all Images and Videos

        //Log.e(TAG,""+realm.where(GalleryObject.class).equalTo("isimage",false).findAll().size());

        galleryRecyclerView = (RecyclerView)v.findViewById(R.id.gallery_recyclerview);
        galleryRecyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        galleryRecyclerView.setLayoutManager(gridLayoutManager);

        galleryAdapter = new GalleryAdapter(getActivity(),galleryList);
        galleryRecyclerView.setAdapter(galleryAdapter);


        return v;
    }
}
