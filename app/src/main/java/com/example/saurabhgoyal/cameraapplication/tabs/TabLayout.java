package com.example.saurabhgoyal.cameraapplication.tabs;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saurabhgoyal.cameraapplication.R;


/**
 * Created by saurabh goyal on 10/1/2015.
 */
public class TabLayout extends Fragment implements ActionBar.TabListener {
    private Toolbar toolbar;

    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabPagerAdapter tabPagerAdapter;

    private String[] tabs = { "Camera" ,"Gallery"};

    int []icons={R.drawable.icon
            , R.drawable.icon};
   static SlidingTabLayout mtabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Retrieving the currently selected item number

        // Creating view correspoding to the fragment
        View v = inflater.inflate(R.layout.slidetab, container, false);

        viewPager = (ViewPager)v. findViewById(R.id.pager);
        mtabs = (SlidingTabLayout)v.findViewById(R.id.tab);

        tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(),tabs,icons,getActivity());
        viewPager.setAdapter(tabPagerAdapter);

mtabs.setDistributeEvenly(true);

        mtabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mtabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));

 //       mtabs.setCustomTabView(R.layout.customtabview, R.id.tabtext);
//        mtabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.accentColor);
//            }
//        });

        mtabs.setViewPager(viewPager);

//        actionBar = getActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        for (String tab_name : tabs) {
////            actionBar.addTab(actionBar.newTab().setText(tab_name)
////                    .setTabListener(this));
//            actionBar.addTab(actionBar.newTab().setText(tab_name)
//                    .setTabListener(this));
//        }
//
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            /**
//             * on swipe select the respective tab
//             * */
//            @Override
//            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) { }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) { }
//        });




        return  v;

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) { }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.colorAccent);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.primaryText);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }
}
