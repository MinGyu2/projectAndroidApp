package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Storesignin extends AppCompatActivity {
    private WebView mWebs;
    private WebSettings mWebset;
    String addrs= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesignin);


        mWebs =(WebView)findViewById(R.id.webs);
        mWebs.setWebViewClient(new WebViewClient());
        mWebset = mWebs.getSettings();
        mWebset.setJavaScriptEnabled(true);
        mWebs.addJavascriptInterface(new WebAppInterface(this),"Android");

        mWebs.loadUrl("http://13.125.255.233:8887/clients/addStore.html");

        mWebs.reload();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebs.canGoBack()){
            mWebs.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void exitss(){
            finish();
        }
        @JavascriptInterface
        public void goMapss(){
            Intent i = new Intent(mContext,MapsView.class);
            startActivityForResult(i,2);
        }
        @JavascriptInterface
        public String getAddr(){
            return addrs;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            addrs = data.getStringExtra("addrs");
        }
    }
}
