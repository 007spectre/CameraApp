package com.example.saurabhgoyal.cameraapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;


/**
 * Created by saurabh goyal on 2/7/2016.
 */
public class About extends ActionBarActivity {

    Toolbar toolbar;
    private ImageView imageViewRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        imageViewRound=(ImageView)findViewById(R.id.aboutUsImage);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.saurabh);
        imageViewRound.setImageBitmap(icon);



    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();


//

    }
}
