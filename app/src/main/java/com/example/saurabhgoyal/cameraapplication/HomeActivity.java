package com.example.saurabhgoyal.cameraapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.saurabhgoyal.cameraapplication.Utilities.ConnectionDetector;
import com.example.saurabhgoyal.cameraapplication.Utilities.Utility;
import com.example.saurabhgoyal.cameraapplication.adapters.VivzAdapter2;
import com.example.saurabhgoyal.cameraapplication.fragment.Series;
import com.example.saurabhgoyal.cameraapplication.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh goyal on 10/21/2016.
 */
public class HomeActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private RecyclerView recyclerView;
    private VivzAdapter2 vivzAdapter;
    private RecyclerView recyclerView2;
    private VivzAdapter2 vivzAdapter2;
    private  boolean mUserLearnedDrawer;
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private  boolean mSavedInstanceState;
    SharedPreferences syncPreference;
    SharedPreferences.Editor editor;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        containerView=findViewById(R.id.drawer);
        recyclerView= (RecyclerView) findViewById(R.id.drawerlist);
        recyclerView2= (RecyclerView) findViewById(R.id.drawerlist2);





        vivzAdapter=new VivzAdapter2(getApplicationContext(),getData());
        vivzAdapter2=new VivzAdapter2(getApplicationContext(),getData2());

        recyclerView.setAdapter(vivzAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView2.setAdapter(vivzAdapter2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        vivzAdapter.setClickListener(new VivzAdapter2.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {

                if (position == 0) {
                    mDrawerLayout.closeDrawers();
                    Intent t = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(t);
                }
                if(position==1){
                    mDrawerLayout.closeDrawers();
                    Intent t = new Intent(getApplicationContext(), GalleryActivity.class);
                    t.putExtra("Camera",99);

                    startActivity(t);

                }





            }
        });
        vivzAdapter2.setClickListener(new VivzAdapter2.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {

                if (position == 1) {
                    mDrawerLayout.closeDrawers();
//                    Intent k=new Intent(getApplicationContext(),RetrieveContentsActivity.class);
//                    startActivity(k);
                    ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(connectionDetector.isConnectingToInternet()==true) {
                        navigateTp("https://www.linkedin.com/in/saurabh-goyal-13353a88?trk=nav_responsive_tab_profile_pic");

                    }
                    else {


                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();

                    }
                }
                if (position == 0) {

                Intent t = new Intent(getApplicationContext(), About.class);
                    startActivity(t);

                }
                if (position == 2) {
                    ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(connectionDetector.isConnectingToInternet()==true) {
                        navigateTp("https://github.com/007spectre");

                    }
                    else {


                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();

                    }

                }



            }
        });



        Utility utility=new Utility();
        mUserLearnedDrawer= Boolean.valueOf(utility.readFromSharedPref(this,KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mSavedInstanceState=true;
        }

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){

                    mUserLearnedDrawer=true;
                    Utility utility=new Utility();
                    utility.writeToSharedPref(getApplicationContext(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };

        if(!mUserLearnedDrawer && !mSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();

            }
        });
        navigateHome();



    }
















    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //    Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        }
        return false;
    }
    void navigateTp(String url) {
        WebViewActivity webViewFragment=new WebViewActivity();
        webViewFragment.url=url;
        Intent k=new Intent(this, WebViewActivity.class);
        startActivity(k);


    }
    void navigate(String url) {
        WebViewActivity webViewFragment=new WebViewActivity();
        webViewFragment.url=url;
        Intent k=new Intent(this, WebViewActivity.class);
        startActivity(k);


    }
    public static List<Information> getData(){
        List<Information> data=new ArrayList<>();
        int icon[]={R.drawable.homeicon,R.drawable.galleryicon};
        String []titles={"Home","Gallery"};
        for(int i=0;i<titles.length && i<icon.length;i++){

            Information current =new Information();
            current.itemId=icon[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;

    }
    public static List<Information> getData2(){
        List<Information> data=new ArrayList<>();
        int icon[]={R.drawable.aboutus,R.drawable.linkdin,R.drawable.github};
        String []titles={"About Me","Linkedin","GitHub"};
        for(int i=0;i<titles.length && i<icon.length;i++){

            Information current =new Information();
            current.itemId=icon[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;

    }


    private void navigateHome() {


        FragmentManager fragmentManager = getSupportFragmentManager();
        Series rFragment = new Series();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();

    }

//    @Override
//    public void onBackPressed() {
//
//
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//
//        builder.setTitle("Confirm");
//        builder.setMessage("Are you sure You want to exit from the BuyHatke App?");
//
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//
//                // Do nothing but close the dialog
//
//
//                finish();
//                System.exit(0);
//            }
//
//        });
//
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                // Do nothing
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog alert = builder.create();
//        alert.show();
//
//
//    }



}

