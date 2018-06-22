package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MapsView extends AppCompatActivity{
    private WebView web;
    private WebSettings webss;

    String addrs= "" ;
    Intent i;


    // GPSTracker class
    GPSTracker gps = null;

    public Handler mHandler;

    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;

    String lati = "";
    String longit = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsview);

        //new
        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }
        //new
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==RENEW_GPS){
                    makeNewGpsService();
                }
                if(msg.what==SEND_PRINT){
                    logPrint((String)msg.obj);
                }
            }
        };
        //new

        web = (WebView)findViewById(R.id.mapview);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new MapsView.WebAppInterface(this),"Android");

        web.loadUrl("http://13.125.255.233:8887/clients/map2.html");
        web.reload();

        i = getIntent();
        //storename = i.getStringExtra("storename");
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
        public void setAddr(String addr){
            addrs = addr;
            i.putExtra("addrs", addr);
            setResult(RESULT_OK, i);
            finish();
        }
        @JavascriptInterface
        public void getGPSss(){
            String sss;
            if(gps == null) {
                gps = new GPSTracker(mContext,mHandler);
            }else{
                gps.Update();
            }
            // check if GPS enabled
            if(gps.canGetLocation()){
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                lati =""+latitude;
                longit = ""+longitude;
                // \n is for new line
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }
        @JavascriptInterface
        public String getLatitude(){
            return lati;
        }
        @JavascriptInterface
        public String getLongitude(){
            return longit;
        }
    }

    public void makeNewGpsService(){
        if(gps == null) {
            gps = new GPSTracker(MapsView.this,mHandler);
        }else{
            gps.Update();
        }

    }
    public void logPrint(String str){
        //editText.append(getTimeStr()+" "+str+"\n");
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    /*
    public String getTimeStr(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm:ss");
        return sdfNow.format(date);
    }*/
}
