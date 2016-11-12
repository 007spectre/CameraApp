package com.example.saurabhgoyal.cameraapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.saurabhgoyal.cameraapplication.PhotoActivity;
import com.example.saurabhgoyal.cameraapplication.R;

/**
 * Created by ccs7pc on 3/1/2016.
 */
public class Series extends Fragment {
    TextView t1;
    String email;
    Button b1;
    Button b2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.series, container, false);
        b1=(Button)v. findViewById(R.id.game);
        b2=(Button)v. findViewById(R.id.test);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),PhotoActivity.class);
                startActivity(i);
            }
        });



        return v;
    }

}
