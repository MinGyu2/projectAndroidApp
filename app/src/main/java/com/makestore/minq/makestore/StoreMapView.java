package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class StoreMapView extends AppCompatActivity{
    String sname = "";
    WebView web;
    WebSettings webss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemapview);

        Intent i = getIntent();
        sname = i.getStringExtra("storename");
        String ssss;


        web = (WebView)findViewById(R.id.storemapvs);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new StoreMapView.WebAppInterface(this),"Android");
        web.loadUrl("http://13.125.255.233:8887/clients/map.php?store="+sname);
        web.reload();
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
    }
}
