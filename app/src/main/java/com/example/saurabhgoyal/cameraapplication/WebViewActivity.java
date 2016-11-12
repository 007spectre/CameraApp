package com.example.saurabhgoyal.cameraapplication;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by saurabh goyal on 5/29/2015.
 */
public class WebViewActivity extends Activity {
    public static String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewfragment);
        WebView browser=(WebView)findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setPluginState(WebSettings.PluginState.ON);
        browser.getSettings().setAllowFileAccess(true);
        browser.getSettings().setAllowContentAccess(true);



        if(url!=null && url!="")
            browser.loadUrl(url);
        browser.setWebViewClient(new MyAppWebviewclient());


    }


    @Override
    public void onBackPressed() {


        super.onBackPressed();


    }


}
